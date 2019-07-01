package com.example.myapplication

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.SearchView
import android.util.Log
import com.example.myapplication.models.User
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import io.reactivex.functions.Predicate
import io.reactivex.schedulers.Schedulers


import java.util.concurrent.Callable
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity(), Consumer<in Throwable> {

    var disposables: CompositeDisposable = CompositeDisposable()
    val observables_string by lazy { Observable.just("A", "B", "C", "D", "E", "F") }
    val observables_int by lazy { Observable.just(1, 2, 3, 4, 5, 6, 7, 8, 9, 10) }
    lateinit var mSearchView: SearchView

    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        println((1..100).map { i -> mapOf(0 to i, i % 3 to "Fizz", i % 5 to "Buzz", i % 15 to "FizzBuzz")[0] })
        var large = 1_000_000 // User UnderScore to Improve Readablility

//        basicObservable()
        bufferObservable()
        try {
            Observable.just(serverRequest())
        } catch (e: Exception) {
        }

//        freezeThread(filterForgroundObservable()) //This will freeze the Main Thread
//        freezeThread(filterBackgroundObservable())  // This won't has it happens in background Thread

        val textQuery:Observable<String> = Observable.create<String> { emitter ->

            mSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(s: String): Boolean {
                    return false
                }
                override fun onQueryTextChange(s: String): Boolean {
                    if (!emitter.isDisposed)
                        emitter.onNext(s)
                    return false
                }
            })
        }.debounce(500, TimeUnit.MILLISECONDS)
            .subscribeOn(Schedulers.io())

        textQuery.subscribe(
            { onNext -> },
            { error -> },
            {          },
            { disposable -> })

        textQuery.subscribe(this::acceptString, this::acceptError,this::onComplete, this::addToDisposables)

        textQuery.subscribe(object : Observer<String> {
            override fun onComplete() {

            }

            override fun onSubscribe(d: Disposable) {

            }

            override fun onNext(t: String) {

            }

            override fun onError(e: Throwable) {

            }

        })

        textQuery.subscribe(object : Observer<String> {
            override fun onSubscribe(d: Disposable) {
                disposables.add(d)
            }

            override fun onNext(s: String) {
                sendServerRequest(s)
            }

            override fun onError(e: Throwable) {

            }

            override fun onComplete() {

            }
        })
    }

    private fun bufferObservable() {
        observables_int.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .buffer(3)
            .subscribe({}, {

            })
    }

    fun acceptString(s:String) {

    }

    fun acceptError(t:Throwable) {

    }

    fun onComplete() {

    }

    fun addToDisposables(d: Disposable) {

    }

    private fun callable() {
//From callable---------------------------------------------------------------------------------
        //wraps a synchronous function into a asynchronous call


        Observable.fromCallable(this::getString).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::receiver)

//--------------------------------OR--------------------------------------------------------------
// declare from callable as seperate function
        val networkCallable = Callable { serverRequest() }
        Observable.fromCallable(networkCallable).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::receiver)
    }

    fun getString() = "Hello"

    fun receiver(x: String) {

    }
//------------------------------------------------------------------------------------------------

    fun printSqr(a: Int) = println(a * a)

    fun calcArea(a: Int, func: (Int) -> Unit) {
        func(a)
    }

    private fun dbRequest() {
        calcArea(5, ::printSqr)
    }

    fun serverRequest(): String {
        return "sdfs"
    }


    private fun basicObservable() {

        observables_string
            .buffer(2)
            .subscribe(object : Observer<List<String>> {
                override fun onComplete() {
                    println("not implemented")
                }

                override fun onSubscribe(d: Disposable) {
                    println("not implemented")
                }

                override fun onNext(string: List<String>) {
                    string.forEach { println("The String is $it") }
                }

                override fun onError(e: Throwable) {
                    println("Error is ${e.localizedMessage}")
                }

            })

        var strings = arrayListOf("Tom", "Jack", "Harry")

        Observable.just(strings)
            .subscribe {
                it.toString()
            }

        Observable.fromArray(strings)
            .subscribe {

            }

        Observable.range(1, 100)
            .take(10)
            .takeLast(10)
            .subscribe()
    }

    fun filterForgroundObservable() = Observable
        .fromIterable(DataSource.createUserList())
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())


    fun filterBackgroundObservable() = Observable
        .fromIterable(DataSource.createUserList())
        .subscribeOn(Schedulers.io())
        .filter(Predicate<User> {
            Thread.sleep(1000)
            return@Predicate true
        })
        .observeOn(AndroidSchedulers.mainThread())

    private fun freezeThread(observableUser: Observable<User>) {
        observableUser.subscribe(object : Observer<User> {
            override fun onComplete() {

            }

            override fun onSubscribe(d: Disposable) {
                disposables.add(d)
            }

            override fun onNext(t: User) {
                Log.d("onNext", Thread.currentThread().name)
                Log.d("onNext", t.name)
                Thread.sleep(1000)      // Example of freezing UI. This is caused when main thread is put on sleep
            }

            override fun onError(e: Throwable) {
                Log.d("error", "Error")
            }

        })
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.clear()

    }
}
