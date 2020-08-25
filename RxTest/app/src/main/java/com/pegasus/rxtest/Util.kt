package com.pegasus.rxtest

import android.util.Log
import io.reactivex.Flowable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.rxkotlin.toFlowable
import io.reactivex.rxkotlin.toObservable
import io.reactivex.schedulers.Schedulers

fun rxTestMethod() {
    val list = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9)

    list.toObservable()
        .map { i -> i*3 }
        .filter { i -> i % 2 == 1 }
        .subscribeOn(Schedulers.computation())
        .observeOn(Schedulers.io())
        .subscribe(::compute)

    list.toFlowable()
        .map { i -> i*3 }
        .filter { i -> i % 2 == 1 }
        .subscribeOn(Schedulers.computation())
        .observeOn(Schedulers.io())
        .subscribe(::compute)

    val integerFlowable = Flowable.just(1, 2, 3, 4,5,6,7,8,9,10)
    integerFlowable.map { i -> i*3 }
        .filter { i -> i % 2 == 1 }
        .subscribeOn(Schedulers.computation())
        .observeOn(Schedulers.io())
        .subscribe(::compute)

    list.toObservable()
        .subscribeBy(
            onNext = { compute(it) },
            onError =  { it.printStackTrace() },
            onComplete = { Log.d("RXTAGG","Process Completed")}
        )
}

fun compute(v: Int) {
    Log.d("RXTAGG", "Input: $v");
    Thread.sleep(1000);
}
