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

```    
[main] INFO ai.djl.pytorch.engine.PtEngine - Number of inter-op threads is 8
[main] INFO ai.djl.pytorch.engine.PtEngine - Number of intra-op threads is 8
features: [0,352672, 2,204252]
label: -2.5814414
[main] INFO com.enpasos.bugs.Main - Training epoch = 0
[main] INFO ai.djl.pytorch.engine.PtGradientCollector - 19 no of managed arrays
[main] INFO ai.djl.pytorch.engine.PtGradientCollector - removing the gradient in 2 no of managed arrays
epoch;duration[ms];gpuMem[MiB]
0;8;1726
[main] INFO com.enpasos.bugs.Main - Training epoch = 1
[main] INFO ai.djl.pytorch.engine.PtGradientCollector - 37 no of managed arrays
[main] INFO ai.djl.pytorch.engine.PtGradientCollector - removing the gradient in 2 no of managed arrays
epoch;duration[ms];gpuMem[MiB]
0;8;1726
1;3;1726
[main] INFO com.enpasos.bugs.Main - Training epoch = 2
[main] INFO ai.djl.pytorch.engine.PtGradientCollector - 55 no of managed arrays
[main] INFO ai.djl.pytorch.engine.PtGradientCollector - removing the gradient in 2 no of managed arrays
epoch;duration[ms];gpuMem[MiB]
0;8;1726
1;3;1726
2;3;1726
```


## Example run

### Run1
**Result:**
- number of managed arrays grows by 18 per epoch
- 2 gradients are removed per epoch


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

