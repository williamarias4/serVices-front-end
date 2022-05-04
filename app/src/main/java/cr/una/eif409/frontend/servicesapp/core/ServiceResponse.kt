package cr.una.eif409.frontend.servicesapp.core

sealed class ServiceResponse<out T> {
    data class Success<out T>(val data: T) : ServiceResponse<T>()
    data class Error(val exception: Exception) : ServiceResponse<Nothing>()
}