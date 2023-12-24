package com.example.deliverykotlinapp.fragments.list

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.deliverykotlinapp.HomeActivity
import com.example.deliverykotlinapp.R
import com.example.deliverykotlinapp.databinding.FragmentListBinding
import com.example.deliverykotlinapp.viewModel.DeliverViewModel

class ListFragment : Fragment() {

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    private lateinit var deliverViewModel: DeliverViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentListBinding.inflate(inflater, container, false)
        // val view = inflater.inflate(R.layout.fragment_list, container, false) // <- This is not required.

        // RecyclerView
        val adapter = ListAdapter()
        // val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerview) // <- This is replaced.
        val recyclerView = binding.recyclerview
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // DeliverViewModel
        deliverViewModel = ViewModelProvider(this).get(DeliverViewModel::class.java)
        deliverViewModel.readAllData.observe(viewLifecycleOwner, Observer { deliver ->
            adapter.setData(deliver)
        })

        binding.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_addFragment)
        }

        binding.btnBack.setOnClickListener {
            activity?.let {
                val intent = Intent(it, HomeActivity::class.java)
                it.startActivity(intent)
            }
        }

        // Add menu
        setHasOptionsMenu(true)

        return binding.root
    }


    @Deprecated("Deprecated in Java")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu, menu)
    }

    @Deprecated("Deprecated in Java")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_delete) {
            deleteAllDelivers()
        }
        return super.onOptionsItemSelected(item)
    }

    // Implement logic to delete all delivers
    private fun deleteAllDelivers() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") { _, _ ->     // Make a "Yes" option and set action if the deliver selects "Yes"
            deliverViewModel.deleteAllDelivers()    // Execute : delete all delivers
            Toast.makeText(                                // Notification if a deliver is deleted successfully
                requireContext(),
                "Successfully removed everything",
                Toast.LENGTH_SHORT)
                .show()
            // Note: No need to navigate app deliver to List Fragment since deleting all delivers takes place at List Fragment.
        }
        builder.setNegativeButton("No") { _, _ -> }    // Make a "No" option and set action if the deliver selects "No"
        builder.setTitle("Delete everything ?")  // Set the title of the prompt with a sentence saying the first name of the deliver inside the app (using template string)
        builder.setMessage("Are you sure to remove everything ?")  // Set the message of the prompt with a sentence saying the first name of the deliver inside the app (using template string)
        builder.create().show()  // Create a prompt with the configuration above to ask the deliver (the real app deliver which is human)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // <- whenever we destroy our fragment, _binding is set to null. Hence it will avoid memory leaks.
    }
}