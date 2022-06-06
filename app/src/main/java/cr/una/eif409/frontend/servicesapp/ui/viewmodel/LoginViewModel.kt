package cr.una.eif409.frontend.servicesapp.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cr.una.eif409.frontend.servicesapp.core.ServiceResponse
import cr.una.eif409.frontend.servicesapp.core.SharedApp
import cr.una.eif409.frontend.servicesapp.data.model.UserLogin
import cr.una.eif409.frontend.servicesapp.data.repository.AuthenticationRepository
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    // Repository for retrieving data
    private val authenticationRepository = AuthenticationRepository()

    // Data binding variables
    var username: MutableLiveData<String> = MutableLiveData()
    var password: MutableLiveData<String> = MutableLiveData()

    // Variables to communicate with the view
    var userLogin: MutableLiveData<UserLogin> = MutableLiveData()
    var isValidUser: MutableLiveData<Boolean> = MutableLiveData()
    var errorMessage: MutableLiveData<String> = MutableLiveData()

    fun onLoginClicked() {
        userLogin.value = UserLogin(username.value, password.value)

        if (username.value.isNullOrBlank() || password.value.isNullOrBlank()) return

        // Execute login request
        viewModelScope.launch {
            when (val result = authenticationRepository.login(userLogin.value!!)) {
                is ServiceResponse.Success -> {
                    isValidUser.value = true
                    SharedApp.preferences.token = result.data.token!!.split("token=")[0]
                }
                is ServiceResponse.Error -> {
                    isValidUser.value = false
                    errorMessage.postValue(result.exception.message)
                }
            }
        }
    }
}
