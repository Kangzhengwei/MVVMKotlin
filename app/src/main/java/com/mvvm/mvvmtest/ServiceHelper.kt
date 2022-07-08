package com.mvvm.mvvmtest

class ServiceHelper(private val apiService: ApiService) {


    suspend fun getToken() = apiService.getToken()


    companion object {
        @Volatile
        private var instance: ServiceHelper? = null

        @JvmStatic
        fun getInstance(apiService: ApiService): ServiceHelper? {
            if (instance == null) {
                synchronized(ServiceHelper::class.java) {
                    if (instance == null) {
                        instance = ServiceHelper(apiService)
                    }
                }
            }
            return instance
        }
    }
}