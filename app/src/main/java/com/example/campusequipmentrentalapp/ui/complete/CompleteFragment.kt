package com.example.campusequipmentrentalapp.ui.complete

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.campusequipmentrentalapp.R
import com.example.campusequipmentrentalapp.data.EquipmentRepository
import com.example.campusequipmentrentalapp.databinding.FragmentCompleteBinding

class CompleteFragment : Fragment(R.layout.fragment_complete) {

    private var _binding: FragmentCompleteBinding? = null
    private val binding: FragmentCompleteBinding
        get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentCompleteBinding.bind(view)

        val equipmentId = arguments?.getInt("equimentId") ?: -1
        val applicantName = arguments?.getString("applicantName") ?: "미입력"
        val studentId = arguments?.getString("studentId") ?: "미입력"
        val purpose = arguments?.getString("purpose") ?: "미입력"
        val rentalDays = arguments?.getInt("rentalDays") ?: 1

        val equipment = EquipmentRepository.findById(equipmentId)

        binding.tvCompleteEquipment.text = "장비명: ${equipment?.name ?: "알 수 없음"}"
        binding.tvCompleteApplicant.text = "신청자: $applicantName"
        binding.tvCompleteStudentId.text = "학번: $studentId"
        binding.tvCompletePeriod.text = "대여 기간: ${rentalDays}일"
        binding.tvCompletePurpose.text = "대여 목적: $purpose"

        binding.btnGoList.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_equipmentListFragment) // This might not be right if we are already in the stack
            // Better to pop to list or home.
            findNavController().popBackStack(R.id.equipmentListFragment, false)
        }

        binding.btnGoHome.setOnClickListener {
            findNavController().popBackStack(R.id.homeFragment, false)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
