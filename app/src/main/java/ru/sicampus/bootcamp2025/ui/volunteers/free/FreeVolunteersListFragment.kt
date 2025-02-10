package ru.sicampus.bootcamp2025.ui.volunteers.free

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.sicampus.bootcamp2025.R
import ru.sicampus.bootcamp2025.databinding.FragmentFreeVolunteersListBinding
import ru.sicampus.bootcamp2025.util.collectWithLifecycle

class FreeVolunteersListFragment : Fragment(R.layout.fragment_free_volunteers_list){
    private var _binding : FragmentFreeVolunteersListBinding? = null
    private val binding : FragmentFreeVolunteersListBinding get() = _binding!!

    private val viewModel by viewModels<FreeVolunteersListViewModel>{FreeVolunteersListViewModel.Factory}
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = FragmentFreeVolunteersListBinding.bind(view)
        binding.refreshBtn.setOnClickListener{ viewModel.clickRefresh() }
        val adapter = FreeVolunteersAdapter()
        binding.content.adapter = adapter

        binding.menuHome.setOnClickListener{ findNavController().navigate(R.id.action_freeVolunteersListFragment_to_mainPageFragment) }
        binding.menuProfile.setOnClickListener{ findNavController().navigate(R.id.action_freeVolunteersListFragment_to_profileFragment) }
        viewModel.state.collectWithLifecycle(this){ state ->
            binding.error.visibility = if(state is FreeVolunteersListViewModel.State.Error) View.VISIBLE else View.GONE
            binding.loading.visibility = if(state is FreeVolunteersListViewModel.State.Loading) View.VISIBLE else View.GONE
            binding.content.visibility = if(state is FreeVolunteersListViewModel.State.Show) View.VISIBLE else View.GONE

            when(state) {
                is FreeVolunteersListViewModel.State.Loading -> Unit
                is FreeVolunteersListViewModel.State.Show -> {
                    adapter.submitList(state.items.toList())
                }
                is FreeVolunteersListViewModel.State.Error -> {
                    binding.errorText.text = state.text
                }
            }
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}