package com.example.bnet.ui.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bnet.R
import com.example.bnet.app.App
import com.example.bnet.app.AppDatabase
import com.example.bnet.data.UserNote
import com.example.bnet.extencions.ctx
import kotlinx.android.synthetic.main.user_note_item.view.*
import java.text.SimpleDateFormat
import java.util.*

class UserNoteListAdapter (private val onItemClickListener: OnItemClickListener,
                           private var userNotesList: List<UserNote>):
    RecyclerView.Adapter<UserNoteListAdapter.ViewHolder>() {
    var database: AppDatabase = App.database
    //private var userNotesList=database.userNoteDao().getUserNotes().value

    private val mInternalListener = View.OnClickListener { view ->
        val userNote= view.tag as UserNote
        onItemClickListener.onItemClick(view, userNote)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.ctx).inflate(R.layout.user_note_item, parent, false)
        return ViewHolder(view);
    }

    class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {
        private val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
        fun bindUserNotes(userNote: UserNote, onItemClickListener: OnItemClickListener){
            itemView.userNoteTextView.text = userNote.text
            itemView.userNoteCardView.setOnClickListener{
                onItemClickListener.onItemClick(itemView.userNoteCardView, userNote)}
            Log.d("myLogs", "im in UserNoteListAdapter, bindUserNotes ")

                itemView.noteModifyDateView.text = simpleDateFormat.format(userNote.modified)
                itemView.noteCreateDateView.text = simpleDateFormat.format(userNote.created)
            if(userNote.modified!=userNote.created){
                itemView.noteModifyDateView.visibility= View.VISIBLE
            }
        }

    }

    fun updateUserNotes(userNotesList: List<UserNote>) {
        this.userNotesList = userNotesList
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = userNotesList.size
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        userNotesList?.get(position)?.let { holder.bindUserNotes(it, onItemClickListener) }
        holder.itemView.setOnClickListener(mInternalListener)
        holder.itemView.tag = userNotesList?.get(position)
    }

    interface OnItemClickListener {
        fun onItemClick(view: View, userNote: UserNote)
    }
}