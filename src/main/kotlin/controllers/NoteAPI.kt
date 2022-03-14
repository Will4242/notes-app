package controllers

import listNotes
import models.Note
import persistence.Serializer
import utils.ScannerInput.readNextInt
import kotlin.math.sign

class NoteAPI(serializerType: Serializer){

    private var serializer: Serializer = serializerType

    @Throws(Exception::class)
    fun load() {
        notes = serializer.read() as ArrayList<Note>
    }

    @Throws(Exception::class)
    fun store() {
        serializer.write(notes)
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

    fun listAllNotes(): String {
        return if (notes.isEmpty()) {
            "No notes stored"
        } else {
            var listOfNotes = ""
            for (i in notes.indices) {
                listOfNotes += "${i}: ${notes[i]} \n"
            }
            listOfNotes
        }
    }

    fun updateNote(indexToUpdate: Int, note: Note?): Boolean {
        //find the note object by the index number
        val foundNote = findNote(indexToUpdate)

        //if the note exists, use the note details passed as parameters to update the found note in the ArrayList.
        if ((foundNote != null) && (note != null)) {
            foundNote.noteTitle = note.noteTitle
            foundNote.notePriority = note.notePriority
            foundNote.noteCategory = note.noteCategory
            return true
        }

        //if the note was not found, return false, indicating that the update was not successful
        return false
    }

    fun isValidIndex(index: Int) :Boolean{
        return isValidListIndex(index, notes);
    }

    fun numberOfNotes(): Int {
        return notes.size
    }

    fun findNote(index: Int): Note? {
        return if (isValidListIndex(index, notes)) {
            notes[index]
        } else null
    }

    //utility method to determine if an index is valid in a list.
    fun isValidListIndex(index: Int, list: List<Any>): Boolean {
        return (index >= 0 && index < list.size)
    }

    fun listActiveNotes(): String {
            return if (notes.isEmpty()) {
                "No notes stored"
            } else {
                var listOfNotes = ""
                for (i in notes.indices) {
                    if(!notes[i].isNoteArchived)
                    listOfNotes += "${i}: ${notes[i]} \n"
                }
                if(listOfNotes.isEmpty())
                    "no active notes"
                else listOfNotes
            }
    }

    //took out explanation mark to list archive
    fun listArchivedNotes(): String {
        return if (notes.isEmpty()) {
            "No notes stored"
        } else {
            var listOfNotes = ""
            for (i in notes.indices) {
                if(notes[i].isNoteArchived)
                    listOfNotes += "${i}: ${notes[i]} \n"
            }
            if(listOfNotes.isEmpty())
                "no archived notes"
            else listOfNotes
        }
    }

    fun numberOfArchivedNotes(): Int {
        return notes.count({it.isNoteArchived})
}

    fun numberOfActiveNotes(): Int {
        return notes.count({!it.isNoteArchived})
    }

    fun listNotesBySelectedPriority(priority: Int): String {

        return if (notes.isEmpty()) {
            "No notes stored"
        } else {
            var listOfNotes = ""
            for (i in notes.indices) {

                if(notes[i].notePriority==priority)
                    listOfNotes += "${i}: ${notes[i]} \n"
            }
           if(!listOfNotes.isEmpty())
               listOfNotes
            else "No notes fpr priority ${priority}"
    }
}

    fun numberOfNotesByPriority(priority: Int): Int{
        return notes.count({it.notePriority==priority})
}

   fun notesSortedByPriority(): String{
        notes.sortBy { it.notePriority }
       return listActiveNotes()
   }

    fun notesSortedByTitle(): String{
        notes.sortBy { it.noteTitle }
        return listActiveNotes()
}

    fun notesSortedByCategory(): String{
        notes.sortBy { it.noteCategory }
        return listActiveNotes()
    }
}