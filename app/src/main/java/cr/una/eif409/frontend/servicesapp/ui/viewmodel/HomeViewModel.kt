package cr.una.eif409.frontend.servicesapp.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cr.una.eif409.frontend.servicesapp.core.ServiceResponse
import cr.una.eif409.frontend.servicesapp.data.model.ServiceDetails
import cr.una.eif409.frontend.servicesapp.data.repository.ServiceRepository
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    private val serviceRepository = ServiceRepository()
    var serviceList: MutableLiveData<ArrayList<ServiceDetails>> = MutableLiveData()
    var response: MutableLiveData<Boolean> = MutableLiveData(false)
    var message: MutableLiveData<String> = MutableLiveData()

    init {
        getServices()
    }

    fun getServices() {
        viewModelScope.launch {
            when (val result = serviceRepository.getServices()) {
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
}