package com.example.x1_todolist.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.x1_todolist.R
import com.example.x1_todolist.data.entity.Task
import com.example.x1_todolist.databinding.FragmentEditTaskBinding
import com.example.x1_todolist.ui.viewmodel.TaskViewModel
import com.example.x1_todolist.utils.Calculations
import com.example.x1_todolist.utils.PairConverter
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Locale

@AndroidEntryPoint
class EditTaskFragment : Fragment(R.layout.fragment_edit_task) {
    private lateinit var binding: FragmentEditTaskBinding
    private lateinit var taskViewModel : TaskViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentEditTaskBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempViewModel : TaskViewModel by viewModels()
        taskViewModel = tempViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bundle : EditTaskFragmentArgs by navArgs()
        val editableTask = bundle.currentTask

        val timeRemaining = Calculations.calculateRemainingTime(editableTask.task_deadline)

        var _taskDeadline = editableTask.task_deadline.split("  ")
        var taskDeadline_date = _taskDeadline[0]
        var taskDeadline_time = _taskDeadline[1]

        binding.editTasktitle.setText(editableTask.task_title)
        binding.editTaskdescription.setText(editableTask.task_description)

        binding.editTextviewDeadline.setText(taskDeadline_date)
        binding.textviewTimet.setText(taskDeadline_time)

        binding.tvCreationTime.setText("Created: ${editableTask.task_creation_time}")
        binding.textViewTargetTime.setText("Targeted : ${editableTask.task_deadline}")
        binding.tvSinceCreation.setText("Remaining: $timeRemaining")

        binding.editBtnSelectDeadline.setOnClickListener {
            showDatePicker()
        }

        binding.btnDeadlineHour.setOnClickListener {
            showTimePicker()
        }

        binding.editBtnSubmit.setOnClickListener {
            val taskTitle = binding.editTasktitle.text.toString().trim()
            val taskDesc = binding.editTaskdescription.text.toString().trim()

            val taskDeadline_date = binding.editTextviewDeadline.text.toString().trim()
            val taskDeadLine_time = binding.textviewTimet.text.toString().trim()

            val _taskCreationTime = Calculations.getCurrentDateTime()

            var taskDeadline = "$taskDeadline_date  $taskDeadLine_time"


            if (!(taskTitle.isEmpty()
                        || taskDesc.isEmpty()
                        || taskDeadline.isEmpty()
                        || taskDeadline_date.isEmpty()
                        || taskDeadLine_time.isEmpty() )){

                val taskCreationTime = PairConverter.fromPair(_taskCreationTime)

                val task = Task(editableTask.task_id,taskTitle,taskDesc,taskDeadline,taskCreationTime)

                taskViewModel.updateTask(task)

                findNavController().navigate(R.id.action_editTaskFragment_to_homeFragment)

                Toast.makeText(context,"Successfully updated!",Toast.LENGTH_SHORT).show()

                view.findNavController().popBackStack(R.id.homeFragment,false)
            }
            else{
                Toast.makeText(context,"Please fill all the fields",Toast.LENGTH_SHORT).show()
            }
        }

    }
    private fun showTimePicker() {
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
            binding.textviewTimet.setText(formattedTime)
        }
    }

    private fun showDatePicker() {
        val dp = MaterialDatePicker.Builder.datePicker()
            .setTitleText("Pick a Date")
            .build()

        dp.show(requireActivity().supportFragmentManager, "Date")
        dp.addOnPositiveButtonClickListener {
            val df = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            val date = df.format(it)
            binding.editTextviewDeadline.setText(date)
        }
    }



}