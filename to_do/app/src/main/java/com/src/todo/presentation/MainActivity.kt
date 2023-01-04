package com.src.todo.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.src.todo.App
import com.src.todo.R
import com.src.todo.presentation.listOfFolders.ListOfFoldersFragment
import com.src.todo.presentation.listOfFolders.viewModel.ListOfFoldersViewModel
import com.src.todo.presentation.listOfFolders.viewModel.ListOfFoldersViewModelFactory
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var listOfFoldersViewModelFactory: ListOfFoldersViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (applicationContext as App).appComponent.inject(this)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, ListOfFoldersFragment())
            .addToBackStack(null)
            .commit()
    }

    fun getListOfFoldersViewModel(): ListOfFoldersViewModel =
        ViewModelProvider(this, listOfFoldersViewModelFactory)[ListOfFoldersViewModel::class.java]
}