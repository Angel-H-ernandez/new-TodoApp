package com.AngelHernandez.mytodoapp.data



import com.AngelHernandez.mytodoapp.core.RetrofitHelper
import com.AngelHernandez.mytodoapp.data.network.ApiClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return RetrofitHelper.getRetrofit()
    }

    @Provides
    fun provideApiClient(retrofit: Retrofit): ApiClient {
        return retrofit.create(ApiClient::class.java)
    }
}
