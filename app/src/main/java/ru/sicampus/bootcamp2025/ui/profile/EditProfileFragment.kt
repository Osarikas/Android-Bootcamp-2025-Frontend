package ru.sicampus.bootcamp2025.ui.profile

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import ru.sicampus.bootcamp2025.R
import ru.sicampus.bootcamp2025.databinding.FragmentEditProfileBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class EditProfileFragment : Fragment(R.layout.fragment_edit_profile) {
    private var _binding: FragmentEditProfileBinding? = null
    private val binding get() = _binding!!
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = FragmentEditProfileBinding.bind(view)

        binding.editTextDate.apply {
            isFocusable = false
            isClickable = true
            setOnClickListener { showDatePickerDialog() }
        }
    }

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        DatePickerDialog(
            requireContext(),
            R.style.CustomDatePickerDialog,
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