package com.example.deliverykotlinapp.fragments.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.deliverykotlinapp.R
import com.example.deliverykotlinapp.model.Deliver

class ListAdapter : RecyclerView.Adapter<ListAdapter.MyViewHolder>() {

    private var deliverList = emptyList<Deliver>()

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.custom_row, parent, false
            )
        )
    }

    override fun getItemCount(): Int {
        return deliverList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentDelivery = deliverList[position]

        holder.itemView.findViewById<TextView>(R.id.textView2).text = currentDelivery.id.toString()
        holder.itemView.findViewById<TextView>(R.id.txtDeliveredDate).text = currentDelivery.dropOffTime
        holder.itemView.findViewById<TextView>(R.id.lname).text = currentDelivery.recipientName
        holder.itemView.findViewById<TextView>(R.id.fname).text = currentDelivery.dropOffPostcode
        holder.itemView.findViewById<TextView>(R.id.txtShippedWithWhom).text =
            currentDelivery.senderName
        holder.itemView.findViewById<CardView>(R.id.rowLayout).setOnClickListener {
            // warning : Sometime, you need to 'rebuild project' (on the toolbar, click Build > Rebuild Project) to be able to see the 'actionListFragmentToUpdateFragment()' suggestion.
            val action =
                ListFragmentDirections.actionListFragmentToUpdateFragment(currentDelivery) // <- Pass object to Update Fragment
            holder.itemView.findNavController().navigate(action)
        }
    }

    fun setData(deliver: List<Deliver>) {
        this.deliverList = deliver
        notifyDataSetChanged()
    }
}