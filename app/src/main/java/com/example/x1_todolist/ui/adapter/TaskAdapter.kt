package com.example.x1_todolist.ui.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.x1_todolist.data.entity.Task
import com.example.x1_todolist.databinding.CardTaskBinding
import com.example.x1_todolist.ui.fragments.HomeFragmentDirections
import com.example.x1_todolist.ui.viewmodel.TaskViewModel
import com.example.x1_todolist.utils.Calculations
import com.google.android.material.snackbar.Snackbar

class TaskAdapter(var viewModel: TaskViewModel) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    inner class TaskViewHolder(val itemBinding: CardTaskBinding)
        : RecyclerView.ViewHolder(itemBinding.root)


    private val differCallback = object : DiffUtil.ItemCallback<Task>() {
        override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem.task_id == newItem.task_id &&
                    oldItem.task_title == newItem.task_title &&
                    oldItem.task_description == newItem.task_description &&
                    oldItem.task_deadline == newItem.task_deadline
        }

        override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem == newItem
        }
    }
    val differ = AsyncListDiffer(this,differCallback)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val binding = CardTaskBinding.inflate(LayoutInflater.from(parent.context), parent,false)
        return TaskViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val currentTask = differ.currentList[position]

        val timeRemaining = Calculations.calculateRemainingTime(currentTask.task_deadline)

        holder.itemBinding.noteTitle.text = currentTask.task_title
        holder.itemBinding.noteDesc.text = currentTask.task_description
        holder.itemBinding.deadline.text = currentTask.task_deadline
        holder.itemBinding.creationTime.text = "($timeRemaining)"

        holder.itemBinding.ivDELETE.setOnClickListener {
            Snackbar.make(it,"DELETE ${currentTask.task_title}?", Snackbar.LENGTH_SHORT)
                .setAction("YES") {
                    viewModel.deleteTask(currentTask.task_id)
                }
                .show()
        }

        holder.itemBinding.cardView.setOnClickListener {
            val direction = HomeFragmentDirections.actionHomeFragmentToEditTaskFragment(currentTask)
            Navigation.findNavController(it).navigate(direction)
            //it.findNavController().navigate(direction)
        }
    }
}