package edu.okei.core.data.data_source.network

import android.telephony.mbms.DownloadProgressListener
import edu.okei.core.data.body.admin.NewUserBody
import edu.okei.core.data.body.statistics.CreateReportDataBody
import io.ktor.client.HttpClient
import io.ktor.client.plugins.onDownload
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

internal class AdminApi(
    private val authClient: HttpClient,
) {
    suspend fun addUser(newUser: NewUserBody) = authClient
        .post("/api/signup"){
            setBody(newUser)
            contentType(ContentType.Application.Json)
        }
    suspend fun deleteUser(id: String) = authClient
        .delete("/api/user/$id")

    suspend fun getReportForMonth(monthIndex: Int) = authClient.
            get("/api/report/${monthIndex}")
    suspend fun createReportForMonth(monthIndex: Int, body: CreateReportDataBody) = authClient
        .post("/api/report/${monthIndex}"){
            setBody(body)
            contentType(ContentType.Application.Json)
        }
    suspend fun downloadFile(url: String) = authClient
        .get(url)
}