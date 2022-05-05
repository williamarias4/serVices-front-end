package cr.una.eif409.frontend.servicesapp.data.network

import cr.una.eif409.frontend.servicesapp.core.RetrofitBuilder
import cr.una.eif409.frontend.servicesapp.core.ServiceResponse
import cr.una.eif409.frontend.servicesapp.data.model.UserLogin
import cr.una.eif409.frontend.servicesapp.data.model.UserResult
import cr.una.eif409.frontend.servicesapp.data.model.UserSignup
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AuthenticationService {
    private val retrofit = RetrofitBuilder.getRetrofitInstance()
    private val authenticationApiClient = retrofit.create(AuthenticationApiClient::class.java)

    suspend fun login(userLogin: UserLogin): ServiceResponse<UserResult> =
        withContext(Dispatchers.IO) {
            try {
                val response = authenticationApiClient.login(userLogin)

                when (response.isSuccessful) {
                    true -> ServiceResponse.Success(response.body()!!)
                    false -> ServiceResponse.Error(Exception("Usuario o contraseña incorrectos"))
                }
            } catch (e: Exception) {
                ServiceResponse.Error(e)
            }
        }

    suspend fun signup(userSignup: UserSignup): ServiceResponse<UserResult> =
        withContext(Dispatchers.IO) {
            try {
                val response = authenticationApiClient.signup(userSignup)

                when (response.isSuccessful) {
                    true -> ServiceResponse.Success(response.body()!!)
                    false -> ServiceResponse.Error(Exception("Error al registrar el usuario"))
                }
            } catch (e: Exception) {
                ServiceResponse.Error(e)
            }
        }
}
