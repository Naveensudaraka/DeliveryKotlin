package com.example.deliverykotlinapp.fragments.add

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.deliverykotlinapp.HomeActivity
import com.example.deliverykotlinapp.MainActivity
import com.example.deliverykotlinapp.R
import com.example.deliverykotlinapp.databinding.FragmentAddBinding
import com.example.deliverykotlinapp.model.Deliver
import com.example.deliverykotlinapp.viewModel.DeliverViewModel


class AddFragment : Fragment() {


    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding!!

    private lateinit var deliverViewModel: DeliverViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? = FragmentAddBinding.inflate(inflater, container, false)
    .runCatching {
        // Inflate the layout for this fragment
        _binding = this
        root
    }.getOrNull()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        deliverViewModel = ViewModelProvider(this)[DeliverViewModel::class.java]

        binding.btnAdd.setOnClickListener {
            insertDataToDatabase()
        }

        binding.btnBack.setOnClickListener {
            activity?.let {
                val intent = Intent(it, HomeActivity::class.java)
                it.startActivity(intent)
            }
        }
    }

    private fun insertDataToDatabase() {
        val senderName = binding.edSenderName.text.toString()
        val senderContactNumber = binding.edContactNumber.text
        val pickUpPostcode = binding.edPickUpPostcode.text.toString()
        val pickUpDate = binding.edPickUpDate.text.toString()
        val pickUpTime = binding.edPickUpTime.text.toString()

        val recipientName = binding.edRecipientName.text.toString()
        val recipientContactNumber = binding.edRecipientContactNumber.text
        val dropOffPostcode = binding.edDropOffPostcode.text.toString()
        val dropOffTime = binding.edDropOffTime.text.toString()

        val packageSize = binding.edPackageSize.text.toString()
        val packageWeight = binding.edPackageWeight.text
        val quantity = binding.edQuantity.text

        if (inputCheck(
                senderName,
                senderContactNumber,
                pickUpPostcode,
                pickUpDate,
                pickUpTime,
                recipientName,
                recipientContactNumber,
                dropOffPostcode,
                dropOffTime,
                packageSize,
                packageWeight,
                quantity
            )
        ) {
            val deliver = Deliver(
                0,
                senderName,
                Integer.parseInt(senderContactNumber.toString()),
                pickUpPostcode,
                pickUpDate,
                pickUpTime,
                recipientName,
                Integer.parseInt(recipientContactNumber.toString()),
                dropOffPostcode,
                dropOffTime,
                packageSize,
                Integer.parseInt(packageWeight.toString()),
                Integer.parseInt(quantity.toString()),
            )
            // Add Data to database
            deliverViewModel.addDeliver(deliver)
            Toast.makeText(requireContext(), "Successfully added!", Toast.LENGTH_LONG).show()
            // Navigate back
            findNavController().navigate(R.id.action_addFragment_to_listFragment)
        } else {
            Toast.makeText(requireContext(), "Please fill out all fields!", Toast.LENGTH_LONG)
                .show()
        }
    }

    private fun inputCheck(
        senderName: String,
        senderContactNumber: Editable,
        pickUpPostcode: String,
        pickUpDate: String,
        pickUpTime: String,
        recipientName: String,
        recipientContactNumber: Editable,
        dropOffPostcode: String,
        dropOffTime: String,
        packageSize: String,
        packageWeight: Editable,
        quantity: Editable
    ): Boolean {
        return !(TextUtils.isEmpty(senderName) && senderContactNumber.isEmpty() && TextUtils.isEmpty(
            pickUpPostcode
        ) && TextUtils.isEmpty(pickUpDate) && TextUtils.isEmpty(pickUpTime)

                && TextUtils.isEmpty(recipientName) && recipientContactNumber.isEmpty() && TextUtils.isEmpty(
            dropOffPostcode
        ) && TextUtils.isEmpty(dropOffTime)

                && TextUtils.isEmpty(packageSize) && packageWeight.isEmpty() && TextUtils.isEmpty(
            quantity
        ))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding =
            null // <- whenever we destroy our fragment, _binding is set to null. Hence it will avoid memory leaks.
    }

}