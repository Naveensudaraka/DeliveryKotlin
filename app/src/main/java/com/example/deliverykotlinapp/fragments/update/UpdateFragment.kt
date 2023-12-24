package com.example.deliverykotlinapp.fragments.update

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.deliverykotlinapp.HomeActivity
import com.example.deliverykotlinapp.R
import com.example.deliverykotlinapp.databinding.FragmentUpdateBinding
import com.example.deliverykotlinapp.model.Deliver
import com.example.deliverykotlinapp.viewModel.DeliverViewModel

class UpdateFragment : Fragment() {
    private lateinit var editText1: EditText
    private lateinit var editText2: EditText
    private lateinit var textViewSum: TextView

    private var _binding: FragmentUpdateBinding? = null
    private val binding get() = _binding!!

    private val args by navArgs<UpdateFragmentArgs>()

    private lateinit var deliverViewModel: DeliverViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? = FragmentUpdateBinding.inflate(inflater, container, false)
        .runCatching {
            // Inflate the layout for this fragment
            _binding = this
            root
        }.getOrNull()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /* val view = inflater.inflate(R.layout.fragment_update, container, false)
        editText1 = view.findViewById(R.id.updatepackageWeight)
        editText2 = view.findViewById(R.id.updatequantity)
        textViewSum = view.findViewById(R.id.txtTotal)

        editText1.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                updateSum()
            }

            override fun afterTextChanged(s: Editable?) {}
        })


        editText2.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {println("Amo");}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                updateSum()

            }

        })*/

        deliverViewModel = ViewModelProvider(this)[DeliverViewModel::class.java]

        binding.updatesenderName.setText(args.currentDeliver.senderName)
        binding.updatesenderContactNumber.setText(args.currentDeliver.senderContactNumber.toString())
        binding.updatepickUpPostcode.setText(args.currentDeliver.pickUpPostcode)
        binding.updatepickUpDate.setText(args.currentDeliver.pickUpDate)
        binding.updatepickUpTime.setText(args.currentDeliver.pickUpTime)

        binding.updaterecipientName.setText(args.currentDeliver.recipientName)
        binding.updaterecipientContactNumber.setText(args.currentDeliver.recipientContactNumber.toString())
        binding.updatedropOffPostcode.setText(args.currentDeliver.dropOffPostcode)
        binding.updatedropOffTime.setText(args.currentDeliver.dropOffTime)

        binding.updatepackageSize.setText(args.currentDeliver.packageSize)
        binding.updatepackageWeight.setText(args.currentDeliver.packageWeight.toString())
        binding.updatequantity.setText(args.currentDeliver.quantity.toString())

        // Use kotlin extensions
        // Before changed texts
        //binding.updatepackageWeight.doBeforeTextChanged { text, start, count, after ->  }
        // On text changes
        //binding.updatepackageWeight.doOnTextChanged { text, start, before, count ->  }
        // After changed texts
        //binding.updatepackageWeight.doAfterTextChanged {  }

        binding.updatepackageWeight.doOnTextChanged { _, _, _, _ ->
            updateSum()
        }

        binding.updatequantity.doOnTextChanged { _, _, _, _ ->
            updateSum()
        }

        binding.btnUpdate.setOnClickListener {
            updateItem()
        }

        /**
         * Calculation formula for item price and quantity (Simple)
         * item_price * item_quantity (10 * 5) = 50
         * ex :- grand_total * quantity
         *
         * Add price ranges for each weight of packages. (Advanced)
         * ex :- 1(g/kg) = Rs.10, 2(g/kg) = Rs.20, 3(g/kg) = Rs.30 ...
         *
         * Calc goes like... If the package is under 1(g/kg) (Rs.10)
         * Round the package price with double eg; Rs.10 = 10.toDouble()
         * ex :- item_price + package_price * item_quantity
         *       100.00 (Double) + 10.00 (Double) * 2 (Integer) = Rs.220
         *
         */


