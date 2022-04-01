package utils

import org.junit.jupiter.api.Assertions.*

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import utils.Helper.categories
import utils.Helper.isValidCategory

internal class HelperTest {
    @Nested
    inner class CategoryVal {
        @Test
        fun categoriesReturnsFullCategoriesSet() {
            Assertions.assertEquals(5, categories.size)
            Assertions.assertTrue(categories.contains("home"))
            Assertions.assertTrue(categories.contains("college"))
            Assertions.assertFalse(categories.contains(""))
        }

        @Test
        fun isValidCategoryTrueWhenCategoryExists() {
            Assertions.assertTrue(isValidCategory("Home"))
            Assertions.assertTrue(isValidCategory("home"))
            Assertions.assertTrue(isValidCategory("COLLEGE"))
        }

        @Test
        fun isValidCategoryFalseWhenCategoryDoesNotExist() {
            Assertions.assertFalse(isValidCategory("Hom"))
            Assertions.assertFalse(isValidCategory("colllege"))
            Assertions.assertFalse(isValidCategory(""))
        }
    }

}