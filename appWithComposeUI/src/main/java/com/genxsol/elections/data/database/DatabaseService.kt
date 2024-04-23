package com.genxsol.elections.data.database

import com.genxsol.elections.data.database.entity.CandidateEntity
import kotlinx.coroutines.flow.Flow

interface DatabaseService {
    suspend fun getAllCandidates(): Flow<List<CandidateEntity>>
    suspend fun deleteAllAndInsertAll(candidates: List<CandidateEntity>)
}