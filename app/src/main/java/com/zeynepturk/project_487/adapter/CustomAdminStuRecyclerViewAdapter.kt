package com.zeynepturk.project_487.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.zeynepturk.project_487.R
import com.zeynepturk.project_487.model.Student
import org.w3c.dom.Text

class CustomAdminStuRecyclerViewAdapter(
    private val context: Context,
    //private val images: List<Int>,
    private val onItemDelete: (Student) -> Unit,
    private val onEditClicked: (Student) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var recyclerViewItem = emptyList<Student>()
    fun setData(items: MutableList<Student>) {
        recyclerViewItem = items
        notifyDataSetChanged()
    }

    fun removeItem(position: Int) {
        val item = recyclerViewItem[position]
        onItemDelete(item)
        recyclerViewItem = recyclerViewItem.toMutableList().apply { removeAt(position) }
        notifyItemRemoved(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(context)
        return if (viewType == Student.TYPE_SUCCESSFUL) {
            val itemView = inflater.inflate(R.layout.item_stu_success, parent, false)
            SuccessViewHolder(itemView)
        } else {
            val itemView = inflater.inflate(R.layout.item_stu_unsuccess, parent, false)
            UnsuccessViewHolder(itemView)
        }
    }

    override fun getItemCount(): Int {
        return recyclerViewItem.size
    }

    override fun getItemViewType(position: Int): Int {
        val currentItem = recyclerViewItem[position]
        return currentItem.calculateType()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentItem = recyclerViewItem[position]
        if(getItemViewType(position) == Student.TYPE_SUCCESSFUL) {
            val itemHolder = holder as SuccessViewHolder
            itemHolder.stuId2.text = currentItem.id.toString()
            itemHolder.stuName2.text = currentItem.name
            itemHolder.stuMail2.text = currentItem.mail
            itemHolder.stuCgpa2.text = currentItem.cgpa.toString()
            //itemHolder.stuImg2.setImageResource(images[position])
            itemHolder.fabUpdate2.setOnClickListener {
                onEditClicked(currentItem)
            }
        } else {
            val itemHolder = holder as UnsuccessViewHolder
            itemHolder.stuId.text = currentItem.id.toString()
            itemHolder.stuName.text = currentItem.name
            itemHolder.stuMail.text = currentItem.mail
            itemHolder.stuCgpa.text = currentItem.cgpa.toString()
            //itemHolder.stuImg.setImageResource(images[position])
            itemHolder.fabUpdate.setOnClickListener {
                onEditClicked(currentItem)
            }
        }
    }

    inner class SuccessViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val stuId2: TextView = itemView.findViewById(R.id.idTxt2)
        val stuName2: TextView = itemView.findViewById(R.id.nameTxt2)
        val stuMail2: TextView = itemView.findViewById(R.id.mailTxt2)
        val stuCgpa2: TextView = itemView.findViewById(R.id.cgpaTxt2)
        val layout2: LinearLayout = itemView.findViewById(R.id.stuSuccessLayout)
        //val stuImg2: ImageView =itemView.findViewById(R.id.imageView2)
        val fabUpdate2: FloatingActionButton = itemView.findViewById(R.id.fabUpdate)
    }
    inner class UnsuccessViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val stuId: TextView = itemView.findViewById(R.id.idTxt)
        val stuName: TextView = itemView.findViewById(R.id.nameTxt)
        val stuMail: TextView = itemView.findViewById(R.id.mailTxt)
        val stuCgpa: TextView = itemView.findViewById(R.id.cgpaTxt)
        val layout: LinearLayout = itemView.findViewById(R.id.stuUnsuccessLayout)
        //val stuImg: ImageView =itemView.findViewById(R.id.imageView)
        val fabUpdate: FloatingActionButton = itemView.findViewById(R.id.fabUpdate)
    }

}