### RxJava ###

```
1. Observable example


```
### Channels
```
val channel = Channel<Int>()

GlobalScope.launch { repeat(x) { channel.send(it)} }
GlobalScope.launch { for(i in channel) print("$i") }
```

### Sequence

```
fun rangeCoroutine(begin:Int, end:Int) = sequence {
        for (i in begin..end) 
          yield(i)
    }
```

# Coroutines

#### withContext
```
GlobalScope.launch{
withContext(Dispatchers.Default or IO) {
     }
}
```

### async

return type of async is Deferred which extends Job
#### serial
```
val first = async { firstNumber()}.await()
val second = async {secondNumber()}.await()
val third = async { thirdNumber() }.await()
```

#### Parallel
```
val result = first.await() + second.await() + third.await()
```

#### RunBlocking

Blocks the execution of current thread, whether it may be main or coroutine

refer: CoroutineTest.kt -> orderOfExecution()

Also refer: [stackoverflow](https://stackoverflow.com/q/53535977/7735032) 