        binding.btnBack.setOnClickListener {
            activity?.let {
                val intent = Intent(it, HomeActivity::class.java)
                it.startActivity(intent)
            }
        }
        // Add menu
        setHasOptionsMenu(true)
    }

    private fun updateSum() {
        val value1 = binding.updatepackageWeight.text.toString().toDoubleOrNull() ?: 0.0
        val value2 = binding.updatequantity.text.toString().toDoubleOrNull() ?: 0.0
        val sum = value1 + value2
        binding.txtTotal.text = sum.toString()
    }

    private fun updateItem() {
        val senderName = binding.updatesenderName.text.toString()
        val senderContactNumber =
            Integer.parseInt(binding.updatesenderContactNumber.text.toString())
        val pickUpPostcode = binding.updatepickUpPostcode.text.toString()
        val pickUpDate = binding.updatepickUpDate.text.toString()
        val pickUpTime = binding.updatepickUpTime.text.toString()

        val recipientName = binding.updaterecipientName.text.toString()
        val recipientContactNumber =
            Integer.parseInt(binding.updaterecipientContactNumber.text.toString())
        val dropOffPostcode = binding.updatedropOffPostcode.text.toString()
        val dropOffTime = binding.updatedropOffTime.text.toString()

        val packageSize = binding.updatepackageSize.text.toString()
        val packageWeight = Integer.parseInt(binding.updatepackageWeight.text.toString())
        val quantity = Integer.parseInt(binding.updatequantity.text.toString())

        if (inputCheck(
                senderName,
                binding.updatesenderContactNumber.text,
                pickUpPostcode,
                pickUpDate,
                pickUpTime,
                recipientName,
                binding.updaterecipientContactNumber.text,
                dropOffPostcode,
                dropOffTime,
                packageSize,
                binding.updatepackageWeight.text,
                binding.updatequantity.text
            )
        ) {
            // Create Delivery Object
            val updateDeliver = Deliver(
                args.currentDeliver.id, senderName,
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

            // Update Current Delivery
            deliverViewModel.updateDeliver(updateDeliver)
            Toast.makeText(requireContext(), "Updated Successfully !", Toast.LENGTH_SHORT).show()

            // Navigate back to List Fragment
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        } else {
            Toast.makeText(requireContext(), "Please fill all fields !", Toast.LENGTH_SHORT).show()
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
        quantity: Editable,
    ): Boolean {
        return !(TextUtils.isEmpty(senderName)
                && senderContactNumber.isEmpty()
                && TextUtils.isEmpty(pickUpPostcode)
                && TextUtils.isEmpty(pickUpDate)
                && TextUtils.isEmpty(pickUpTime)

                && TextUtils.isEmpty(recipientName)
                && recipientContactNumber.isEmpty()
                && TextUtils.isEmpty(dropOffPostcode)
                && TextUtils.isEmpty(dropOffTime)

                && TextUtils.isEmpty(packageSize)
                && packageWeight.isEmpty()
                && quantity.isEmpty())
    }

    // Inflate the layout to our menu
    @Deprecated("Deprecated in Java")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu, menu)
    }

    // Handle clicks on menu items
    @Deprecated("Deprecated in Java")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_delete) {
            deleteDeliver()
        }
        return super.onOptionsItemSelected(item)
    }

    // Implement logic to delete a Delivery
    private fun deleteDeliver() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") { _, _ ->     // Make a "Yes" option and set action if the Delivery selects "Yes"
            deliverViewModel.deleteDeliver(args.currentDeliver)    // Execute : delete Delivery
            Toast.makeText(                                // Notification if a Delivery is deleted successfully
                requireContext(),
                "Successfully removed ${args.currentDeliver.senderName}",
                Toast.LENGTH_SHORT
            )
                .show()
            findNavController().navigate(R.id.action_updateFragment_to_listFragment) // Navigate to List Fragment after deleting a Delivery
        }
        builder.setNegativeButton("No") { _, _ -> }    // Make a "No" option and set action if the Delivery selects "No"
        builder.setTitle("Delete ${args.currentDeliver.senderName} ?")  // Set the title of the prompt with a sentence saying the first name of the Delivery inside the app (using template string)
        builder.setMessage("Are you sure to remove ${args.currentDeliver.senderName} ?")  // Set the message of the prompt with a sentence saying the first name of the Delivery inside the app (using template string)
        builder.create()
            .show()  // Create a prompt with the configuration above to ask the Delivery (the real app Delivery which is human)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        this.getArguments()?.clear();
        _binding =
            null // <- whenever we destroy our fragment, _binding is set to null. Hence it will avoid memory leaks.
    }

}