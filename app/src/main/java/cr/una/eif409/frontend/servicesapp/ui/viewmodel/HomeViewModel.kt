package cr.una.eif409.frontend.servicesapp.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cr.una.eif409.frontend.servicesapp.core.ServiceResponse
import cr.una.eif409.frontend.servicesapp.data.model.ServiceResult
import cr.una.eif409.frontend.servicesapp.data.repository.ServiceRepository
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    private val serviceRepository = ServiceRepository()
    var serviceList: ArrayList<ServiceResult> = ArrayList()
    var message: MutableLiveData<String> = MutableLiveData()

    init {
        viewModelScope.launch {
            when (val result = serviceRepository.getServices()) {
                is ServiceResponse.Success -> {
                    serviceList = result.data
                }
                is ServiceResponse.Error -> {
                    message.value = "Error al cargar los servicios"

                }
            }
        }
    }
}