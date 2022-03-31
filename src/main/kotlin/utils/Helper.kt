package utils

object Helper {
    //utility method to determine if an index is valid in a list.
    @JvmStatic
    fun isValidListIndex(index: Int, list: List<Any>): Boolean {
        return (index >= 0 && index < list.size)
    }
    @JvmStatic
    fun isValidPriority(priority: Int): Boolean {
        return (priority in 1..5)
    }
    @JvmStatic
    fun isValidCategory(category: String): Boolean {
        var categories = listOf("work","college","home","sport","holidays")
        return categories.contains(category.lowercase())
    }
    @JvmStatic
    fun isValidStatus(status: String): Boolean {
        var statuses = listOf("todo", "doing", "done")
        return statuses.contains(status.lowercase())
    }
}