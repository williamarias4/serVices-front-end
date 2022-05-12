package cr.una.eif409.frontend.servicesapp.data.network

import cr.una.eif409.frontend.servicesapp.data.model.ServiceInput
import cr.una.eif409.frontend.servicesapp.data.model.ServiceDetails
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ServiceApiClient {
    @GET("api/v1/job")
    suspend fun getServices(): Response<ArrayList<ServiceDetails>>

    @POST("api/v1/job")
    suspend fun createService(@Body serviceInput: ServiceInput): Response<ServiceDetails>
}
