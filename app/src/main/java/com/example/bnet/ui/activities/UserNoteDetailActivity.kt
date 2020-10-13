package com.example.bnet.ui.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.bnet.R
import com.example.bnet.app.App
import com.example.bnet.app.AppDatabase
import com.example.bnet.data.UserNote
import kotlinx.android.synthetic.main.activity_user_note_detail.*
import java.util.*



class UserNoteDetailActivity: AppCompatActivity() {

    var database: AppDatabase = App.database
    companion object {
        const val EXTRA_NOTE = "EXTRA_NOTE"
        fun newIntent(context: Context): Intent  = Intent(
            context,
            UserNoteDetailActivity::class.java
        )


        fun newIntent(context: Context, note: UserNote): Intent {
            val intent = newIntent(context)
            intent.putExtra(EXTRA_NOTE, note)
            return intent
        }
    }

    private lateinit var userNote: UserNote

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_note_detail)

        userNote = if (intent.getSerializableExtra(EXTRA_NOTE)!=null) {
            intent.getSerializableExtra(EXTRA_NOTE) as UserNote
        } else{
            UserNote(Math.random().toLong(),"", null, null)
        }
        cancelButton.setOnClickListener{finish()}
        Log.d("myLogs", "" + userNote.created)
        if (userNote.text =="") {
            saveUserNoteButton.setOnClickListener { saveUserNote() }
        }
        else{
            editUserNoteView.setText(userNote.text)
            saveUserNoteButton.setOnClickListener { editUserNote() }
        }

    }

    private fun editUserNote() {
        userNote.text = editUserNoteView.text.toString()
        userNote.modified =Date().time
        if (userNote.created == null) {
            userNote.created = Date().time
            userNote.modified =userNote.created
        }

        database.userNoteDao().updateUserNote(userNote)
        finish()
    }


    private fun saveUserNote(){
        userNote.text = editUserNoteView.text.toString()
        userNote.modified =Date().time
        if (userNote.created == null) {
            userNote.created = Date().time
            userNote.modified =userNote.created
        }

        database.userNoteDao().addUserNote(userNote)
        finish()
    }

}