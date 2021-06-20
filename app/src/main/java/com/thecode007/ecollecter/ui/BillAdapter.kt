package com.thecode007.ecollecter.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.thecode007.ecollecter.R
import com.thecode007.ecollecter.network.model.Bill
import kotlinx.android.synthetic.main.row_bill.view.*

class BillAdapter(val bills:ArrayList<Bill>) : RecyclerView.Adapter<BillAdapter.BillVH>() {


    inner class BillVH(itemView: View) : RecyclerView.ViewHolder(itemView) {

        @SuppressLint("SetTextI18n")
        fun setData(bill: Bill) {
            itemView.tv_bill_cost.text = "Bill Cost " + bill.billBalance + " L.L"
            itemView.tv_meter_balance.text = "Meter Consumption " + bill.meterBalance + " KW"
            itemView.tv_date.text = "Create At " + bill.createdAt!!.split("T")[0]

            if (bill.status == "0") {
                itemView.iv_status.setImageResource(R.drawable.un_paid)
            }
            else {
                itemView.iv_status.setImageResource(R.drawable.paid)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BillVH {
        return BillVH(LayoutInflater.from(parent.context).inflate(R.layout.row_bill, parent, false))
    }

    override fun onBindViewHolder(holder: BillVH, position: Int) {
        holder.setData(bills[position])
    }

    override fun getItemCount(): Int {
        return bills.size
    }
}