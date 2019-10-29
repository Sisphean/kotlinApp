package com.sisyphean.kotlinapp.model.api

import android.util.Log
import com.sisyphean.kotlinapp.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import java.security.SecureRandom
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLContext


object RetrofitClient {

    val service by lazy {
        Log.d(javaClass.name, "retrofit service init...")
        getService(ApiService::class.java, ApiService.BASE_URL)
    }

    private val client: OkHttpClient
        get() {

            val x509TrustManager = object : X509TrustManager {
                override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun getAcceptedIssuers(): Array<X509Certificate> {
                    return arrayOf()
                }
            }
            val trustAllCerts: Array<TrustManager> = arrayOf(x509TrustManager)

            val sslContext = SSLContext.getInstance("SSL")
            sslContext.init(null, trustAllCerts, SecureRandom())

            val logging = HttpLoggingInterceptor()
            if (BuildConfig.DEBUG) {
                logging.level = HttpLoggingInterceptor.Level.BODY
            } else {
                logging.level = HttpLoggingInterceptor.Level.BASIC
            }

            return OkHttpClient.Builder()
//                    .sslSocketFactory(sslContext.socketFactory, x509TrustManager)
//                    .hostnameVerifier(HostnameVerifier { _, _ -> true })
                    .addInterceptor(logging)
                    .connectTimeout(5, TimeUnit.SECONDS)
                    .build()
        }

    private fun <T> getService(serviceClass: Class<T>, baseUrl: String): T {
        return Retrofit.Builder()
                .client(client)
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(serviceClass)

    }
}