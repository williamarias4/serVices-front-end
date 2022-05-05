package cr.una.eif409.frontend.servicesapp.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cr.una.eif409.frontend.servicesapp.core.ServiceResponse
import cr.una.eif409.frontend.servicesapp.data.model.UserSignup
import cr.una.eif409.frontend.servicesapp.data.repository.AuthenticationRepository
import kotlinx.coroutines.launch

class RegisterViewModel : ViewModel() {
    // Repository for retrieving data
    private val authenticationRepository = AuthenticationRepository()

    // Data binding variables
    var name: MutableLiveData<String> = MutableLiveData()
    var lastName: MutableLiveData<String> = MutableLiveData()
    var email: MutableLiveData<String> = MutableLiveData()
    var phoneNumber: MutableLiveData<String> = MutableLiveData()
    var password: MutableLiveData<String> = MutableLiveData()
    var passwordConfirmation: MutableLiveData<String> = MutableLiveData()

    // Variables to communicate with the view
    var userSignup: MutableLiveData<UserSignup> = MutableLiveData()
    var registered: MutableLiveData<Boolean> = MutableLiveData()
    var errorMessage: MutableLiveData<String> = MutableLiveData()

    fun onSignupButtonClicked() {
        buildUserSignupObject()

        if (isUserSignupObjectOk()) saveUser() else return
    }

    private fun buildUserSignupObject() {
        userSignup.value = UserSignup(
            name.value,
            lastName.value,
            email.value,
            phoneNumber.value,
            password.value,
            passwordConfirmation.value
        )
    }

    private fun isUserSignupObjectOk(): Boolean {
        val user = userSignup.value ?: return false

        return !(user.name.isNullOrBlank() ||
                user.lastName.isNullOrBlank() ||
                user.email.isNullOrBlank() ||
                user.phoneNumber.isNullOrBlank() ||
                user.password.isNullOrBlank() ||
                user.passwordConfirmation.isNullOrBlank() ||
                user.password != passwordConfirmation.value)
    }

    private fun saveUser() {
        viewModelScope.launch {
            when (val result = authenticationRepository.signup(userSignup.value!!)) {
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