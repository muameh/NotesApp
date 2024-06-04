package com.example.x1_todolist.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.x1_todolist.R
import com.example.x1_todolist.data.entity.Task
import com.example.x1_todolist.databinding.FragmentEditTaskBinding
import com.example.x1_todolist.databinding.FragmentHomeBinding
import com.example.x1_todolist.ui.adapter.TaskAdapter
import com.example.x1_todolist.ui.viewmodel.TaskViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var taskViewModel : TaskViewModel
    private lateinit var taskAdapter : TaskAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempViewModel : TaskViewModel by viewModels()
        taskViewModel = tempViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupHomeRecyclerView()

        binding.fabAdd.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_homeFragment_to_addTaskFragment)
        }
    }

    private fun setupHomeRecyclerView(){
       taskAdapter = TaskAdapter(taskViewModel)
        binding.recyclerView.apply {
            //layoutManager = LinearLayoutManager(requireContext())
            //layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            //layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
            layoutManager = StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
            //layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = taskAdapter
        }

        activity?.let {
            taskViewModel.getAllTasks().observe(viewLifecycleOwner){
                taskAdapter.differ.submitList(it)
                updateUI(it)
            }
        }
    }

    private fun updateUI(tasks:List<Task>){
            if (tasks.isNotEmpty()) {
                binding.noAddedtaskText.visibility = View.GONE
                binding.recyclerView.visibility = View.VISIBLE
            }
            else{
                binding.noAddedtaskText.visibility = View.VISIBLE
                binding.recyclerView.visibility = View.GONE
            }
    }

}