package com.fakss.premierleaguefixtures.data.api

import com.fakss.premierleaguefixtures.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiService {

    companion object {
        private val api = lazy { ApiService().create(Api::class.java) }
        fun getApiInstance(): Api {
            return api.value
        }
    }

    private fun <S> create(clazz: Class<S>) = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .client(getHttpClient())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(clazz)

    private fun getHttpClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG)
            interceptor.level = HttpLoggingInterceptor.Level.BODY
        else
            interceptor.level = HttpLoggingInterceptor.Level.NONE

        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .retryOnConnectionFailure(true)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .cache(null)
            .addInterceptor {
                val original = it.request()
                val request = original.newBuilder()
                    .header("X-Auth-Token", BuildConfig.TOKEN)
                    .method(original.method(), original.body())
                    .build()
                it.proceed(request)
            }
            .build()
    }
}