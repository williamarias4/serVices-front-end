package cr.una.eif409.frontend.servicesapp.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import cr.una.eif409.frontend.servicesapp.R
import cr.una.eif409.frontend.servicesapp.data.model.ServiceDetails
import cr.una.eif409.frontend.servicesapp.databinding.FragmentMyServicesBinding
import cr.una.eif409.frontend.servicesapp.ui.adapter.ServicesAdapter
import cr.una.eif409.frontend.servicesapp.ui.viewmodel.MyServicesViewModel

class MyServicesFragment : Fragment() {
    private lateinit var binding: FragmentMyServicesBinding
    private val viewModel: MyServicesViewModel by activityViewModels()
    private lateinit var adapter: ServicesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_services, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.myServicesViewModel = viewModel

        setUpRecyclerView()
        setUpSwiperContainer()
        listenForEvents()
        getServices()

        return binding.root
    }

    private fun setUpRecyclerView() {
        val recyclerView = binding.fragmentMyServicesRecyclerView
        adapter = ServicesAdapter(viewModel.serviceList.value ?: arrayListOf()) {
            viewModel.selectedService.postValue(it)
            findNavController().navigate(R.id.action_myServicesFragment_to_editServiceFragment)
        }
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)
    }

    private fun setUpSwiperContainer() {
        binding.swipeContainer.setOnRefreshListener {
            viewModel.getServices()
        }
    }

    private fun listenForEvents() {
        viewModel.serviceList.observe(viewLifecycleOwner) {
            val services = ArrayList<ServiceDetails>(it)
            adapter.clear()
            adapter.addAll(services)
        }

        viewModel.response.observe(viewLifecycleOwner) {
            if (it) binding.swipeContainer.isRefreshing = false
        }

        viewModel.message.observe(viewLifecycleOwner) {
            if (it != null) {
                showMessage(it)
                viewModel.message.value = null
            }
        }
    }

    private fun getServices() {
        viewModel.getServices()
    }

    private fun showMessage(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}