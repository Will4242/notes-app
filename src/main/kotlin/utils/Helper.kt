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
}