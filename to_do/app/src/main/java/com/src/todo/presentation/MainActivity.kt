package com.src.todo.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.src.todo.App
import com.src.todo.R
import com.src.todo.presentation.addTask.viewModel.AddTaskViewModel
import com.src.todo.presentation.addTask.viewModel.AddTaskViewModelFactory
import com.src.todo.presentation.createFolder.viewModel.CreateFolderViewModel
import com.src.todo.presentation.createFolder.viewModel.CreateFolderViewModelFactory
import com.src.todo.presentation.listOfFolders.viewModel.ListOfFoldersViewModel
import com.src.todo.presentation.listOfFolders.viewModel.ListOfFoldersViewModelFactory
import com.src.todo.presentation.listOfTask.viewModel.ListOfTasksViewModel
import com.src.todo.presentation.listOfTask.viewModel.ListOfTasksViewModelFactory
import com.src.todo.presentation.listOfTasksWithoutFolder.viewModel.ListOfTasksWithoutFolderViewModel
import com.src.todo.presentation.listOfTasksWithoutFolder.viewModel.ListOfTasksWithoutFolderViewModelFactory
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

    @Inject
    lateinit var createFolderViewModelFactory: CreateFolderViewModelFactory

    @Inject
    lateinit var addTaskViewModelFactory: AddTaskViewModelFactory

    @Inject
    lateinit var listOfTasksWithoutFolderViewModelFactory: ListOfTasksWithoutFolderViewModelFactory

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

    fun getCreateFolderViewModel(): CreateFolderViewModel =
        ViewModelProvider(this, createFolderViewModelFactory)[CreateFolderViewModel::class.java]

    fun getAddTaskViewModel(): AddTaskViewModel =
        ViewModelProvider(this, addTaskViewModelFactory)[AddTaskViewModel::class.java]

    fun getListOfTasksWithoutFolderViewModel(): ListOfTasksWithoutFolderViewModel =
        ViewModelProvider(
            this,
            listOfTasksWithoutFolderViewModelFactory
        )[ListOfTasksWithoutFolderViewModel::class.java]
}