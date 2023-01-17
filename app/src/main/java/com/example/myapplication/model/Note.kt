package com.example.myapplication.model

data class Note(
    val id: String? = null,
    val title: String? = null,
    val body: String? = null,
    val createdAt: Long? = null,
    val favorite: Boolean? = null
) {

    override fun toString(): String {
        return "$title\n$body"
    }

}
