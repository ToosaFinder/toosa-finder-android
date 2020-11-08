package com.toosafinder.network

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import okio.Buffer
import java.io.IOException

class ErrorHandlingInterceptor: Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response? {
        val request: Request = chain.request()
        val response: Response = chain.proceed(request)

        var buffer: Buffer = Buffer()
        request.body()?.writeTo(buffer)
        println(buffer.readUtf8())

        if(response.code() == 409) {
            return Response.Builder()
                .request(request)
                .receivedResponseAtMillis(response.receivedResponseAtMillis())
                .protocol(response.protocol())
                .priorResponse(response.priorResponse())
                .networkResponse(response.networkResponse())
                .message(response.message())
                .code(200)
                .handshake(response.handshake())
                .cacheResponse(response.cacheResponse())
                .headers(response.headers())
                .body(response.body())
                .build()
        }
        return response
    }
}