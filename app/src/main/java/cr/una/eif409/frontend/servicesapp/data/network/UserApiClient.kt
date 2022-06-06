package cr.una.eif409.frontend.servicesapp.data.network

import cr.una.eif409.frontend.servicesapp.data.model.UserResult
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface UserApiClient {
    @GET("api/v1/user/{user_name}")
    suspend fun getUserByUserName(@Path("user_name") username: String): Response<UserResult>

    @PUT("api/v1/user")
    suspend fun updateUser(@Body user: UserResult): Response<UserResult>
}
