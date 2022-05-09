package cr.una.eif409.frontend.servicesapp.data.network

import cr.una.eif409.frontend.servicesapp.core.RetrofitBuilder
import cr.una.eif409.frontend.servicesapp.core.ServiceResponse
import cr.una.eif409.frontend.servicesapp.data.model.ServiceInput
import cr.una.eif409.frontend.servicesapp.data.model.ServiceDetails
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ServiceService {
    private val retrofit = RetrofitBuilder.getRetrofitInstance()
    private val serviceApiClient = retrofit.create(ServiceApiClient::class.java)

    suspend fun getServices(): ServiceResponse<ArrayList<ServiceDetails>> =
        withContext(Dispatchers.IO) {
            try {
                val response = serviceApiClient.getServices()

                when (response.isSuccessful) {
                    true -> ServiceResponse.Success(response.body()!!)
                    false -> ServiceResponse.Error(Exception("No se pudo obtener los servicios"))
                }
            } catch (e: Exception) {
                ServiceResponse.Error(e)
            }
        }

    suspend fun createService(serviceInput: ServiceInput): ServiceResponse<ServiceDetails> =
        withContext(Dispatchers.IO) {
            try {
                val response = serviceApiClient.createService(serviceInput)

                when (response.isSuccessful) {
                    true -> ServiceResponse.Success(response.body()!!)
                    false -> ServiceResponse.Error(Exception("No se pudo registrar el servicio"))
                }
            } catch (e: Exception) {
                ServiceResponse.Error(e)
            }
        }
}