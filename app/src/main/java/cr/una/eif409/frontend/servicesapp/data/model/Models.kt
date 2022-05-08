package cr.una.eif409.frontend.servicesapp.data.model

import com.google.gson.annotations.SerializedName

data class UserLogin(
    @SerializedName("user_name") val username: String? = null,
    @SerializedName("password") val password: String? = null,
)

data class UserSignup(
    val name: String? = null,
    val lastName: String? = null,
    val email: String? = null,
    val phoneNumber: String? = null,
    val password: String? = null,
    val passwordConfirmation: String? = null,
)

// TODO: Add missing properties
data class UserResult(
    @SerializedName("user_name") val username: String? = null,
    @SerializedName("password") val password: String? = null,
    @SerializedName("reset_password_token") val resetPasswordToken: String? = null,
)

data class ServiceInput(
    val title: String? = null,
    val type: String? = null,
    val price: String? = null,
    val description: String? = null,
)

// TODO: Add missing properties
data class ServiceResult(
    val id: Int? = null,
)
