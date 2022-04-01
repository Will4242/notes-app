package utils

import org.junit.jupiter.api.Assertions.*

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import utils.Helper.categories
import utils.Helper.isValidCategory
import utils.Helper.isValidPriority
import utils.Helper.isValidStatus
import utils.Helper.statuses

internal class HelperTest {
    @Nested
    inner class CategoryVal {
        @Test
        fun categoriesReturnsFullCategoriesSet() {
            Assertions.assertEquals(5, categories.size)
            Assertions.assertTrue(categories.contains("work"))
            Assertions.assertTrue(categories.contains("college"))
            Assertions.assertTrue(categories.contains("home"))
            Assertions.assertTrue(categories.contains("sport"))
            Assertions.assertTrue(categories.contains("holidays"))
            Assertions.assertFalse(categories.contains(""))
        }

        @Test
        fun isValidCategoryTrueWhenCategoryExists() {
            Assertions.assertTrue(isValidCategory("Home"))
            Assertions.assertTrue(isValidCategory("home"))
            Assertions.assertTrue(isValidCategory("COLLEGE"))
            Assertions.assertTrue(isValidCategory("Sport"))
        }

        @Test
        fun isValidCategoryFalseWhenCategoryDoesNotExist() {
            Assertions.assertFalse(isValidCategory("Hom"))
            Assertions.assertFalse(isValidCategory("colllege"))
            Assertions.assertFalse(isValidCategory(""))
        }
    }
    @Nested
    inner class StatusVal {
        @Test
        fun StatusesReturnsFullStatusSet() {
            Assertions.assertEquals(3, statuses.size)
            Assertions.assertTrue(statuses.contains("todo"))
            Assertions.assertTrue(statuses.contains("doing"))
            Assertions.assertTrue(statuses.contains("done"))
            Assertions.assertFalse(categories.contains(""))
        }

        @Test
        fun isValidStatusTrueWhenStatusExists() {
            Assertions.assertTrue(isValidStatus("todo"))
            Assertions.assertTrue(isValidStatus("toDo"))
            Assertions.assertTrue(isValidStatus("dOing"))
            Assertions.assertTrue(isValidStatus("DONE"))
        }

        @Test
        fun isValidStatusFalseWhenStatusDoesNotExist() {
            Assertions.assertFalse(isValidStatus("todos"))
            Assertions.assertFalse(isValidStatus("doingss"))
            Assertions.assertFalse(isValidStatus("donne"))
            Assertions.assertFalse(isValidStatus(""))
        }
    }
    @Nested
    inner class PriorityVal {

        @Test
        fun isValidPriorityTrueWhenPriorityExists() {
            Assertions.assertTrue(isValidPriority(1))
            Assertions.assertTrue(isValidPriority(2))
            Assertions.assertTrue(isValidPriority(3))
            Assertions.assertTrue(isValidPriority(4))
            Assertions.assertTrue(isValidPriority(4))
        }

        @Test
        fun isValidPriorityFalseWhenPriorityDoesNotExist() {
            Assertions.assertFalse(isValidPriority(-2))
            Assertions.assertFalse(isValidPriority(0))
            Assertions.assertFalse(isValidPriority(6))
        }
    }
}