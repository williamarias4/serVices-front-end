package cr.una.eif409.frontend.servicesapp.data.repository

import cr.una.eif409.frontend.servicesapp.data.model.UserResult
import cr.una.eif409.frontend.servicesapp.data.network.UserService

class UserRepository {
    private val userService = UserService()

    suspend fun getUserByUserName(username: String) = userService.getUserByUserName(username)

    suspend fun updateUser(user: UserResult) = userService.updateUser(user)
}