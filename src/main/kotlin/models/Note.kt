package models

import com.fasterxml.jackson.annotation.JsonProperty

data class Note(
    var noteTitle: String,
    var noteStatus: String,
    var notePriority: Int,
    var noteCategory: String,
    @get : JsonProperty ("isNoteArchived") var isNoteArchived :Boolean){


    override fun toString(): String {
        return "Note(noteTitle='$noteTitle', noteStatus='$noteStatus', notePriority=$notePriority, noteCategory='$noteCategory', isNoteArchived=$isNoteArchived)"
    }
}