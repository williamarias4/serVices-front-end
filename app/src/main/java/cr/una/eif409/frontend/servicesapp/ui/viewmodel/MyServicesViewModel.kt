package cr.una.eif409.frontend.servicesapp.ui.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.auth0.android.jwt.JWT
import cr.una.eif409.frontend.servicesapp.core.ServiceResponse
import cr.una.eif409.frontend.servicesapp.core.SharedApp
import cr.una.eif409.frontend.servicesapp.data.model.ServiceDetails
import cr.una.eif409.frontend.servicesapp.data.model.ServiceInput
import cr.una.eif409.frontend.servicesapp.data.repository.ServiceRepository
import cr.una.eif409.frontend.servicesapp.data.repository.UserRepository
import kotlinx.coroutines.launch
import java.math.BigDecimal

class MyServicesViewModel : ViewModel() {
    private val serviceRepository = ServiceRepository()
    var serviceList: MutableLiveData<ArrayList<ServiceDetails>> = MutableLiveData()
    var selectedService: MutableLiveData<ServiceDetails> = MutableLiveData()
    var response: MutableLiveData<Boolean> = MutableLiveData(false)
    var message: MutableLiveData<String> = MutableLiveData()
    var deleted: MutableLiveData<Boolean> = MutableLiveData(false)
    var updated: MutableLiveData<Boolean> = MutableLiveData(false)

    var price: MutableLiveData<String> = MutableLiveData()

    fun getServices() {
        val jwt = JWT(SharedApp.preferences.token)
        val usernameFromJwt = jwt.subject ?: ""

        viewModelScope.launch {
            when (val result = serviceRepository.getServicesByUserName(usernameFromJwt)) {
                is ServiceResponse.Success -> {
                    response.value = true
                    result.data.reverse()
                    serviceList.value = result.data
                }
                is ServiceResponse.Error -> {
                    response.value = true
                    message.value = "Error al cargar los servicios"
                }
            }
        }
    }

    fun onDelete() {
        val serviceId = selectedService.value?.id ?: return

        viewModelScope.launch {
            when (serviceRepository.deleteService(serviceId)) {
                is ServiceResponse.Success -> {
                    deleted.value = true
                    message.value = "Servicio eliminado correctamente"
                }
                is ServiceResponse.Error -> {
                    message.value = "Error al eliminar el servicio"
                }
            }
        }
    }

    fun onSave() {
        val parsedPrice = if (price.value != null) BigDecimal(price.value) else null
        selectedService.value?.price = parsedPrice

        viewModelScope.launch {
            when (val result = serviceRepository.updateService(selectedService.value!!)) {
                is ServiceResponse.Success -> {
                    updated.postValue(true)
                    message.postValue("Servicio actualizado correctamente")
                }
                is ServiceResponse.Error -> {
                    updated.postValue(false)
                    message.postValue(result.exception.message)
                }
            }
        }
    }
}
