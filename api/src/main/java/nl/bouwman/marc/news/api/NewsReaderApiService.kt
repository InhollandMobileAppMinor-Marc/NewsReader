package nl.bouwman.marc.news.api

import nl.bouwman.marc.news.api.models.LoginResponse
import nl.bouwman.marc.news.api.models.RegistrationResponse
import nl.bouwman.marc.news.api.models.UserCredentials
import nl.bouwman.marc.news.domain.models.ArticleBatch
import nl.bouwman.marc.news.domain.models.Feed
import retrofit2.Response
import retrofit2.http.*

interface NewsReaderApiService {
    @GET("Feeds")
    suspend fun getFeeds(): Response<Set<Feed>>

    @GET("Articles")
    suspend fun getArticles(
        @Header("x-authtoken") authToken: String? = null,
        @Query("count") count: Int? = null,
        @Query("feed") feed: Int? = null,
        @Query("feeds") feeds: String? = null,
        @Query("category") category: Int? = null
    ): Response<ArticleBatch>

    @GET("Articles/{id}")
    suspend fun getArticlesById(
        @Path("id") id: Int,
        @Header("x-authtoken") authToken: String? = null,
        @Query("count") count: Int? = null,
        @Query("feed") feed: Int? = null,
        @Query("feeds") feeds: String? = null,
        @Query("category") category: Int? = null
    ): Response<ArticleBatch>

    @GET("Articles/liked")
    suspend fun getLikedArticles(
        @Header("x-authtoken") authToken: String
    ): Response<ArticleBatch>

    @PUT("Articles/{id}/like")
    suspend fun likeArticle(
        @Path("id") id: Int,
        @Header("x-authtoken") authToken: String
    ): Response<Unit>

    @DELETE("Articles/{id}/like")
    suspend fun dislikeArticle(
        @Path("id") id: Int,
        @Header("x-authtoken") authToken: String
    ): Response<Unit>

    @POST("Users/register")
    suspend fun registerNewUser(
        @Body user: UserCredentials
    ): Response<RegistrationResponse>

    @POST("Users/login")
    suspend fun loginUser(
        @Body user: UserCredentials
    ): Response<LoginResponse>
}