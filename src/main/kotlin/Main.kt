import controllers.NoteAPI
import models.Note
import mu.KotlinLogging
import persistence.JSONSerializer
import utils.ScannerInput
import utils.ScannerInput.readNextInt
import utils.ScannerInput.readNextLine
import java.io.File
import java.lang.System.exit



//private val noteAPI = NoteAPI(XMLSerializer(File("notes.xml")))
private val noteAPI = NoteAPI(JSONSerializer(File("notes.json")))

private val logger = KotlinLogging.logger {}


fun main(args: Array<String>) {
    runMenu()
}

fun mainMenu() : Int {
    return ScannerInput.readNextInt(""" 
         > ----------------------------------
         > |        NOTE KEEPER APP         |
         > ----------------------------------
         > | NOTE MENU                      |
         > |   1) Add a note                |
         > |   2) List Notes                |
         > |   3) Update a note             |
         > |   4) Delete a note             |
         > |   5) Archive a note            |
         > |   6) save                      |
         > |   7) load                      |
         > ----------------------------------
         > |   0) Exit                      |
         > ----------------------------------
         > ==>> """.trimMargin(">"))
}

fun runMenu() {
    do {
        val option = mainMenu()
        when (option) {
            1  -> addNote()
            2  -> listNotes()
            3  -> updateNote()
            4  -> deleteNote()
            5  -> archiveNote()
            6  -> save()
            7  -> load()
            0  -> exitApp()
            else -> System.out.println("Invalid option entered: ${option}")
        }
    } while (true)
}

fun listNotes() {
    if (noteAPI.numberOfNotes() > 0) {
        val option = readNextInt(
            """
                  > ----------------------------------
                  > |   1) View ALL notes            |
                  > |   2) View ACTIVE notes         |
                  > |   3) View ARCHIVED notes       |
                  > |   4) Number of archived notes  |
                  > |   5) Number of active notes    |
                  > |   6) View notes by priority    |
                  > |   7) View ordered by priority  |
                  > |   8) View ordered by title     |
                  > |   9) View ordered by category  |
                  > |  10) View selected category    |
                  > ----------------------------------
         > ==>> """.trimMargin(">"))

        when (option) {
            1  -> listAllNotes();
            2  -> listActiveNotes();
            3  -> listArchivedNotes();
            4  -> numberOfArchivedNotes();
            5  -> numberOfActiveNotes();
            6  -> listNotesBySelectedPriority();
            7  -> notesSortedByPriority();
            8  -> notesSortedByTitle();
            9  -> notesSortedByCategory();
            10 -> listNotesBySelectedCategory();
            else -> println("Invalid option entered: " + option);
        }
    } else {
        println("Option Invalid - No notes stored");
    }
}


    fun save() {
        try {
            noteAPI.store()
        } catch (e: Exception) {
            System.err.println("Error writing to file: $e")
        }
    }

    fun load() {
        try {
            noteAPI.load()
        } catch (e: Exception) {
            System.err.println("Error reading from file: $e")
        }
    }

fun addNote(){
    //logger.info { "addNote() function invoked" }
    val noteTitle = readNextLine("Enter a title for the note: ")
    val notePriority = readNextInt("Enter a priority (1-low, 2, 3, 4, 5-high): ")
    val noteCategory = readNextLine("Enter a category for the note: ")
    val isAdded = noteAPI.add(Note(noteTitle, notePriority, noteCategory, false))

    if (isAdded) {
        println("Added Successfully")
    } else {
        println("Add Failed")
    }
}

fun listAllNotes(){
    //logger.info { "listNotes() function invoked" }
    println(noteAPI.listAllNotes())
}

fun updateNote() {
    //logger.info { "updateNotes() function invoked" }
    listNotes()
    if (noteAPI.numberOfNotes() > 0) {
        //only ask the user to choose the note if notes exist
        val indexToUpdate = readNextInt("Enter the index of the note to update: ")
        if (noteAPI.isValidIndex(indexToUpdate)) {
            val noteTitle = readNextLine("Enter a title for the note: ")
            val notePriority = readNextInt("Enter a priority (1-low, 2, 3, 4, 5-high): ")
            val noteCategory = readNextLine("Enter a category for the note: ")

            //pass the index of the note and the new note details to NoteAPI for updating and check for success.
            if (noteAPI.updateNote(indexToUpdate, Note(noteTitle, notePriority, noteCategory, false))){
                println("Update Successful")
            } else {
                println("Update Failed")
            }
        } else {
            println("There are no notes for this index number")
        }
    }
}

fun deleteNote(){
    //logger.info { "deleteNotes() function invoked" }
    listNotes()
    if (noteAPI.numberOfNotes() > 0) {
        //only ask the user to choose the note to delete if notes exist
        val indexToDelete = readNextInt("Enter the index of the note to delete: ")
        //pass the index of the note to NoteAPI for deleting and check for success.
        val noteToDelete = noteAPI.deleteNote(indexToDelete)
        if (noteToDelete != null) {
            println("Delete Successful! Deleted note: ${noteToDelete.noteTitle}")
        } else {
            println("Delete NOT Successful")
        }
    }
}

fun exitApp(){
    println("Exiting...bye")
    exit(0)
}

fun listActiveNotes() {
    println(noteAPI.listActiveNotes())
}

fun listArchivedNotes() {
    println(noteAPI.listArchivedNotes())
}

fun numberOfArchivedNotes() {
    println(noteAPI.numberOfArchivedNotes())
}
fun numberOfActiveNotes() {
    println(noteAPI.numberOfActiveNotes())
}
fun listNotesBySelectedPriority(){
    val chosenPriority=ScannerInput.readNextInt("Enter priority ")
    println(noteAPI.listNotesBySelectedPriority(chosenPriority))
    println("There are ${noteAPI.numberOfNotesByPriority(chosenPriority)} for this priority")
}
fun notesSortedByPriority(){
    println(noteAPI.notesSortedByPriority())
}
fun notesSortedByTitle(){
    println(noteAPI.notesSortedByTitle())
}
fun notesSortedByCategory(){
    println(noteAPI.notesSortedByCategory())
}
//lists selected category ordered by priority with number of notes in that category
fun listNotesBySelectedCategory(){
    val chosenCategory=ScannerInput.readNextLine("Enter category ")
    println(noteAPI.listNotesBySelectedCategory(chosenCategory))
    println("There are ${noteAPI.numberOfNotesByCategory(chosenCategory)} notes for this category")
}
fun archiveNote() {
    listActiveNotes()
    if (noteAPI.numberOfActiveNotes() > 0) {
        val indexToArchive = readNextInt("Enter the index of the note to archive: ")
        if (noteAPI.archiveNote(indexToArchive)) {
            println("Archive Successful!")
        } else {
            println("Archive NOT Successful")
        }
    }
}