package ru.sicampus.bootcamp2025.ui.ogranizations.list

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import ru.sicampus.bootcamp2025.R
import ru.sicampus.bootcamp2025.databinding.FragmentMainPageBinding

import ru.sicampus.bootcamp2025.util.collectWithLifecycle

class OrganizationListFragment : Fragment(R.layout.fragment_main_page) {
    private var _binding: FragmentMainPageBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<OrganizationListViewModel>{ OrganizationListViewModel.Factory }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = FragmentMainPageBinding.bind(view)

      binding.menuProfile.setOnClickListener {
          findNavController().navigate(R.id.action_mainPageFragment_to_profileFragment)
      }
      binding.menuVolunteers.setOnClickListener {
          findNavController().navigate(R.id.action_mainPageFragment_to_freeVolunteersListFragment)
      }
   val recyclerView = binding.content  // замените на ID вашего RecyclerView
   recyclerView.layoutManager = LinearLayoutManager(requireContext())
   val adapter = OrganizationListAdapter()

   binding.refreshBtn.setOnClickListener { adapter.refresh() }
   binding.content.adapter = adapter

   viewModel.listState.collectWithLifecycle(this) { data ->
       println("data $data")
       adapter.submitData(data)

   }
   adapter.loadStateFlow.collectWithLifecycle(this) { loadState ->
       val state = loadState.refresh
       binding.error.visibility = if (state is LoadState.Error) View.VISIBLE else View.GONE
       binding.loading.visibility = if (state is LoadState.Loading) View.VISIBLE else View.GONE

       if (state is LoadState.Error) {
           binding.errorText.text = state.error.message.toString()
       }
   }



    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}