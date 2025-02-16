package ru.sicampus.bootcamp2025.ui.login

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.launch
import ru.sicampus.bootcamp2025.R
import ru.sicampus.bootcamp2025.data.UserDataStoreManager
import ru.sicampus.bootcamp2025.data.login.LoginNetworkDataSource
import ru.sicampus.bootcamp2025.databinding.FragmentLoginBinding

class LoginFragment : Fragment(R.layout.fragment_login) {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<LoginViewModel> { LoginViewModel.Factory }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = FragmentLoginBinding.bind(view)

        binding.loginBtn.setOnClickListener {
            val email = binding.email.text.toString().trim()
            val password = binding.password.text.toString().trim()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                viewModel.login(email, password)
            } else {
                Toast.makeText(requireContext(), "Введите email и пароль", Toast.LENGTH_SHORT).show()
            }
        }


        val textWatcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                binding.loginBtn.isEnabled = binding.email.text.isNotBlank() && binding.password.text.isNotBlank()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        }
        with(binding){
            email.addTextChangedListener(textWatcher)
            password.addTextChangedListener(textWatcher)
        }
        binding.backArrow.setOnClickListener{
            findNavController().popBackStack()
        }

        lifecycleScope.launch {
            viewModel.state.collect { state ->
                when (state) {
                    is LoginViewModel.LoginState.Loading -> {
                        _binding?.let {
                            it.loginBtn.isEnabled = false
                        }

                    }
                    is LoginViewModel.LoginState.Success -> {
                        _binding?.let {
                            findNavController().navigate(R.id.action_loginFragment_to_mainPageFragment)
                        }

                    }
                    is LoginViewModel.LoginState.Error -> {
                        _binding?.let {
                            it.loginBtn.isEnabled = true
                            Toast.makeText(requireContext(), state.message, Toast.LENGTH_SHORT).show()
                            println(state.message)
                        }

                    }
                    else -> Unit
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}