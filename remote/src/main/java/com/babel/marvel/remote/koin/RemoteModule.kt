package com.babel.marvel.remote.koin

import com.babel.marvel.data.datasource.remote.MarvelRemoteDataSource
import com.babel.marvel.remote.service.MarvelApiService
import com.babel.marvel.remote.source.MarvelRemoteDataSourceImpl
import java.math.BigInteger
import java.security.MessageDigest
import java.sql.Timestamp
import java.util.*
import java.util.concurrent.TimeUnit
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "https://gateway.marvel.com:443/v1/public/"
const val TIME_OUT = 30L
const val API_KEY_VALUE_PUBLIC = "ba9dbe77040fd5833bcd484fa79a0452"
const val API_KEY_VALUE_PRIVATE = "0ed42722f083f074f95e8f9cb1ea9607c5781946"
const val API_KEY = "apikey"
const val TS_KEY = "ts"
const val HASH_KEY = "hash"
const val FORMAT_MD5 = "MD5"

val remoteModule = module {

    single {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        val httpClient = OkHttpClient.Builder()
        httpClient.interceptors().add(
            Interceptor { chain ->
                var request: Request = chain.request()
                val ts = Date(Timestamp(System.currentTimeMillis()).time).time.toString()
                val url: HttpUrl = request.url.newBuilder()
                    .addQueryParameter(TS_KEY, ts)
                    .addQueryParameter(API_KEY, API_KEY_VALUE_PUBLIC)
                    .addQueryParameter(HASH_KEY, md5(ts + API_KEY_VALUE_PRIVATE + API_KEY_VALUE_PUBLIC))
                    .build()
                request = request.newBuilder().url(url).build()
                chain.proceed(request)
            }
        )
        httpClient.readTimeout(TIME_OUT, TimeUnit.SECONDS)
        httpClient.writeTimeout(TIME_OUT, TimeUnit.SECONDS)
        httpClient.connectTimeout(TIME_OUT, TimeUnit.SECONDS)
        httpClient.addInterceptor(logging)

        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient.build())
            .build()
    }

    factory { provideMarvelAPI(get()) }

    factory<MarvelRemoteDataSource> {
        MarvelRemoteDataSourceImpl(
            get()
        )
    }
}

fun provideMarvelAPI(retrofit: Retrofit): MarvelApiService =
    retrofit.create(MarvelApiService::class.java)

fun md5(input: String): String {
    val md = MessageDigest.getInstance(FORMAT_MD5)
    return BigInteger(1, md.digest(input.toByteArray())).toString(16).padStart(32, '0')
}
