package com.example.x1_todolist.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.fragment.findNavController
import com.example.x1_todolist.R
import com.example.x1_todolist.databinding.FragmentAddTaskBinding
import com.example.x1_todolist.ui.viewmodel.TaskViewModel
import com.example.x1_todolist.utils.Calculations
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Locale

@AndroidEntryPoint
class AddTaskFragment : Fragment(R.layout.fragment_add_task) {
    private lateinit var binding: FragmentAddTaskBinding
    private lateinit var taskViewModel : TaskViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentAddTaskBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempViewModel : TaskViewModel by viewModels()
        taskViewModel = tempViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSelectDeadline.setOnClickListener {
            showDatePicker()
        }

        binding.btnSelectDeadlineHour.setOnClickListener {
            showTimePicker()
        }


        binding.btnSubmit.setOnClickListener {
            var task_title = binding.tasktitle.text.toString()
            var task_desc = binding.taskdescription.text.toString()
            var creationTime = Calculations.getCurrentDateTime()

            var task_deadline_date = binding.textviewDeadline.text.toString()
            var deadline_date_time = binding.textviewTime.text.toString()

            var taskDeadline = "$task_deadline_date  $deadline_date_time"

            if (!(task_title.isEmpty() || task_desc.isEmpty() || task_deadline_date.isEmpty())){
                taskViewModel.addTask(task_title,task_desc,taskDeadline,creationTime)

                Toast.makeText(context,"Successfully Added!",Toast.LENGTH_SHORT).show()

                findNavController().navigate(R.id.action_addTaskFragment_to_homeFragment)
            } else {
                Toast.makeText(context,"Please fill all the fields",Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun showTimePicker() {
        // Material TimePicker'ı oluşturun
        val timePicker = MaterialTimePicker.Builder()
            .setTimeFormat(TimeFormat.CLOCK_24H) // Saat biçimini ayarlayın (12 veya 24 saat)
            .setTitleText("Saat Seç") // Başlık metnini ayarlayın
            .build()

        // TimePicker'ı gösterin
        timePicker.show(requireActivity().supportFragmentManager, "TimePicker")

        // TimePicker'da pozitif düğmeye tıklandığında işlem yapın
        timePicker.addOnPositiveButtonClickListener {
            // Seçilen saat ve dakikayı alın
            val hour = timePicker.hour
            val minute = timePicker.minute

            // Seçilen saati ve dakikayı biçimlendirin (örneğin, HH:mm)
            val formattedTime = String.format(Locale.getDefault(), "%02d:%02d", hour, minute)

            // Seçilen saati göstermek için bir işlem yapın (örneğin, bir TextView'e yazın)
            binding.textviewTime.setText(formattedTime)
        }
    }

    private fun showDatePicker() {
        val dp = MaterialDatePicker.Builder.datePicker()
            .setTitleText("Tarih seç")
            .build()

        dp.show(requireActivity().supportFragmentManager, "Tarih")
        dp.addOnPositiveButtonClickListener {
            val df = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            val tarih = df.format(it)
            binding.textviewDeadline.setText(tarih)
        }
    }





}