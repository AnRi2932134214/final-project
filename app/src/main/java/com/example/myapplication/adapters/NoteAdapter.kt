package com.example.myapplication.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.model.Note

class NoteAdapter : ListAdapter<Note, NoteAdapter.NoteViewHolder>(NoteItemCallback) {

    var onItemClick: (Note) -> Unit = {  }

    var onItemLongClick: (Note) -> Unit = {  }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return NoteViewHolder(inflater.inflate(R.layout.item_note, parent, false))
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind()
    }

    inner class NoteViewHolder(private val view: View): RecyclerView.ViewHolder(view) {
        private val titleTextView: TextView = view.findViewById(R.id.titleTextView)
        private val bodyTextView: TextView = view.findViewById(R.id.bodyTextView)

        fun bind() {
            val note = getItem(adapterPosition)
            titleTextView.text = note.title
            bodyTextView.text = note.body
            view.setOnClickListener {
                onItemClick.invoke(note)
            }
            view.setOnLongClickListener {
                onItemLongClick.invoke(note)
                true
            }
        }
    }

    object NoteItemCallback : DiffUtil.ItemCallback<Note>() {
        override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem == newItem
        }
    }

}