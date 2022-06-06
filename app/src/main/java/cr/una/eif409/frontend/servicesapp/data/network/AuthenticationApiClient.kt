package cr.una.eif409.frontend.servicesapp.data.network

import cr.una.eif409.frontend.servicesapp.data.model.LoginResponse
import cr.una.eif409.frontend.servicesapp.data.model.UserLogin
import cr.una.eif409.frontend.servicesapp.data.model.UserResult
import cr.una.eif409.frontend.servicesapp.data.model.UserSignup
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthenticationApiClient {
    @POST("api/v1/user/authenticate")
    suspend fun login(@Body userLogin: UserLogin): Response<LoginResponse>

    @POST("api/v1/user/register")
    suspend fun signup(@Body userSignup: UserSignup): Response<UserResult>
}
