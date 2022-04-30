package cr.una.eif409.frontend.servicesapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import cr.una.eif409.frontend.servicesapp.model.UserLogin

class LoginViewModel : ViewModel() {
    var email: MutableLiveData<String> = MutableLiveData()
    var password: MutableLiveData<String> = MutableLiveData()
    var userLogin: MutableLiveData<UserLogin> = MutableLiveData()
    var isValidUser: MutableLiveData<Boolean> = MutableLiveData()

    fun onLoginClicked() {
        userLogin.value = UserLogin(email.value, password.value)

        if (email.value.isNullOrBlank() || password.value.isNullOrBlank()) return

        // This is for testing purposes
        isValidUser.value = email.value == "admin" && password.value == "admin"
    }
}