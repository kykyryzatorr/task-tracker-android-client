package com.example.task_tracker_android_client.data.datasource.remote

import com.google.gson.GsonBuilder
import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.UUID
import java.util.concurrent.TimeUnit

object NetworkModule {
    private const val BASE_URL = "https://your-backend-url.com/"

    private class UUIDTypeAdapter : TypeAdapter<UUID>() {
        override fun write(out: JsonWriter, value: UUID?) {
            out.value(value?.toString())
        }

        override fun read(`in`: JsonReader): UUID? {
            return if (`in`.peek() == com.google.gson.stream.JsonToken.NULL) {
                `in`.nextNull()
                null
            } else {
                UUID.fromString(`in`.nextString())
            }
        }
    }

    private class LocalDateTimeTypeAdapter : TypeAdapter<LocalDateTime>() {
        private val formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME

        override fun write(out: JsonWriter, value: LocalDateTime?) {
            out.value(value?.format(formatter))
        }

        override fun read(`in`: JsonReader): LocalDateTime? {
            return if (`in`.peek() == com.google.gson.stream.JsonToken.NULL) {
                `in`.nextNull()
                null
            } else {
                LocalDateTime.parse(`in`.nextString(), formatter)
            }
        }
    }

    private val gson = GsonBuilder()
        .registerTypeAdapter(UUID::class.java, UUIDTypeAdapter())
        .registerTypeAdapter(LocalDateTime::class.java, LocalDateTimeTypeAdapter())
        .create()

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    val apiService: ApiService = retrofit.create(ApiService::class.java)
}