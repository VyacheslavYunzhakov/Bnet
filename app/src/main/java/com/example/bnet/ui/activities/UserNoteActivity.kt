package com.example.bnet.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.ViewCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bnet.R
import com.example.bnet.app.App
import com.example.bnet.app.AppDatabase
import com.example.bnet.data.UserNote
import com.example.bnet.data.UserNoteData
import com.example.bnet.ui.adapters.UserNoteListAdapter
import com.example.bnet.utils.InjectorUtils
import com.example.bnet.viewmodel.UserNotesViewModel
import kotlinx.android.synthetic.main.activity_user_note_list.*

class UserNoteActivity : AppCompatActivity() {

    private val factory = InjectorUtils.provideUserNoteViewModelFactory()
    //private lateinit var adapter: UserNoteListAdapter
    var database: AppDatabase = App.database

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_note_list)

        //database.userNoteDao().clearTable()
        val userNotesViewModel = ViewModelProvider(this, factory).get(UserNotesViewModel::class.java)
        userNoteListView.layoutManager = LinearLayoutManager(this)


        var userNoteList= UserNoteData(database.userNoteDao().getUserNotesSimple())
        userNoteListView.adapter = UserNoteListAdapter(object : UserNoteListAdapter.OnItemClickListener{
            override fun onItemClick(view: View, userNote: UserNote) {
                editUserNote(view, userNote)
            }
        }, userNoteList)

        //adapter.updateUserNotes(database.userNoteDao().getUserNotesSimple())
        //Log.d("myLogs", "MainActivity: " + (database.userNoteDao().getUserNotesSimple()))
        userNotesViewModel.getUserNotes().observe(this, Observer {
            it?.let{
                userNoteList= UserNoteData(it.toMutableList())
                (userNoteListView.adapter as UserNoteListAdapter).updateUserNotes(userNoteList)
            }
        })
        addUserNoteButton.setOnClickListener{addUserNote()}
    }
    private fun editUserNote(view: View, userNote: UserNote) {
        val intent = UserNoteDetailActivity.newIntent(this, userNote)
        val options = ViewCompat.getTransitionName(view)?.let {
            ActivityOptionsCompat.makeSceneTransitionAnimation(this,
                view,
                it
            )
        }
        Log.d("myLogs", "Im giving userNote" + userNote.text)
        ActivityCompat.startActivity(this, intent, options?.toBundle())
    }
    private fun addUserNote() {
        val intent = UserNoteDetailActivity.newIntent(this)
        startActivity(intent)
    }

}