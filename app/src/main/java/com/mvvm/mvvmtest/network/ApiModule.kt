package com.mvvm.mvvmtest.network

import com.mvvm.mvvmkotlin.network.RetrofitHelper
import com.mvvm.mvvmtest.Constant
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
internal object ApiModule {

    @Provides
    @Singleton
    fun providerApiService(): ApiService {
        return RetrofitHelper(Constant.BASEURL).create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideHelper(apiService: ApiService): ServiceHelper {
        return ServiceHelper(apiService)
    }

    @Provides
    @Singleton
    fun providerRepository(helper: ServiceHelper): Repository {
        return Repository(helper)
    }
}