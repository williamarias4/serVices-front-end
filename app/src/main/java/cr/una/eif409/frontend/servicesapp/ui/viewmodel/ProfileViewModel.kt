package cr.una.eif409.frontend.servicesapp.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.auth0.android.jwt.JWT
import cr.una.eif409.frontend.servicesapp.core.ServiceResponse
import cr.una.eif409.frontend.servicesapp.core.SharedApp
import cr.una.eif409.frontend.servicesapp.data.model.UserResult
import cr.una.eif409.frontend.servicesapp.data.repository.UserRepository
import kotlinx.coroutines.launch

class ProfileViewModel : ViewModel() {
    // Repository for retrieving data
    private val userRepository = UserRepository()

    // Data binding variables
    var fullName = MutableLiveData<String>()
    var username = MutableLiveData<String>()
    var email = MutableLiveData<String>()
    var phoneNumber = MutableLiveData<String>()
    var province = MutableLiveData<String>()
    var address = MutableLiveData<String>()

    private var user = MutableLiveData<UserResult>()

    // Variables to communicate with the view
    var message: MutableLiveData<String> = MutableLiveData()

    fun getLoggedUser() {
        val jwt = JWT(SharedApp.preferences.token)
        val usernameFromJwt = jwt.subject ?: ""

        viewModelScope.launch {
            when (val result = userRepository.getUserByUserName(usernameFromJwt)) {
                is ServiceResponse.Success -> {
                    val loggedUser = result.data

                    user.postValue(loggedUser)
                    fullName.postValue(loggedUser.person?.fullName ?: "")
                    username.postValue(loggedUser.username ?: "")
                    email.postValue(loggedUser.person?.email ?: "")
                    phoneNumber.postValue(loggedUser.person?.phoneNumber ?: "")
                    province.postValue(loggedUser.person?.province ?: "")
                    address.postValue(loggedUser.person?.address ?: "")
                }
                is ServiceResponse.Error -> {
                    message.postValue(result.exception.message)
                }
            }
        }
    }

    fun onSaveProfile() {
        buildUser()

        viewModelScope.launch {
            when (userRepository.updateUser(user.value!!)) {
                is ServiceResponse.Success -> {
                    message.postValue("Perfil actualizado correctamente")
                }
                is ServiceResponse.Error -> {
                    message.postValue("No se pudo actualizar el perfil")
                }
            }
        }
    }

    private fun buildUser() {
        user.value?.person?.fullName = fullName.value
        user.value?.username = username.value
        user.value?.person?.email = email.value
        user.value?.person?.phoneNumber = phoneNumber.value
        user.value?.person?.province = province.value
        user.value?.person?.address = address.value
    }
}