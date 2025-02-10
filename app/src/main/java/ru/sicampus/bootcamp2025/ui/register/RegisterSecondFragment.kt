package ru.sicampus.bootcamp2025.ui.register

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.launch
import ru.sicampus.bootcamp2025.R
import ru.sicampus.bootcamp2025.databinding.FragmentRegisterSecondBinding

class RegisterSecondFragment : Fragment(R.layout.fragment_register_second) {

    private var _binding: FragmentRegisterSecondBinding? = null
    private val binding: FragmentRegisterSecondBinding get() = _binding!!
    private val viewModel by activityViewModels<RegisterViewModel> { RegisterViewModel.Factory }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = FragmentRegisterSecondBinding.bind(view)

        // TextWatcher для включения кнопки регистрации
        val textWatcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                binding.registerBtn.isEnabled =
                    binding.phone.text.isNotBlank() &&
                            binding.tg.text.isNotBlank() &&
                            binding.about.text.isNotBlank()
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        }

        binding.phone.addTextChangedListener(textWatcher)
        binding.tg.addTextChangedListener(textWatcher)
        binding.about.addTextChangedListener(textWatcher)

        binding.registerBtn.setOnClickListener {
            if (binding.registerBtn.isEnabled) {
                viewModel.clickRegister(
                    binding.phone.text.toString(),
                    binding.tg.text.toString(),
                    binding.about.text.toString()
                )
                viewModel.registerUser()
            }
        }
        binding.backArrow.setOnClickListener{
            findNavController().popBackStack()
        }

        viewModel.user.observe(viewLifecycleOwner) { user ->
            if (user != null) {
                Toast.makeText(requireContext(), "Добро пожаловать, ${user.name}", Toast.LENGTH_SHORT).show()
                try {
                    findNavController().navigate(R.id.action_registerSecondFragment_to_mainPageFragment)
                } catch (e: Exception) {
                    println(e.message)
                    Toast.makeText(requireContext(), "Navigation error: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state.collect { state ->
                val enabled = state is RegisterViewModel.State.Show
                binding.phone.isEnabled = enabled
                binding.tg.isEnabled = enabled
                binding.about.isEnabled = enabled

                if (state is RegisterViewModel.State.Show && state.errorText != null) {
                    binding.errorText.visibility = View.VISIBLE
                    binding.errorText.text = state.errorText
                } else {
                    binding.errorText.visibility = View.GONE
                }
            }
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}