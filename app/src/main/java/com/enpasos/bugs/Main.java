package com.enpasos.bugs;

import ai.djl.Device;
import ai.djl.Model;
import ai.djl.engine.Engine;
import ai.djl.ndarray.NDArray;
import ai.djl.ndarray.NDList;
import ai.djl.ndarray.NDManager;
import ai.djl.ndarray.gc.SwitchGarbageCollection;
import ai.djl.ndarray.types.DataType;
import ai.djl.ndarray.types.Shape;
import ai.djl.nn.Block;
import ai.djl.nn.LambdaBlock;
import ai.djl.pytorch.engine.PtGradientCollector;
import ai.djl.training.DefaultTrainingConfig;
import ai.djl.training.GradientCollector;
import ai.djl.training.Trainer;
import ai.djl.training.dataset.ArrayDataset;
import ai.djl.training.dataset.Batch;
import ai.djl.translate.TranslateException;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

//import static ai.djl.pytorch.engine.PtNDManager.debugDumpFromSystemManager;
import static com.enpasos.bugs.Training.setupTrainingConfig;
import static com.enpasos.bugs.Training.sgd;
import static com.enpasos.bugs.Training.linreg;
import static com.enpasos.bugs.Training.squaredLoss;


/**
 * A simple example taken from https://d2l.djl.ai/chapter_linear-networks/linear-regression-scratch.html to demonstrate a bug in DJL.
 */
@Slf4j
public final class Main {

    private Main() {
    }

    public static void main(String[] args) throws IOException, TranslateException {


        if(args.length > 0 && args[0].equals("gc")) {
            SwitchGarbageCollection.on();
        }



       try (NDManager manager = NDManager.newBaseManager();) {

            List<DurAndMem> durations = new ArrayList<>();

            NDArray trueW = manager.create(new float[]{2, -3.4f});
            float trueB = 4.2f;

            DataPoints dp = syntheticData(manager, trueW, trueB, 1000);
            NDArray features = dp.getX();
            NDArray labels = dp.getY();

            System.out.printf("features: [%f, %f]\n", features.get(0).getFloat(0), features.get(0).getFloat(1));
            System.out.println("label: " + labels.getFloat(0));


            int batchSize = 10;

            ArrayDataset dataset = new ArrayDataset.Builder()
                .setData(features) // Set the Features
                .optLabels(labels) // Set the Labels
                .setSampling(batchSize, false) // set the batch size and random sampling to false
                .build();


            NDArray w = manager.randomNormal(0, 0.01f, new Shape(2, 1), DataType.FLOAT32);
            NDArray b = manager.zeros(new Shape(1));

            Block block = LambdaBlock.singleton(x ->  x.dot(w).add(b));


            NDList params = new NDList(w, b);

            float lr = 0.03f;
            int numEpochs = 100;


            for (NDArray param : params) {
                param.setRequiresGradient(true);
            }

            try (Model model = Model.newInstance("simple model", Device.gpu())) {
                model.setBlock(block);

                DefaultTrainingConfig config = setupTrainingConfig();



                    for (int epoch = 0; epoch < numEpochs; epoch++) {

                        log.info("Training epoch = {}", epoch);
                        DurAndMem duration = new DurAndMem();
                        duration.on();

                        // Assuming the number of examples can be divided by the batch size, all
                        // the examples in the training dataset are used once in one epoch
                        // iteration. The features and tags of minibatch examples are given by X
                        // and y respectively.
                        for (Batch batch : dataset.getData(manager)) {
                            NDArray X = batch.getData().head();
                            NDArray y = batch.getLabels().head();
                            // log.info("...newGradientCollector()");
                            try (GradientCollector gc = Engine.getInstance().newGradientCollector()) {
                                // Minibatch loss in X and y
                                NDArray l = squaredLoss(linreg(X, params.get(0), params.get(1)), y);
                                gc.backward(l);  // Compute gradient on l with respect to w and b
                            }
                            sgd(params, lr, batchSize);
                            batch.close();
                        }
                        NDArray trainL = squaredLoss(linreg(features, params.get(0), params.get(1)), labels);

                        duration.off();
                        durations.add(duration);
                        System.out.println("epoch;duration[ms];gpuMem[MiB]");
                        IntStream.range(0, durations.size()).forEach(i -> System.out.println(i + ";" + durations.get(i).getDur() + ";" + durations.get(i).getMem() / 1024 / 1024));

                    }
                }

        }
    }


    // Generate y = X w + b + noise
    public static DataPoints syntheticData(NDManager manager, NDArray w, float b, int numExamples) {
        NDArray X = manager.randomNormal(new Shape(numExamples, w.size()));
        NDArray y = X.matMul(w.transpose()).add(b);
        // Add noise
        y = y.add(manager.randomNormal(0, 0.01f, y.getShape(), DataType.FLOAT32));
        return new DataPoints(X, y);
    }

}
