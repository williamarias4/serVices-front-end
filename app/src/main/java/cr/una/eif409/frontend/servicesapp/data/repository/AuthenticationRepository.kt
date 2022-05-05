package cr.una.eif409.frontend.servicesapp.data.repository

import cr.una.eif409.frontend.servicesapp.data.model.UserLogin
import cr.una.eif409.frontend.servicesapp.data.model.UserSignup
import cr.una.eif409.frontend.servicesapp.data.network.AuthenticationService

class AuthenticationRepository {
    private val authenticationService = AuthenticationService()

    suspend fun login(userLogin: UserLogin) = authenticationService.login(userLogin)
    suspend fun signup(userSignup: UserSignup) = authenticationService.signup(userSignup)
}
