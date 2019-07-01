package com.example.myapplication

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.OnLifecycleEvent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlin.coroutines.CoroutineContext
import kotlin.system.measureTimeMillis

class CoroutineTest : AppCompatActivity(), LifecycleObserver, CoroutineScope {

    lateinit var job: Job
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    val channel = Channel<Int>()

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate() {
        job = Job()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun on_Destroy() {
        job.cancel()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coroutine_test)
        lifecycle.addObserver(this)
//        runOnBlocking()
//        serialAsync()
        jobExample()
//        orderOfExecution()

    }

    private fun orderOfExecution() {
        GlobalScope.launch {
            runBlocking {
                launch {
                    delay(200L)
                    println("Task from runBlocking")
                }

                coroutineScope {
                    // Creates a new coroutine scope
                    launch {
                        delay(900L)
                        println("Task from nested launch")
                    }

                    delay(100L)
                    println("Task from coroutine scope") // This line will be printed before nested launch
                }
            }
            println("Coroutine scope is over")
        }
    }

    private fun jobExample() {

        GlobalScope.launch(Dispatchers.Default) {
            runBlocking {
                val job: Job = GlobalScope.launch(Dispatchers.Default) {
                    repeat(10) {
                        println("Hello")
                        delay(500)
                    }
                }
                delay(2000)
                println("cancelling")
                job.cancel()
            }
            println("out of blocking scope")
        }
    }

    private fun serialAsync() {

        GlobalScope.launch {
            val job: Job = async { firstNumber() }

            withContext(Dispatchers.Main) {

            }
            val time = measureTimeMillis {
                val first = async { firstNumber() }.await()
                val second = async { secondNumber() }.await()
                val third = async { thirdNumber() }.await()

                val result = first + second + third //6053 time
//                val result = first.await() + second.await() + third.await() // 3028 time
                println("Result:  $result")
            }
            println("Result in $time")
        }
    }

    suspend private fun thirdNumber(): Int {
        delay(1000)
        return 3
    }

    suspend private fun secondNumber(): Int {
        delay(2000)
        return 2
    }

    suspend private fun firstNumber(): Int {
        delay(3000)
        return 1
    }

    fun rangeCoroutine() {
        for (x in rangeCoroutine(0, 50))
            println(x)
    }

    fun channelCoroutine() {
        GlobalScope.launch { repeat(20) { channel.send(it) } }
        GlobalScope.launch { for (i in channel) print("$i") }
    }

    fun runOnBlocking() = GlobalScope.launch {

        withContext(Dispatchers.Default) {
            delay(5000)
            println("Thread Default ${Thread.currentThread().name}")
        }

        withContext(Dispatchers.IO) {
            delay(5000)
            println("Thread IO ${Thread.currentThread().name}")
        }
    }


    fun sandbox() = runBlocking {
        for (x in rangeCoroutine(7, 11)) print(x)
    }

    fun loadData() = GlobalScope.launch() { }
    fun rangeCoroutine(begin: Int, end: Int) = sequence {
        for (i in begin..end) yield(i)
    }
}
