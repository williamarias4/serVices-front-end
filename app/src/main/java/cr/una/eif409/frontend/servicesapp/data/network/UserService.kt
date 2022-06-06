package cr.una.eif409.frontend.servicesapp.data.network

import cr.una.eif409.frontend.servicesapp.core.RetrofitBuilder
import cr.una.eif409.frontend.servicesapp.core.ServiceResponse
import cr.una.eif409.frontend.servicesapp.data.model.UserResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserService {
    private val retrofit = RetrofitBuilder.getRetrofitInstance()
    private val userApiClient = retrofit.create(UserApiClient::class.java)

    suspend fun getUserByUserName(username: String): ServiceResponse<UserResult> =
        withContext(Dispatchers.IO) {
            try {
                val response = userApiClient.getUserByUserName(username)

                when (response.isSuccessful) {
                    true -> ServiceResponse.Success(response.body()!!)
                    false -> ServiceResponse.Error(Exception("No se pudo obtener el usuario"))
                }
            } catch (e: Exception) {
                ServiceResponse.Error(e)
            }
        }

    suspend fun updateUser(user: UserResult): ServiceResponse<UserResult> =
        withContext(Dispatchers.IO) {
            try {
                val response = userApiClient.updateUser(user)

                when (response.isSuccessful) {
                    true -> ServiceResponse.Success(response.body()!!)
                    false -> ServiceResponse.Error(Exception("No se pudo actualizar el usuario"))
                }
            } catch (e: Exception) {
                ServiceResponse.Error(e)
            }
        }
}
