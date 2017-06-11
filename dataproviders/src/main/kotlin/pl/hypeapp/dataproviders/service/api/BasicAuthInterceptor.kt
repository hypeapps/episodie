package pl.hypeapp.dataproviders.service.api

import okhttp3.Credentials
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class BasicAuthInterceptor(username: String, password: String) : Interceptor {

    val credentials: String = Credentials.basic(username, password)

    override fun intercept(chain: Interceptor.Chain?): Response {
        val request: Request? = chain?.request()
        val authenticatedRequest: Request = request!!.newBuilder()
                .header("Authorization", credentials)
                .build()
        return chain.proceed(authenticatedRequest)
    }

}
