package com.example.myapplication

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

object NoteCollections {

    val notesCollection = Firebase.firestore.collection("notes")

}