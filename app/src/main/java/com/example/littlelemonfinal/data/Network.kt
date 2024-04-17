package com.example.littlelemonfinal.data

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MenuNetwork(

    @SerialName("menu")
    val items: List<MenuItemNetwork>
)

@Serializable
data class MenuItemNetwork(
    @SerialName("id")
    val id: Int,

    @SerialName("title")
    val title: String,

    @SerialName("description")
    val description: String,

    @SerialName("price")
    val price: Double,

    @SerialName("category")
    val category: String,

    @SerialName("image")
    val imageUrl: String
){
    fun toMenuItemRoom() = MenuItemRoom(
        id = id,
        title = title,
        price = price,
        description = description,
        category = category,
        imageUrl = imageUrl
    )
}

suspend fun fetchFromServer(url: String): List<MenuItemNetwork> {
    val httpClient = HttpClient(Android) {
        install(ContentNegotiation) {
            json(contentType = ContentType("text", "plain"))
        }
    }
    val  httpResponse: MenuNetwork = httpClient.get(url).body()
    return httpResponse.items
}