package ru.sicampus.bootcamp2025.ui.ogranizations.list

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import ru.sicampus.bootcamp2025.R
import ru.sicampus.bootcamp2025.databinding.FragmentMainPageBinding
import ru.sicampus.bootcamp2025.ui.ogranizations.detail.OrganizationDetailFragment

import ru.sicampus.bootcamp2025.util.collectWithLifecycle

class OrganizationListFragment : Fragment(R.layout.fragment_main_page) {
    private var _binding: FragmentMainPageBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<OrganizationListViewModel>{ OrganizationListViewModel.Factory }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = FragmentMainPageBinding.bind(view)
        val recyclerView = binding.content
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Передаем элемент организации в адаптер
        val adapter = OrganizationListAdapter { organization ->
            val fragment = OrganizationDetailFragment.newInstance(organization) // создаем новый фрагмент с передачей данных
            findNavController().navigate(R.id.action_mainPageFragment_to_organizationDetailFragment, fragment.arguments)
            Log.d("OrganizationDetail", "Received organization: $organization")
        }

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
