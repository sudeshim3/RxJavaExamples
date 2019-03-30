package com.example.myapplication

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.myapplication.models.User
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Predicate
import io.reactivex.schedulers.Schedulers

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        freezeThread(filterForgroundObservable()) //This will freeze the Main Thread
//        freezeThread(filterBackgroundObservable())  // This won't has it happens in background Thread



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
                Log.d("d", "OnSubscribe")
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
}
