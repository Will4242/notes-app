package models

data class Note(val noteTitle: String,
                val notePriority: Int,
                val noteCategory: String,
                var isNoteArchived :Boolean){
}