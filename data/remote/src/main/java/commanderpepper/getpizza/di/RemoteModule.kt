package commanderpepper.getpizza.di

import commanderpepper.getpizza.remote.retrofit.FourSquareService
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

fun provideInterceptor(): Interceptor {
    return HttpLoggingInterceptor().apply {
        this.level = HttpLoggingInterceptor.Level.BODY
    }
}

fun provideClient(interceptor: Interceptor): OkHttpClient {
    return OkHttpClient.Builder().apply {
        this.addInterceptor(interceptor)
    }.build()
}

fun provideConverterFactory(): Converter.Factory {
    return Json { ignoreUnknownKeys = true }.asConverterFactory(
        "application/json; charset=UTF8".toMediaType())
}

fun provideRetrofit(okHttpClient: OkHttpClient, converterFactory: Converter.Factory): Retrofit {
    return Retrofit.Builder()
        .baseUrl("https://api.foursquare.com/v2/")
        .client(okHttpClient)
        .addConverterFactory(converterFactory)
        .build()
}

fun provideService(retrofit: Retrofit): FourSquareService {
    return retrofit.create(FourSquareService::class.java)
}

val remoteModule = module {
    single { provideInterceptor() }
    single { provideClient(get()) }
    single { provideConverterFactory() }
    single { provideRetrofit(get(), get()) }
    single { provideService(get()) }
}