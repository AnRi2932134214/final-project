package com.example.myapplication.data

import com.example.myapplication.NoteCollections
import com.example.myapplication.model.Note
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await

object NotesProvider {

    fun saveNote(note: Note): Unit = runBlocking {
        NoteCollections.notesCollection
            .add(note)
            .await()
    }

    fun observeAllNotes(observer: (List<Note>) -> Unit) {
        NoteCollections.notesCollection.addSnapshotListener { value, error ->
            error?.let { return@addSnapshotListener }
            observer.invoke(value?.toObjects(Note::class.java) ?: emptyList())
        }
    }

    fun editNote(oldNote: Note, newNoteValues: Map<String, Any>): Unit = runBlocking {
        val noteDocument = NoteCollections.notesCollection
            .whereEqualTo("id", oldNote.id)
            .get()
            .await()
            .documents[0]
        NoteCollections.notesCollection.document(noteDocument.id).set(
            newNoteValues,
            SetOptions.merge()
        ).await()
    }

    fun setNoteFavorite(note: Note, isFavorite: Boolean) {
        editNote(
            oldNote = note,
            newNoteValues = mapOf(
                "favorite" to isFavorite
            )
        )
    }

    fun deleteNote(note: Note): Unit = runBlocking {
        val noteDocument = NoteCollections.notesCollection
            .whereEqualTo("id", note.id)
            .get()
            .await()
            .documents[0]
        NoteCollections.notesCollection.document(noteDocument.id).delete().await()
    }

}