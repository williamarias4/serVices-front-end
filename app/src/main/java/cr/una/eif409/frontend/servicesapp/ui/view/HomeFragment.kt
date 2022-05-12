package cr.una.eif409.frontend.servicesapp.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import cr.una.eif409.frontend.servicesapp.R
import cr.una.eif409.frontend.servicesapp.databinding.FragmentHomeBinding
import cr.una.eif409.frontend.servicesapp.ui.adapter.ServicesAdapter
import cr.una.eif409.frontend.servicesapp.ui.viewmodel.HomeViewModel

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var adapter: ServicesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.homeViewModel = viewModel

        setUpRecyclerView()
        setUpSwiperContainer()
        listenForEvents()

        return binding.root
    }

    private fun setUpRecyclerView() {
        val recyclerView = binding.fragmentHomeRecyclerView
        adapter = ServicesAdapter(viewModel.serviceList.value ?: arrayListOf())
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
            adapter.clear()
            adapter.addAll(it)
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

    private fun showMessage(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}
