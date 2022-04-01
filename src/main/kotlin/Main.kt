import controllers.NoteAPI
import models.Note
import mu.KotlinLogging
import persistence.JSONSerializer
import persistence.YAMLSerializer
import utils.Helper.isValidCategory
import utils.Helper.isValidPriority
import utils.Helper.isValidStatus
import utils.ScannerInput
import utils.ScannerInput.readNextInt
import utils.ScannerInput.readNextLine
import java.io.File
import java.lang.System.exit

// private val noteAPI = NoteAPI(XMLSerializer(File("notes.xml")))
 private val noteAPI = NoteAPI(JSONSerializer(File("notes.json")))
//private val noteAPI = NoteAPI(YAMLSerializer(File("notes.yaml")))

private val logger = KotlinLogging.logger {}

fun main(args: Array<String>) {
    runMenu()
}

fun mainMenu(): Int {
    return ScannerInput.readNextInt(
        """ 
         > ----------------------------------
         > |        NOTE KEEPER APP         |
         > ----------------------------------
         > | NOTE MENU                      |
         > |   1) Add a note                |
         > |   2) List Notes                |
         > |   3) Update a note             |
         > |   4) Delete a note             |
         > |   5) Archive a note            |
         > |   6) Update Status             |
         > |   7) save                      |
         > |   8) load                      |
         > ----------------------------------
         > |   0) Exit                      |
         > ----------------------------------
         > ==>> """.trimMargin(">")
    )
}

