package com.genxsol.elections.data.repository

import com.genxsol.elections.api.Candidate
import com.genxsol.elections.api.ResultsService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton
import com.genxsol.elections.api.Results
import com.genxsol.elections.data.database.DatabaseService
import com.genxsol.elections.data.database.entity.CandidateEntity
import kotlinx.coroutines.flow.first

@Singleton
class ElectionsRepository @Inject constructor(
    private val network: ResultsService,
    private val database: DatabaseService
) {
    suspend fun getPollResults(): Flow<Results> {
        return flow {
            emit(
                network.latestResults()
            )
        }.catch {
            throw Exception("Network error")
        }
    }

    suspend fun getAllCandidates(): Flow<List<CandidateEntity>> {
        val cachedCandidates = database.getAllCandidates()
        return if (cachedCandidates.first().isEmpty()) {
            try {
                val candidatesEntity = network.allCandidates().map { candidate ->
                    candidate.toCandidateEntity()
                }
                database.deleteAllAndInsertAll(candidatesEntity)
                database.getAllCandidates()
            }catch(e: Exception){
                throw Exception("Network error")
            }
        } else {
            cachedCandidates
        }
    }
}

fun Candidate.toCandidateEntity(): CandidateEntity {
    return CandidateEntity(id = this.id, name = this.name)
}