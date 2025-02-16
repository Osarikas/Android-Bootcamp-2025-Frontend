package ru.sicampus.bootcamp2025.ui.profile.edit

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.sicampus.bootcamp2025.R
import ru.sicampus.bootcamp2025.databinding.FragmentEditProfileBinding
import ru.sicampus.bootcamp2025.util.collectWithLifecycle
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class EditProfileFragment : Fragment(R.layout.fragment_edit_profile) {
    private var _binding: FragmentEditProfileBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<EditProfileViewModel>{ EditProfileViewModel.Factory }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = FragmentEditProfileBinding.bind(view)

        binding.editTextDate.apply {
            isFocusable = false
            isClickable = true
            setOnClickListener { showDatePickerDialog() }
        }
        binding.save.setOnClickListener{
            viewModel.clickSave(
                fullName = getTextOrNull(binding.editFullName),
                date = stringToDate(getTextOrNull(binding.editTextDate)),
                phone = getTextOrNull(binding.editPhone),
                email = getTextOrNull(binding.editEmail),
                telegram = getTextOrNull(binding.editTelegram),
                about = getTextOrNull(binding.editAbout)
            )
            viewModel.state.collectWithLifecycle(this){ state ->
                println(state)
                if(state is EditProfileViewModel.State.Success){
                    findNavController().popBackStack()
                }
            }

        }
        binding.arrowBack.setOnClickListener{
            findNavController().popBackStack()
        }
    }
    fun getTextOrNull(editText: EditText): String? {
        return if (editText.text.isNullOrBlank()) null else editText.text.toString()
    }

    fun stringToDate(dateString: String?): Date? {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        if(dateString != null){
            return try {
                dateFormat.parse(dateString)
            } catch (_: Exception) {
                null
            }
        }
        else{
            return null
        }

    }


    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        DatePickerDialog(
            requireContext(),
            { _, selectedYear, selectedMonth, selectedDay ->
                val selectedCalendar = Calendar.getInstance().apply {
                    set(selectedYear, selectedMonth, selectedDay)
                }

                val formattedDate = SimpleDateFormat("yyyy-MM-dd", Locale.US).format(selectedCalendar.time)

                binding.editTextDate.setText(formattedDate)
            },
            year, month, day
        ).show()
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}