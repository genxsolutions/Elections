package com.genxsol.elections.data.repository


import com.genxsol.elections.api.Result
import com.genxsol.elections.api.Results
import com.genxsol.elections.api.ResultsService
import com.genxsol.elections.data.database.DatabaseService
import com.genxsol.elections.data.database.entity.CandidateEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ElectionsRepositoryTest {

    @Mock
    private lateinit var apiInterface: ResultsService

    @Mock
    private lateinit var databaseService: DatabaseService

    private lateinit var electionsRepository: ElectionsRepository

    @Before
    fun setUp() {
        electionsRepository = ElectionsRepositoryImpl(apiInterface, databaseService)
    }

    @Test
    fun getPollResults_whenNetworkServiceResponseSuccess_shouldReturnSuccess() {
        runBlocking {
            // Arrange
            val results = Results(results = listOf(
                Result(candidateId = 1, party = "party1", votes= 20),
                Result(candidateId = 2, party = "party2", votes= 10),
                Result(candidateId = 3, party = "party3", votes= 30),
            ),
                isComplete = true
            )

            `when`(apiInterface.latestResults()).thenReturn(results)

            // Act
            val actual = electionsRepository.getPollResults().first()

            // Assert
            assertEquals(results, actual)
        }
    }

    @Test
    fun getCandidates_whenNetworkServiceResponseSuccess_shouldReturnSuccess() {
        runBlocking {
            // Arrange
            val candidatesEntities = listOf(
                CandidateEntity(id= 1, name= "candidate1"),
                CandidateEntity(id= 2, name= "candidate2"),
                CandidateEntity(id= 3, name= "candidate3")
            )

            `when`(databaseService.getAllCandidates()).thenReturn(flowOf(candidatesEntities))

            // Act
            val actual = electionsRepository.getAllCandidates().first()

            // Assert
            assertEquals(candidatesEntities, actual)
            verify(apiInterface, times(0)).allCandidates()
        }
    }

    @Test(expected = Exception::class)
    fun `allCandidates throws error on network failure`(): Unit = runBlocking {

        // Mock network error response
        `when`(apiInterface.allCandidates()).thenThrow(Exception("Network error"))

        electionsRepository.getAllCandidates()
    }

}