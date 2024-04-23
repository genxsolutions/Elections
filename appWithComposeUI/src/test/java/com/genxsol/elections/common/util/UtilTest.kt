package com.genxsol.elections.common.util

import org.junit.Assert.assertEquals
import org.junit.Test
import com.genxsol.elections.api.Result

class UtilTest {

    @Test
    fun `sort results by max votes`() {
        // Arrange

         val results = listOf(
             Result(candidateId = 1, party = "party1", votes= 20),
             Result(candidateId = 2, party = "party2", votes= 10),
             Result(candidateId = 3, party = "party3", votes= 30),
             )

        // Act
        val sortedResults = results.sortResultsByVotes()

        // Assert
        assertEquals(3, sortedResults[0].candidateId)
        assertEquals(1, sortedResults[1].candidateId)
        assertEquals(2, sortedResults[2].candidateId)
    }
}
