package com.example.bnet.ui.activities

import android.app.Dialog
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
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
import com.example.bnet.ui.adapters.UserNoteListAdapter
import com.example.bnet.utils.InjectorUtils
import com.example.bnet.viewmodel.UserNotesViewModel
import kotlinx.android.synthetic.main.activity_user_note_list.*
import kotlinx.coroutines.*


class UserNoteActivity : AppCompatActivity() {

    private val factory = InjectorUtils.provideUserNoteViewModelFactory()
    var database: AppDatabase = App.database
    var sessionId:String? = null
    private val progressBar: ProgressBar by lazy {progress_bar_loading}


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_note_list)

        val userNotesViewModel = ViewModelProvider(this, factory).get(UserNotesViewModel::class.java)
        userNoteListView.layoutManager = LinearLayoutManager(this)


        if(!isNetworkConnected(this)){
            showAlertDialogFirstLaunch(userNotesViewModel)
        }else{
            retrieveSessionId(userNotesViewModel)
        }

            userNotesViewModel.getUserNotesFromDatabase().observe(getOwner(), Observer {
                it?.let {
                    if (it.isNotEmpty()) {
                        showLoading()
                        if (isNetworkConnected(this)) {
                        addUserNoteToServer(userNotesViewModel, it[0])
                        } else {
                            showAlertDialog(userNotesViewModel)
                        }
                    }
                }
            })

        addUserNoteButton.setOnClickListener{addUserNote()}
    }

    private fun showAlertDialogFirstLaunch(userNotesViewModel: UserNotesViewModel) {
        AlertDialog.Builder(this).setTitle("No Internet Connection")
            .setMessage("Please check your internet connection and try to launch application again")
            .setPositiveButton(android.R.string.ok) { _, _ -> }
            .setIcon(android.R.drawable.ic_dialog_alert).show()
            .setButton(
                Dialog.BUTTON_POSITIVE, "Refresh"
            ) { dialog, which ->
                finish()
            }
    }

    fun showAlertDialog(userNotesViewModel: UserNotesViewModel){
        AlertDialog.Builder(this).setTitle("No Internet Connection")
            .setMessage("Please check your internet connection and try again")
            .setPositiveButton(android.R.string.ok) { _, _ -> }
            .setIcon(android.R.drawable.ic_dialog_alert).show()
            .setButton(
                Dialog.BUTTON_POSITIVE, "Refresh"
            ) { dialog, which ->
                if (isNetworkConnected(this)) {
                    retrieveUserNotes(userNotesViewModel)
                }else {showAlertDialog(userNotesViewModel)} }
    }

    private fun retrieveSessionId(
        userNotesViewModel: UserNotesViewModel
    ) {
        val mainActivityJob = Job()

        val errorHandler = CoroutineExceptionHandler { _, exception ->
            AlertDialog.Builder(this).setTitle("Error")
                .setMessage(exception.message)
                .setPositiveButton(android.R.string.ok) { _, _ -> }
                .setIcon(android.R.drawable.ic_dialog_alert).show()
        }
        val coroutineScope = CoroutineScope(mainActivityJob + Dispatchers.Main)
        coroutineScope.launch(errorHandler) {
                userNotesViewModel.initSession().observe(getOwner(), Observer {
                    it?.let {
                        Log.d("myLogs", "MainActivity SessionId=" + it.data.session)
                        sessionId = it.data.session

                    }
                })
        }
    }

    private fun addUserNoteToServer(
        userNotesViewModel: UserNotesViewModel,
        userNote: UserNote
    ) {
        val mainActivityJob = Job()

        val errorHandler = CoroutineExceptionHandler { _, exception ->
            AlertDialog.Builder(this).setTitle("Error")
                .setMessage(exception.message)
                .setPositiveButton(android.R.string.ok) { _, _ -> }
                .setIcon(android.R.drawable.ic_dialog_alert).show()
        }
        val coroutineScope = CoroutineScope(mainActivityJob + Dispatchers.Main)
        coroutineScope.launch(errorHandler) {
                userNotesViewModel.addUserNoteToServer(sessionId, userNote.text).observe(
                    getOwner(),
                    Observer {
                        it?.let {
                            retrieveUserNotes(userNotesViewModel)
                        }
                    })
        }
    }

    private fun retrieveUserNotes(userNotesViewModel: UserNotesViewModel){
        val mainActivityJob = Job()

        val errorHandler = CoroutineExceptionHandler { _, exception ->
            AlertDialog.Builder(this).setTitle("Error")
                .setMessage(exception.message)
                .setPositiveButton(android.R.string.ok) { _, _ -> }
                .setIcon(android.R.drawable.ic_dialog_alert).show()
            mainActivityJob.cancel()
        }
        val coroutineScope = CoroutineScope(mainActivityJob + Dispatchers.Main)
        coroutineScope.launch(errorHandler) {
                userNotesViewModel.getUserNotes(sessionId).observe(getOwner(), Observer {
                    it?.let {
                        if (it.data.isNotEmpty()) {
                            userNoteListView.adapter = UserNoteListAdapter(object :
                                UserNoteListAdapter.OnItemClickListener {
                                override fun onItemClick(view: View, userNote: UserNote) {
                                    editUserNote(view, userNote)
                                }
                            }, it.data[0])
                            (userNoteListView.adapter as UserNoteListAdapter).updateUserNotes(it.data[0])
                            hideLoading()
                        }
                    }
                })
        }
    }

    private fun editUserNote(view: View, userNote: UserNote) {
        val intent = UserNoteDetailActivity.newIntent(this, userNote)
        val options = ViewCompat.getTransitionName(view)?.let {
            ActivityOptionsCompat.makeSceneTransitionAnimation(
                this,
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
    private fun getOwner(): UserNoteActivity{
        return this
    }

    override fun onDestroy() {
        super.onDestroy()
        database.userNoteDao().clearTable()
    }

    /*
    private fun isNetworkConnected(): Boolean {

        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.requestNetwork()
        val networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork)
        return networkCapabilities != null &&
                networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }

     */

    fun isNetworkConnected(context: Context?): Boolean {
        val info: NetworkInfo? = getNetworkInfo(context)
        return info != null && info.isConnected
    }
    fun getNetworkInfo(context: Context?): NetworkInfo? {
        val cm = context?.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        return cm.activeNetworkInfo
    }

     fun showLoading() {
        progressBar.visibility = View.VISIBLE
    }

     fun hideLoading() {
        progressBar.visibility = View.GONE
    }
}