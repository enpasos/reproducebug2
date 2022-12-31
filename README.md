# reproduce bug

Using a toy example derived from [djl docs](https://d2l.djl.ai/chapter_linear-networks/linear-regression-scratch.html).

## bug fix implementation for PyTorch

```
git clone https://github.com/enpasos/djl.git
cd djl
git checkout gc-orphaned-resources
gradlew build -x test
gradlew publishToMavenLocal
```


## build

```
gradlew build
```

## run without using gc 

``` 
java -jar app/build/libs/app-0.0.1-SNAPSHOT.jar  
```

## run with using gc

``` 
java -jar app/build/libs/app-0.0.1-SNAPSHOT.jar gc
```

## run with using gc

``` 
java -XX:+UnlockExperimentalVMOptions -XX:G1MaxNewSizePercent=30 -jar app/build/libs/app-0.0.1-SNAPSHOT.jar gc
```


## Stack

 
**Stack:**
- Java
  - DJL: 0.21.0-SNAPSHOT  (31.12.2022)
  - Java: Corretto-17.0.3.6.1
- PYTORCH: 1.13.1
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

