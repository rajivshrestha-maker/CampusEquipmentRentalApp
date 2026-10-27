package com.example.campusequipmentrentalapp.ui.detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.campusequipmentrentalapp.R
import com.example.campusequipmentrentalapp.data.EquipmentRepository
import com.example.campusequipmentrentalapp.databinding.FragmentEquipmentDetailBinding
import com.example.campusequipmentrentalapp.model.RentalStatus
import com.example.campusequipmentrentalapp.util.NavKeys

class EquipmentDetailFragment : Fragment(R.layout.fragment_equipment_detail) {

    private var _binding: FragmentEquipmentDetailBinding? = null
    private val binding: FragmentEquipmentDetailBinding
        get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentEquipmentDetailBinding.bind(view)

        val equipmentId = arguments?.getInt(NavKeys.EQUIPMENT_ID) ?: -1
        val equipment = EquipmentRepository.findById(equipmentId)

        if (equipment != null) {
            binding.tvDetailIcon.text = equipment.icon
            binding.tvDetailName.text = equipment.name
            binding.tvDetailCategory.text = equipment.category
            binding.tvDetailDescription.text = equipment.description
            binding.tvDetailLocation.text = "위치: ${equipment.location}"
            binding.tvDetailMaxDays.text = "최대 대여 가능 일수: ${equipment.maxRentalDays}일"

            binding.tvDetailStatus.apply {
                text = equipment.status.label
                setBackgroundResource(
                    if (equipment.status == RentalStatus.AVAILABLE) R.drawable.bg_status_available
                    else R.drawable.bg_status_unavailable
                )
            }

            binding.btnRentEquipment.isEnabled = equipment.status == RentalStatus.AVAILABLE
            binding.btnRentEquipment.setOnClickListener {
                val bundle = Bundle().apply {
                    putInt(NavKeys.EQUIPMENT_ID, equipment.id)
                }
                findNavController().navigate(R.id.action_equipmentDetailFragment_to_rentalFragment, bundle)
            }
        }

        binding.btnBackDetail.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
