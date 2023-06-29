package com.prasad.abbreviationsfinder.utils

import org.junit.Assert.*

import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ValidationUtilTest {

    @Test
    fun validateAbbreviationTest() {
        val abbreviation = "HMM"
        assertEquals(true, ValidationUtil.isValidShorForm(abbreviation).first)
    }

    @Test
    fun validateEmptyAbbreviationTest() {
        val abbreviation = ""
        assertEquals(false, ValidationUtil.isValidShorForm(abbreviation).first)
    }

    @Test
    fun validateSingleCharAbbreviationTest() {
        val abbreviation = "a"
        assertEquals(false, ValidationUtil.isValidShorForm(abbreviation).first)
    }

    @Test
    fun validateSpecialCharAbbreviationTest() {
        val abbreviation = "a@a"
        assertEquals(false, ValidationUtil.isValidShorForm(abbreviation).first)
    }

    @Test
    fun validateDictionaryWordTest() {
        val abbreviation = "Love"
        assertEquals(true, ValidationUtil.isValidWord(abbreviation).first)
    }

    @Test
    fun validateEmptyDictionaryWordTest() {
        val abbreviation = ""
        assertEquals(false, ValidationUtil.isValidWord(abbreviation).first)
    }

    @Test
    fun validateSingleCharDictionaryWordTest() {
        val abbreviation = "a"
        assertEquals(false, ValidationUtil.isValidWord(abbreviation).first)
    }

    @Test
    fun validateSpecialCharDictionaryWordTest() {
        val abbreviation = "a@a"
        assertEquals(false, ValidationUtil.isValidWord(abbreviation).first)
    }
}