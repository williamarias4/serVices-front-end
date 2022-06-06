package cr.una.eif409.frontend.servicesapp.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.auth0.android.jwt.JWT
import cr.una.eif409.frontend.servicesapp.core.ServiceResponse
import cr.una.eif409.frontend.servicesapp.core.SharedApp
import cr.una.eif409.frontend.servicesapp.data.model.ServiceInput
import cr.una.eif409.frontend.servicesapp.data.model.UserResult
import cr.una.eif409.frontend.servicesapp.data.repository.ServiceRepository
import cr.una.eif409.frontend.servicesapp.data.repository.UserRepository
import kotlinx.coroutines.launch
import java.math.BigDecimal

class NewServiceViewModel : ViewModel() {
    // Repository instance
    private val serviceRepository = ServiceRepository()

    private val userRepository = UserRepository()

    // Data binding variables
    var title: MutableLiveData<String> = MutableLiveData()
    var category: MutableLiveData<String> = MutableLiveData()
    var price: MutableLiveData<String> = MutableLiveData()
    var description: MutableLiveData<String> = MutableLiveData()

    // Variables to communicate with the view
    var serviceInput: MutableLiveData<ServiceInput> = MutableLiveData()
    var registered: MutableLiveData<Boolean> = MutableLiveData()
    var errorMessage: MutableLiveData<String> = MutableLiveData()

    fun onSave() {
        val parsedPrice = if (price.value != null) BigDecimal(price.value) else null

        val username = JWT(SharedApp.preferences.token).subject ?: ""

        viewModelScope.launch {
            when (val result = userRepository.getUserByUserName(username)) {
                is ServiceResponse.Success -> {
                    serviceInput.value = ServiceInput(
                        title.value,
                        description.value,
                        category.value,
                        parsedPrice,
                        true,
                        result.data,
                    )
                }
                is ServiceResponse.Error -> {
                    errorMessage.postValue(result.exception.message)
                }
            }
        }

        if (isServiceInputValid()) saveService() else return
    }

    private fun isServiceInputValid(): Boolean {
        val service = serviceInput.value ?: return false

        return !(service.title.isNullOrBlank() ||
                service.description.isNullOrBlank() ||
                service.category.isNullOrBlank() ||
                service.price == null || service.price!! < BigDecimal.ZERO
                )
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