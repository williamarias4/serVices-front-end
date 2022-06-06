package cr.una.eif409.frontend.servicesapp.data.model

import com.google.gson.annotations.SerializedName
import java.math.BigDecimal

data class UserLogin(
    @SerializedName("user_name")
    var username: String? = null,
    @SerializedName("password")
    var password: String? = null,
)

data class UserSignup(
    @SerializedName("user_name")
    var username: String? = null,
    var password: String? = null,
    var passwordConfirmation: String? = null,
    var person: Person? = null,
    var role: Role? = null,
)

data class LoginResponse(
    @SerializedName("jwtToken")
    var token: String? = null,
)

data class UserResult(
    var id: Long? = null,
    @SerializedName("user_name")
    var username: String? = null,
    var password: String? = null,
    @SerializedName("reset_password_token")
    var resetPasswordToken: String? = null,
    var person: Person? = null,
    var role: Role? = null,
)

data class Person(
    @SerializedName("id_number")
    var id: Long? = null,
    @SerializedName("full_name")
    var fullName: String? = null,
    var email: String? = null,
    @SerializedName("cell_phone")
    var phoneNumber: String? = null,
    var province: String? = null,
    var address: String? = null,
)

data class Role(
    var id: Long? = null,
    var type: String? = null,
)

data class ServiceInput(
    var title: String? = null,
    var description: String? = null,
    var category: String? = null,
    var price: BigDecimal? = null,
    var active: Boolean? = null,
    var publisher: UserResult? = null,
)

data class ServiceDetails(
    var id: Long? = null,
    var title: String? = null,
    var description: String? = null,
    var category: String? = null,
    var price: BigDecimal? = null,
    var active: Boolean? = null,
    var publisher: UserResult? = null,
)
