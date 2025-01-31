package uk.com.genxsol.elections.api.internal

import com.genxsol.elections.api.Candidate
import com.genxsol.elections.api.Results
import com.genxsol.elections.api.Result
import com.genxsol.elections.api.ResultsRepository
import com.genxsol.elections.api.ResultsServiceImpl
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class ResultsServiceImplTest {

    private lateinit var service: ResultsServiceImpl

    private lateinit var stubResultsRepository: StubResultsRepository

    @Before
    fun setUp() {
        stubResultsRepository = StubResultsRepository()

        service = ResultsServiceImpl(stubResultsRepository)
    }

    @Test
    fun testLatestResults() = runTest {
        val mockResult = Result(1, "party", 1)
        val mockResults = Results(false, listOf(mockResult))
        stubResultsRepository.latestResultsStubResult = mockResults

        val results = service.latestResults()

        Assert.assertFalse(results.isComplete)
        Assert.assertEquals(1, results.results.size)
    }
}

class StubResultsRepository: ResultsRepository {

    var latestResultsInvoked = false
    var latestResultsInvocationCount = 0
    lateinit var latestResultsStubResult: Results

    override suspend fun latestResults(): Results {
        latestResultsInvoked = true
        latestResultsInvocationCount += 1
        return latestResultsStubResult
    }


    var allCandidatesInvoked = false
    var allCandidatesInvocationCount = 0
    lateinit var allCandidatesStubResult: List<Candidate>

    override suspend fun allCandidates(): List<Candidate> {
        allCandidatesInvoked = true
        allCandidatesInvocationCount += 1
        return allCandidatesStubResult
    }
}
