package com.fakss.premierleaguefixtures.data

import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


interface RxObservableSchedulers {

    fun <T> applySchedulers(): ObservableTransformer<T, T>

    companion object {
        val DEFAULT: RxObservableSchedulers = object :
            RxObservableSchedulers {
            override fun <T> applySchedulers(): ObservableTransformer<T, T> {
                return ObservableTransformer { single ->
                    single
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                }
            }
        }

        val TEST_SCHEDULER: RxObservableSchedulers = object :
            RxObservableSchedulers {
            override fun <T> applySchedulers(): ObservableTransformer<T, T> {
                return ObservableTransformer { single ->
                    single
                        .subscribeOn(Schedulers.trampoline())
                        .observeOn(Schedulers.trampoline())
                }
            }
        }
    }
}