package cr.una.eif409.frontend.servicesapp.core

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder {
    /**
     * If you are running the app on the emulator, you have to change the BASE_URL
     * to 10.0.2.2:8080/ in order to connect to the localhost server.
     *
     * If you are running the app on a physical device, you have to change the BASE_URL
     * to the IP address of your computer plus the port (8080), like the example below.
     */
    private const val BASE_URL = "http://192.168.0.10:8080/"

    fun getRetrofitInstance(): Retrofit {
        return Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}
