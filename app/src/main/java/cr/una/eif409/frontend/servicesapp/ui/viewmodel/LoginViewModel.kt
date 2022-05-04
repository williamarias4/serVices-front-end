package cr.una.eif409.frontend.servicesapp.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    fun onLoginClicked() {
        userLogin.value = UserLogin(username.value, password.value)

        if (username.value.isNullOrBlank() || password.value.isNullOrBlank()) return

        // Execute login request
        viewModelScope.launch {
            isValidUser.value = authenticationRepository.login(userLogin.value!!) != null

            if (isValidUser.value!!) {
                SharedApp.preferences.username = username.value!!
            }
        }
    }
}
