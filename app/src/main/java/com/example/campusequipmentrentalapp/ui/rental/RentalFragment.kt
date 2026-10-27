package com.example.campusequipmentrentalapp.ui.rental

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.campusequipmentrentalapp.R
import com.example.campusequipmentrentalapp.data.EquipmentRepository
import com.example.campusequipmentrentalapp.data.UserRepository
import com.example.campusequipmentrentalapp.databinding.FragmentRentalBinding
import com.example.campusequipmentrentalapp.util.NavKeys

class RentalFragment : Fragment(R.layout.fragment_rental) {

    private var _binding: FragmentRentalBinding? = null
    private val binding: FragmentRentalBinding
        get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentRentalBinding.bind(view)

        val equipmentId = arguments?.getInt(NavKeys.EQUIPMENT_ID) ?: -1
        val equipment = EquipmentRepository.findById(equipmentId)

        if (equipment != null) {
            binding.tvSelectedEquipment.text = "선택한 장비: ${equipment.name}"
            binding.tvRentalRule.text = "최대 대여 가능 일수: ${equipment.maxRentalDays}일"

            // Pre-fill user info from UserRepository (simulating login)
            val user = UserRepository.users.firstOrNull()
            if (user != null) {
                binding.etApplicantName.setText(user.name)
                binding.etStudentId.setText(user.studentId)
            }

            // Setup Spinner for rental days
            val periods = (1..equipment.maxRentalDays).map { "${it}일" }
            val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, periods)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spinnerPeriod.adapter = adapter

            binding.btnSubmitRental.setOnClickListener {
                val applicantName = binding.etApplicantName.text.toString()
                val studentId = binding.etStudentId.text.toString()
                val purpose = binding.etPurpose.text.toString()
                val rentalDays = binding.spinnerPeriod.selectedItemPosition + 1

                if (applicantName.isBlank() || studentId.isBlank() || purpose.isBlank()) {
                    // Show error or toast (omitted for brevity, but could add if needed)
                    return@setOnClickListener
                }

                val bundle = Bundle().apply {
                    putInt("equimentId", equipmentId) // Note: matches nav_graph.xml arg name
                    putString("applicantName", applicantName)
                    putString("studentId", studentId)
                    putString("purpose", purpose)
                    putInt("rentalDays", rentalDays)
                }

                findNavController().navigate(R.id.action_rentalFragment_to_completeFragment, bundle)
            }
        }

        binding.btnBackRental.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
