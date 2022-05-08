package cr.una.eif409.frontend.servicesapp.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cr.una.eif409.frontend.servicesapp.core.ServiceResponse
import cr.una.eif409.frontend.servicesapp.data.model.ServiceInput
import cr.una.eif409.frontend.servicesapp.data.repository.ServiceRepository
import kotlinx.coroutines.launch

class NewServiceViewModel : ViewModel() {
    // Repository instance
    private val serviceRepository = ServiceRepository()

    // Data binding variables
    var title: MutableLiveData<String> = MutableLiveData()
    var type: MutableLiveData<String> = MutableLiveData()
    var price: MutableLiveData<String> = MutableLiveData()
    var description: MutableLiveData<String> = MutableLiveData()

    // Variables to communicate with the view
    var serviceInput: MutableLiveData<ServiceInput> = MutableLiveData()
    var registered: MutableLiveData<Boolean> = MutableLiveData()
    var errorMessage: MutableLiveData<String> = MutableLiveData()

    fun onSave() {
        serviceInput.value = ServiceInput(
            title.value,
            type.value,
            price.value,
            description.value
        )

        if (isServiceInputValid()) saveService() else return
    }
    
    private fun isServiceInputValid(): Boolean {
        val service = serviceInput.value ?: return false

        return !(service.title.isNullOrBlank() ||
                service.type.isNullOrBlank() ||
                service.type.isNullOrBlank() ||
                service.price.isNullOrBlank() ||
                service.description.isNullOrBlank())
    }

    private fun saveService() {
        viewModelScope.launch {
            when (val result = serviceRepository.createService(serviceInput.value!!)) {
                is ServiceResponse.Success -> {
                    registered.postValue(true)
                }
                is ServiceResponse.Error -> {
                    registered.postValue(false)
                    errorMessage.postValue(result.exception.message)
                }
            }
        }
    }
}