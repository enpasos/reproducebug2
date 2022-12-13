# reproduce bug

Using a toy example derived from [djl docs](https://d2l.djl.ai/chapter_linear-networks/linear-regression-scratch.html).

## build

```
    gradlew build
```

## run

``` 
    java -jar app/build/libs/app-0.0.1-SNAPSHOT.jar  
```




## Example run

### Run1


**Result:**
- number of managed arrays grows by 28 per epoch
- 2 gradients are removed per gradient collection

**Raw Output:**
```    
[main] INFO ai.djl.pytorch.engine.PtEngine - Number of inter-op threads is 8
[main] INFO ai.djl.pytorch.engine.PtEngine - Number of intra-op threads is 8
features: [-0,413181, 0,584059]
label: 1.385866
[main] INFO com.enpasos.bugs.Main - Training epoch = 0
[main] INFO com.enpasos.bugs.Main - next batch
[main] INFO ai.djl.pytorch.engine.PtGradientCollector - 19 no of managed arrays
[main] INFO com.enpasos.bugs.Main - ...newGradientCollector()
[main] INFO ai.djl.pytorch.engine.PtGradientCollector - 19 no of managed arrays
[main] INFO ai.djl.pytorch.engine.PtGradientCollector - removing the gradient in 2 no of managed arrays
[main] INFO ai.djl.pytorch.engine.PtGradientCollector - 32 no of managed arrays
[main] INFO com.enpasos.bugs.Main - sgd(params, lr, batchSize);
[main] INFO ai.djl.pytorch.engine.PtGradientCollector - 38 no of managed arrays
[main] INFO com.enpasos.bugs.Main - batch.close();
[main] INFO ai.djl.pytorch.engine.PtGradientCollector - 27 no of managed arrays
[main] INFO com.enpasos.bugs.Main - next batch
[main] INFO ai.djl.pytorch.engine.PtGradientCollector - 29 no of managed arrays
[main] INFO com.enpasos.bugs.Main - ...newGradientCollector()
[main] INFO ai.djl.pytorch.engine.PtGradientCollector - 29 no of managed arrays
[main] INFO ai.djl.pytorch.engine.PtGradientCollector - removing the gradient in 2 no of managed arrays
[main] INFO ai.djl.pytorch.engine.PtGradientCollector - 42 no of managed arrays
[main] INFO com.enpasos.bugs.Main - sgd(params, lr, batchSize);
[main] INFO ai.djl.pytorch.engine.PtGradientCollector - 48 no of managed arrays
[main] INFO com.enpasos.bugs.Main - batch.close();
[main] INFO ai.djl.pytorch.engine.PtGradientCollector - 37 no of managed arrays
[main] INFO com.enpasos.bugs.Main - NDArray trainL = squaredLoss(...);
[main] INFO ai.djl.pytorch.engine.PtGradientCollector - 45 no of managed arrays
epoch;duration[ms];gpuMem[MiB]
0;15;1726
[main] INFO com.enpasos.bugs.Main - Training epoch = 1
[main] INFO com.enpasos.bugs.Main - next batch
[main] INFO ai.djl.pytorch.engine.PtGradientCollector - 47 no of managed arrays
[main] INFO com.enpasos.bugs.Main - ...newGradientCollector()
[main] INFO ai.djl.pytorch.engine.PtGradientCollector - 47 no of managed arrays
[main] INFO ai.djl.pytorch.engine.PtGradientCollector - removing the gradient in 2 no of managed arrays
[main] INFO ai.djl.pytorch.engine.PtGradientCollector - 60 no of managed arrays
[main] INFO com.enpasos.bugs.Main - sgd(params, lr, batchSize);
[main] INFO ai.djl.pytorch.engine.PtGradientCollector - 66 no of managed arrays
[main] INFO com.enpasos.bugs.Main - batch.close();
[main] INFO ai.djl.pytorch.engine.PtGradientCollector - 55 no of managed arrays
[main] INFO com.enpasos.bugs.Main - next batch
[main] INFO ai.djl.pytorch.engine.PtGradientCollector - 57 no of managed arrays
[main] INFO com.enpasos.bugs.Main - ...newGradientCollector()
[main] INFO ai.djl.pytorch.engine.PtGradientCollector - 57 no of managed arrays
[main] INFO ai.djl.pytorch.engine.PtGradientCollector - removing the gradient in 2 no of managed arrays
[main] INFO ai.djl.pytorch.engine.PtGradientCollector - 70 no of managed arrays
[main] INFO com.enpasos.bugs.Main - sgd(params, lr, batchSize);
[main] INFO ai.djl.pytorch.engine.PtGradientCollector - 76 no of managed arrays
[main] INFO com.enpasos.bugs.Main - batch.close();
[main] INFO ai.djl.pytorch.engine.PtGradientCollector - 65 no of managed arrays
[main] INFO com.enpasos.bugs.Main - NDArray trainL = squaredLoss(...);
[main] INFO ai.djl.pytorch.engine.PtGradientCollector - 73 no of managed arrays
epoch;duration[ms];gpuMem[MiB]
0;15;1726
1;8;1726
[main] INFO com.enpasos.bugs.Main - Training epoch = 2
[main] INFO com.enpasos.bugs.Main - next batch
[main] INFO ai.djl.pytorch.engine.PtGradientCollector - 75 no of managed arrays
[main] INFO com.enpasos.bugs.Main - ...newGradientCollector()
[main] INFO ai.djl.pytorch.engine.PtGradientCollector - 75 no of managed arrays
[main] INFO ai.djl.pytorch.engine.PtGradientCollector - removing the gradient in 2 no of managed arrays
[main] INFO ai.djl.pytorch.engine.PtGradientCollector - 88 no of managed arrays
[main] INFO com.enpasos.bugs.Main - sgd(params, lr, batchSize);
[main] INFO ai.djl.pytorch.engine.PtGradientCollector - 94 no of managed arrays
[main] INFO com.enpasos.bugs.Main - batch.close();
[main] INFO ai.djl.pytorch.engine.PtGradientCollector - 83 no of managed arrays
[main] INFO com.enpasos.bugs.Main - next batch
[main] INFO ai.djl.pytorch.engine.PtGradientCollector - 85 no of managed arrays
[main] INFO com.enpasos.bugs.Main - ...newGradientCollector()
[main] INFO ai.djl.pytorch.engine.PtGradientCollector - 85 no of managed arrays
[main] INFO ai.djl.pytorch.engine.PtGradientCollector - removing the gradient in 2 no of managed arrays
[main] INFO ai.djl.pytorch.engine.PtGradientCollector - 98 no of managed arrays
[main] INFO com.enpasos.bugs.Main - sgd(params, lr, batchSize);
[main] INFO ai.djl.pytorch.engine.PtGradientCollector - 104 no of managed arrays
[main] INFO com.enpasos.bugs.Main - batch.close();
[main] INFO ai.djl.pytorch.engine.PtGradientCollector - 93 no of managed arrays
[main] INFO com.enpasos.bugs.Main - NDArray trainL = squaredLoss(...);
[main] INFO ai.djl.pytorch.engine.PtGradientCollector - 101 no of managed arrays
epoch;duration[ms];gpuMem[MiB]
0;15;1726
1;8;1726
2;7;1726

```

**Stack:**
- Java
  - DJL: 0.21.0-SNAPSHOT  (13.12.2022)
  - Java: Corretto-17.0.3.6.1
- PYTORCH: 1.13.0
- CUDA
  - CUDNN: cudnn-windows-x86_64-8.7.0.84_cuda11-
  - CUDA SDK: 11.7.1
- OS
  - GPU Driver: 527.27
  - OS: Edition	Windows 11 Pro
- HW
  - GPU: NVIDIA Quadro RTX 5000
  - CPU: Intel Xeon E-2286M
  - RAM: 64 GB

