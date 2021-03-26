package com.luc.apifood.application.injection

import com.google.gson.GsonBuilder
import com.luc.apifood.application.AppConstants.API_KEY
import com.luc.apifood.application.AppConstants.BASE_URL_RECIPE_INFORMATION
import com.luc.apifood.application.AppConstants.HOST
import com.luc.apifood.data.RecipeInformationWebService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideRetrofitInstanceForRecipeInformation() = Retrofit.Builder()
        .baseUrl(BASE_URL_RECIPE_INFORMATION)
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
        .client(OkHttpClient.Builder().apply {
            addInterceptor(
                Interceptor { chain ->
                    val builder = chain.request().newBuilder()
                    builder.header("x-rapidapi-key", API_KEY)
                    builder.header("x-rapidapi-host", HOST)
                    return@Interceptor chain.proceed(builder.build())
                }
            )
        }.build())
        .build()

    @Singleton
    @Provides
    fun provideRetrofitWebServiceForRecipesInformation(retrofit: Retrofit) =
        retrofit.create(RecipeInformationWebService::class.java)
}