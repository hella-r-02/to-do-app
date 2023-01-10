package com.src.todo.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.src.todo.App
import com.src.todo.R
import com.src.todo.presentation.listOfFolders.viewModel.ListOfFoldersViewModel
import com.src.todo.presentation.listOfFolders.viewModel.ListOfFoldersViewModelFactory
import com.src.todo.presentation.listOfTask.viewModel.ListOfTasksViewModel
import com.src.todo.presentation.listOfTask.viewModel.ListOfTasksViewModelFactory
import com.src.todo.presentation.task.viewModel.TaskViewModel
import com.src.todo.presentation.task.viewModel.TaskViewModelFactory
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController

    @Inject
    lateinit var listOfFoldersViewModelFactory: ListOfFoldersViewModelFactory

    @Inject
    lateinit var listOfTasksViewModelFactory: ListOfTasksViewModelFactory

    @Inject
    lateinit var taskViewModelFactory: TaskViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (applicationContext as App).appComponent.inject(this)
        setContentView(R.layout.activity_main)
        val navHost =
            supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment
        navController = navHost.navController
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    fun getListOfFoldersViewModel(): ListOfFoldersViewModel =
        ViewModelProvider(this, listOfFoldersViewModelFactory)[ListOfFoldersViewModel::class.java]

    fun getListOfTasksViewModel(): ListOfTasksViewModel =
        ViewModelProvider(this, listOfTasksViewModelFactory)[ListOfTasksViewModel::class.java]

    fun getTaskViewModel(): TaskViewModel =
        ViewModelProvider(this, taskViewModelFactory)[TaskViewModel::class.java]
}