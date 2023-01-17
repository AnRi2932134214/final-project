package com.example.myapplication.dialogs

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.example.myapplication.R
import com.example.myapplication.data.NotesProvider
import com.example.myapplication.model.Note
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.runBlocking
import java.util.UUID

class AddNoteDialog(context: Context) : Dialog(context) {

    private lateinit var titleEditText: EditText
    private lateinit var bodyEditText: EditText
    private lateinit var cancelButton: Button
    private lateinit var saveButton: Button

    init {
        setCancelable(false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_add_note)

        init()

        setListeners()

    }

    private fun init() {
        titleEditText = findViewById(R.id.titleEditText)
        bodyEditText = findViewById(R.id.bodyEditText)
        cancelButton = findViewById(R.id.cancelButton)
        saveButton = findViewById(R.id.saveButton)
    }

    private fun setListeners() {
        cancelButton.setOnClickListener {
            this.dismiss()
        }
        saveButton.setOnClickListener {
            NotesProvider.saveNote(
                Note(
                    id = UUID.randomUUID().toString(),
                    title = titleEditText.text.toString(),
                    body = bodyEditText.text.toString(),
                    createdAt = System.currentTimeMillis(),
                    favorite = false
                )
            )
            this.dismiss()
        }
    }

}