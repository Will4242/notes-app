package controllers

import models.Note
import persistence.Serializer
import utils.Helper.isValidListIndex

class NoteAPI(serializerType: Serializer) {

    private var serializer: Serializer = serializerType

    @Throws(Exception::class)
    fun load() {
        notes = serializer.read() as ArrayList<Note>
    }

    @Throws(Exception::class)
    fun store() {
        serializer.write(notes)
    }

    fun formatListString(notesToFormat: List<Note>): String =
        notesToFormat
            .joinToString(separator = "\n") { note ->
                notes.indexOf(note).toString() + ": " + note.toString()
            }

    private var notes = ArrayList<Note>()

    fun add(note: Note): Boolean {
        return notes.add(note)
    }

    fun deleteNote(indexToDelete: Int): Note? {
        return if (isValidListIndex(indexToDelete, notes)) {
            notes.removeAt(indexToDelete)
        } else null
    }

    fun listAllNotes(): String =
        if (notes.isEmpty()) "No notes stored"
        else formatListString(notes)

    fun updateNote(indexToUpdate: Int, note: Note?): Boolean {
        // find the note object by the index number
        val foundNote = findNote(indexToUpdate)

        // if the note exists, use the note details passed as parameters to update the found note in the ArrayList.
        if ((foundNote != null) && (note != null)) {
            foundNote.noteTitle = note.noteTitle
            foundNote.noteStatus = note.noteStatus
            foundNote.notePriority = note.notePriority
            foundNote.noteCategory = note.noteCategory
            return true
        }

        // if the note was not found, return false, indicating that the update was not successful
        return false
    }

    fun isValidIndex(index: Int): Boolean {
        return isValidListIndex(index, notes)
    }

    fun numberOfNotes(): Int {
        return notes.size
    }

    fun findNote(index: Int): Note? {
        return if (isValidListIndex(index, notes)) {
            notes[index]
        } else null
    }

    fun listActiveNotes(): String =
        if (numberOfActiveNotes() == 0) "No active notes stored"
        else formatListString(notes.filter { note -> !note.isNoteArchived })

    fun listArchivedNotes(): String =
        if (numberOfArchivedNotes() == 0) "No archived notes stored"
        else formatListString(notes.filter { note -> note.isNoteArchived })

    fun numberOfArchivedNotes(): Int {
        return notes.count({ it.isNoteArchived })
    }

    fun numberOfActiveNotes(): Int {
        return notes.count({ !it.isNoteArchived })
    }

    fun listNotesBySelectedPriority(priority: Int): String =
        formatListString(
            notes.filter { note -> note.notePriority == priority }
        )

    fun numberOfNotesByPriority(priority: Int): Int {
        return notes.count({ it.notePriority == priority })
    }

    fun notesSortedByPriority(): String {
        notes.sortBy { it.notePriority }
        return listActiveNotes()
    }

    fun notesSortedByTitle(): String {
        notes.sortBy { it.noteTitle }
        return listActiveNotes()
    }

    fun notesSortedByCategory(): String {
        notes.sortBy { it.noteCategory }
        return listActiveNotes()
    }

    fun numberOfNotesByCategory(category: String): Int {
        return notes.count { it.noteCategory == category }
    }

    fun searchNotesByCategory(category: String) =
        formatListString(
            notes.filter { note -> note.noteCategory.contains(category, ignoreCase = true) }.sortedBy { it.notePriority }
        )

    fun searchNotesByTitle(searchString: String) =
        formatListString(
            notes.filter { note -> note.noteTitle.contains(searchString, ignoreCase = true) }
        )

    fun searchNotesByStatus(status: String) =
        formatListString(
            notes.filter { note -> note.noteStatus.contains(status, ignoreCase = true) }
        )

    fun archiveNote(indexToArchive: Int): Boolean {
        if (isValidIndex(indexToArchive)) {
            val noteToArchive = notes[indexToArchive]
            if (!noteToArchive.isNoteArchived) {
                noteToArchive.isNoteArchived = true
                return true
            }
        }
        return false
    }

    fun updateStatus(indexToStatus: Int, status: String): Boolean {
        // find the note object by the index number
        val foundNote = findNote(indexToStatus)

        // if the note exists, use the note details passed as parameters to update the found note in the ArrayList.
        if ((foundNote != null)) {
            foundNote.noteStatus = status
            return true
        }

        // if the note was not found, return false, indicating that the update was not successful
        return false
    }
}
