package cr.una.eif409.frontend.servicesapp.data.repository

import cr.una.eif409.frontend.servicesapp.data.model.ServiceInput
import cr.una.eif409.frontend.servicesapp.data.network.ServiceService

class ServiceRepository {
    private val serviceService = ServiceService()

    suspend fun getServices() = serviceService.getServices()

    suspend fun createService(serviceInput: ServiceInput) =
        serviceService.createService(serviceInput)

    suspend fun getServicesByUserName(userName: String) =
        serviceService.getServicesByUserName(userName)

    suspend fun deleteService(serviceId: Long) = serviceService.deleteService(serviceId)
}