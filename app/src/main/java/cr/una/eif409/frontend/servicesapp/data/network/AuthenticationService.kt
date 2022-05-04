package cr.una.eif409.frontend.servicesapp.data.network

import cr.una.eif409.frontend.servicesapp.core.RetrofitBuilder
import cr.una.eif409.frontend.servicesapp.core.ServiceResponse
import cr.una.eif409.frontend.servicesapp.data.model.UserLogin
import cr.una.eif409.frontend.servicesapp.data.model.UserRegister
import cr.una.eif409.frontend.servicesapp.data.model.UserResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AuthenticationService {
    private val retrofit = RetrofitBuilder.getRetrofitInstance()
    private val authenticationApiClient = retrofit.create(AuthenticationApiClient::class.java)

    suspend fun login(userLogin: UserLogin): ServiceResponse<UserResult> =
        withContext(Dispatchers.IO) {
            try {
                val response = authenticationApiClient.login(userLogin)
                if (response.isSuccessful) {
                    ServiceResponse.Success(response.body()!!)
                } else {
                    ServiceResponse.Error(Exception("Usuario o contraseña incorrectos"))
                }
            } catch (e: Exception) {
                ServiceResponse.Error(e)
            }
        }

    suspend fun register(userRegister: UserRegister): UserResult? {
        return withContext(Dispatchers.IO) {
            val response = authenticationApiClient.register(userRegister)
            response.body()
        }
    }
}
