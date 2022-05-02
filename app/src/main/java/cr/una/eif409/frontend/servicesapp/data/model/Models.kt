package cr.una.eif409.frontend.servicesapp.data.model

import com.google.gson.annotations.SerializedName

data class UserLogin(
    @SerializedName("user_name") val username: String? = null,
    @SerializedName("password") val password: String? = null,
)

data class UserRegister(
    val name: String? = null,
    val firstLastName: String? = null,
    val secondLastName: String? = null,
    val email: String? = null,
    val phoneNumber: String? = null,
    val password: String? = null,
    val passwordConfirmation: String? = null,
)

data class UserResult(
    @SerializedName("user_name") val username: String? = null,
    @SerializedName("password") val password: String? = null,
    @SerializedName("reset_password_token") val resetPasswordToken: String? = null,
)
