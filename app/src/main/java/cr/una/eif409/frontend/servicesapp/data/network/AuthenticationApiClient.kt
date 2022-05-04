package cr.una.eif409.frontend.servicesapp.data.network

import cr.una.eif409.frontend.servicesapp.data.model.UserLogin
import cr.una.eif409.frontend.servicesapp.data.model.UserRegister
import cr.una.eif409.frontend.servicesapp.data.model.UserResult
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthenticationApiClient {
    @POST("api/v1/user/login")
    suspend fun login(@Body userLogin: UserLogin): Response<UserResult>

    @POST("/signup")
    suspend fun register(@Body userRegister: UserRegister): Response<UserResult>
}
