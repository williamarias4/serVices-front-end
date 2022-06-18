package cr.una.eif409.frontend.servicesapp.data.network

import cr.una.eif409.frontend.servicesapp.data.model.ServiceDetails
import cr.una.eif409.frontend.servicesapp.data.model.ServiceInput
import retrofit2.Response
import retrofit2.http.*

interface ServiceApiClient {
    @GET("api/v1/job")
    suspend fun getServices(): Response<ArrayList<ServiceDetails>>

    @GET("api/v1/job")
    suspend fun getServicesByUserName(@Query("userName") userName: String): Response<ArrayList<ServiceDetails>>

    @POST("api/v1/job")
    suspend fun createService(@Body serviceInput: ServiceInput): Response<ServiceDetails>

    @DELETE("api/v1/job/{id}")
    suspend fun deleteService(@Path("id") id: Long): Response<Void>
}
