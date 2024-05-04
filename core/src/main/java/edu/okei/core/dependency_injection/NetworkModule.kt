package edu.okei.core.dependency_injection
import android.util.Log
import edu.okei.core.data.data_source.network.AdminApi
import edu.okei.core.data.data_source.network.AuthApi
import edu.okei.core.data.data_source.network.CriteriaApi
import edu.okei.core.data.data_source.network.StatsApi
import edu.okei.core.data.data_source.network.TeacherApi
import edu.okei.core.domain.repos.UserRepos
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.HttpRequestRetry
import io.ktor.client.plugins.HttpSend
import io.ktor.client.plugins.cache.HttpCache
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.plugin
import io.ktor.client.request.header
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.kodein.di.DI
import org.kodein.di.bindConstant
import org.kodein.di.bindProvider
import org.kodein.di.bindSingleton
import org.kodein.di.instance

internal const val BASE_CLIENT = "BASE_CLIENT"
internal const val AUTHORIZED_CLIENT = "AUTHORIZED_CLIENT"
const val URL_SERVER = "URL_SERVER"

internal val NetworkModule by DI.Module {
    bindConstant(URL_SERVER) { "http://82.97.249.229" }
    bindSingleton(BASE_CLIENT) {
        httpClient(instance(URL_SERVER))
            .apply {
                plugin(HttpSend).intercept { request ->
                    Log.d("$BASE_CLIENT::intercept::request", request.url.toString())
                    execute(request).apply {
                        Log.d(
                            "$BASE_CLIENT::intercept::answer",
                            this.request.url.toString() + " " + this.response.status.value.toString()
                        )
                    }
                }
            }
    }
    bindSingleton(AUTHORIZED_CLIENT) {
        httpClient(instance(URL_SERVER))
            .apply {
                plugin(HttpSend).intercept { request ->
                    Log.d("$AUTHORIZED_CLIENT::intercept::request", request.url.toString())
                    request.header(HttpHeaders.Authorization, instance<UserRepos>().getAccessToken())
                    execute(request).apply {
                        Log.d(
                            "$AUTHORIZED_CLIENT::intercept::answer",
                            this.request.url.toString() + " " + this.response.status.value.toString()
                        )
                    }
                }
            }
    }
    bindProvider {
        AuthApi(instance<HttpClient>(BASE_CLIENT))
    }
    bindProvider {
        StatsApi(instance<HttpClient>(AUTHORIZED_CLIENT))
    }
    bindProvider {
        TeacherApi(instance<HttpClient>(AUTHORIZED_CLIENT))
    }
    bindProvider {
        AdminApi(instance<HttpClient>(AUTHORIZED_CLIENT))
    }
    bindProvider {
        CriteriaApi(instance<HttpClient>(AUTHORIZED_CLIENT))
    }
}

private fun httpClient(urlServer: String) = HttpClient(Android) {
    install(ContentNegotiation) {
        json(Json {
            prettyPrint = true
            isLenient = true
            ignoreUnknownKeys = true
            allowSpecialFloatingPointValues = true
        })
    }
    install(HttpRequestRetry) {
        retryOnServerErrors(maxRetries = 2)
        exponentialDelay()
    }
    install(HttpCache)
//    expectSuccess = true
    defaultRequest {
        url(urlServer)
    }
}