fun runMenu() {
    do {
        val option = mainMenu()
        when (option) {
            1 -> addNote()
            2 -> listNotes()
            3 -> updateNote()
            4 -> deleteNote()
            5 -> archiveNote()
            6 -> updateStatus()
            7 -> save()
            8 -> load()
            0 -> exitApp()
            else -> System.out.println("Invalid option entered: $option")
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
                  > |  10) Search category           |
                  > |  11) Search title              |
                  > |  12) Search status             |
                  > ----------------------------------
         > ==>> """.trimMargin(">")
        )

        when (option) {
            1 -> listAllNotes()
            2 -> listActiveNotes()
            3 -> listArchivedNotes()
            4 -> numberOfArchivedNotes()
            5 -> numberOfActiveNotes()
            6 -> listNotesBySelectedPriority()
            7 -> notesSortedByPriority()
            8 -> notesSortedByTitle()
            9 -> notesSortedByCategory()
            10 -> searchNotesByCategory()
            11 -> searchNotesByTitle()
            12 -> searchNotesByStatus()
            else -> println("Invalid option entered: " + option)
        }
    } else {
        println("Option Invalid - No notes stored")
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

fun addNote() {
    // logger.info { "addNote() function invoked" }
    val noteTitle = readNextLine("Enter a title for the note: ")

    var noteStatus = readNextLine("Enter a status for the note (ToDo, Doing, Done: ")
    while (!isValidStatus(noteStatus)) {
        noteStatus = readNextLine("Invalid status, enter a status for the note (ToDo, Doing, Done: ")
    }

    var notePriority = readNextInt("Enter a priority (1-low, 2, 3, 4, 5-high): ")
    while (!isValidPriority(notePriority)) {
        notePriority = readNextInt("Invalid priority, enter a priority (1-low, 2, 3, 4, 5-high): ")
    }

    var noteCategory = readNextLine("Enter a category for the note (Work,College,Home,Sport,Holiday): ")
    while (!isValidCategory(noteCategory)) {
        noteCategory = readNextLine("Invalid category, enter a category (Work,College,Home,Sport,Holiday): ")
    }

    val isAdded = noteAPI.add(Note(noteTitle, noteStatus, notePriority, noteCategory, false))
    if (isAdded) {
        println("Added Successfully")
    } else {
        println("Add Failed")
    }
}

fun listAllNotes() {
    // logger.info { "listNotes() function invoked" }
    println(noteAPI.listAllNotes())
}

fun updateNote() {
    // logger.info { "updateNotes() function invoked" }
    listAllNotes()
    if (noteAPI.numberOfNotes() > 0) {
        // only ask the user to choose the note if notes exist
        val indexToUpdate = readNextInt("Enter the index of the note to update: ")
        if (noteAPI.isValidIndex(indexToUpdate)) {
            val noteTitle = readNextLine("Enter a title for the note: ")

            var noteStatus = readNextLine("Enter a status for the note (ToDo, Doing, Done: ")
            while (!isValidStatus(noteStatus)) {
                noteStatus = readNextLine("Invalid status, enter a status for the note (ToDo, Doing, Done: ")
            }

            var notePriority = readNextInt("Enter a priority (1-low, 2, 3, 4, 5-high): ")
            while (!isValidPriority(notePriority)) {
                notePriority = readNextInt("Invalid priority, enter a priority (1-low, 2, 3, 4, 5-high): ")
            }

            var noteCategory = readNextLine("Enter a category for the note (Work,College,Home,Sport,Holiday): ")
            while (!isValidCategory(noteCategory)) {
                noteCategory = readNextLine("Invalid category, enter a category (Work,College,Home,Sport,Holiday): ")
            }

            // pass the index of the note and the new note details to NoteAPI for updating and check for success.
            if (noteAPI.updateNote(indexToUpdate, Note(noteTitle, noteStatus, notePriority, noteCategory, false))) {
                println("Update Successful")
            } else {
                println("Update Failed")
            }
        } else {
            println("There are no notes for this index number")
        }
    }
}

fun deleteNote() {
    // logger.info { "deleteNotes() function invoked" }
    listAllNotes()
    if (noteAPI.numberOfNotes() > 0) {
        // only ask the user to choose the note to delete if notes exist
        val indexToDelete = readNextInt("Enter the index of the note to delete: ")
        // pass the index of the note to NoteAPI for deleting and check for success.
        val noteToDelete = noteAPI.deleteNote(indexToDelete)
        if (noteToDelete != null) {
            println("Delete Successful! Deleted note: ${noteToDelete.noteTitle}")
        } else {
            println("Delete NOT Successful")
        }
    }
}

fun exitApp() {
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

fun listNotesBySelectedPriority() {
    val chosenPriority = ScannerInput.readNextInt("Enter priority ")
    println(noteAPI.listNotesBySelectedPriority(chosenPriority))
    println("There are ${noteAPI.numberOfNotesByPriority(chosenPriority)} notes for this priority")
}

fun notesSortedByPriority() {
    println(noteAPI.notesSortedByPriority())
}

fun notesSortedByTitle() {
    println(noteAPI.notesSortedByTitle())
}

fun notesSortedByCategory() {
    println(noteAPI.notesSortedByCategory())
}

fun searchNotesByCategory() {
    val searchCategory = readNextLine("Enter the category to search by: ")
    val searchResults = noteAPI.searchNotesByCategory(searchCategory)
    if (searchResults.isEmpty()) {
        println("No notes found")
    } else {
        println(searchResults)
        println("There are ${noteAPI.numberOfNotesByCategory(searchCategory)} notes for this category")
    }
}

fun searchNotesByTitle() {
    val searchTitle = readNextLine("Enter the description to search by: ")
    val searchResults = noteAPI.searchNotesByTitle(searchTitle)
    if (searchResults.isEmpty()) {
        println("No notes found")
    } else {
        println(searchResults)
    }
}

fun searchNotesByStatus() {
    val searchStatus = readNextLine("Enter the status to search by: ")
    val searchResults = noteAPI.searchNotesByStatus(searchStatus)
    if (searchResults.isEmpty()) {
        println("No notes found")
    } else {
        println(searchResults)
    }
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
fun updateStatus() {
    listAllNotes()
    if (noteAPI.numberOfNotes() > 0) {
        val indexToStatus = readNextInt("Enter the index of the note to update: ")
        if (noteAPI.isValidIndex(indexToStatus)) {
            var noteStatus = readNextLine("Enter a status for the note (ToDo, Doing, Done: ")
            while (!isValidStatus(noteStatus)) {
                noteStatus = readNextLine("Invalid status, enter a status for the note (ToDo, Doing, Done: ")
            }
            if (noteAPI.updateStatus(indexToStatus, noteStatus)) {
                println("Update Successful")
            } else {
                println("Update Failed")
            }
        } else {
            println("There are no notes for this index number")
        }
    }
}
