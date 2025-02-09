package ru.sicampus.bootcamp2025.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ru.sicampus.bootcamp2025.R
import ru.sicampus.bootcamp2025.databinding.FragmentMainPageBinding

class MainPageFragment : Fragment(R.layout.fragment_main_page) {
    private var _binding: FragmentMainPageBinding? = null
    private val binding get() = _binding!!
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = FragmentMainPageBinding.bind(view)

        // Навигационные кнопки
//        binding.menuProfile.setOnClickListener {
//            findNavController().navigate(R.id.action_mainPageFragment_to_profileFragment)
//        }
//        binding.menuVolunteers.setOnClickListener {
//            findNavController().navigate(R.id.action_mainPageFragment_to_freeVolunteersListFragment)
//        }
//
//        // Адаптер
//        val adapter = OrganizationAdapter()
//        binding.refreshBtn.setOnClickListener { adapter.refresh() }
//
//        // Настройка адаптера
//        binding.content.adapter = adapter
//        viewModel.listState.collectWithLifecycle(viewLifecycleOwner) { data ->
//            adapter.submitData(data)
//        }
//
//        // Слушаем состояние загрузки
//        adapter.loadStateFlow.collectWithLifecycle(viewLifecycleOwner) { loadState ->
//            val state = loadState.refresh
//            binding.error.visibility = if (state is LoadState.Error) View.VISIBLE else View.GONE
//            binding.loading.visibility = if (state is LoadState.Loading) View.VISIBLE else View.GONE
//
//            if (state is LoadState.Error) {
//                binding.errorText.text = state.error.message.toString()
//            }
       // }
    }
    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}