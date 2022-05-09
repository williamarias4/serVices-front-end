package cr.una.eif409.frontend.servicesapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import cr.una.eif409.frontend.servicesapp.R
import cr.una.eif409.frontend.servicesapp.data.model.ServiceDetails

class ServicesAdapter(private val serviceList: ArrayList<ServiceDetails>) :
    RecyclerView.Adapter<ServicesAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val publisher: TextView = itemView.findViewById(R.id.publisher_name)
        val title: TextView = itemView.findViewById(R.id.title)
        val description: TextView = itemView.findViewById(R.id.description)
        val phoneNumber: TextView = itemView.findViewById(R.id.phone_number)
        val email: TextView = itemView.findViewById(R.id.email)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater
            .from(viewGroup.context)
            .inflate(R.layout.service_item, viewGroup, false)

        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val service = serviceList[position]

        viewHolder.publisher.text = "Nombre persona"
        viewHolder.title.text = service.title
        viewHolder.description.text = service.description
        viewHolder.phoneNumber.text = "Telefono"
        viewHolder.email.text = "Email"
    }

    override fun getItemCount() = serviceList.size
}
