package com.genxsol.elections.domain.model

import com.genxsol.elections.api.Results
import com.genxsol.elections.data.database.entity.CandidateEntity
import kotlinx.coroutines.flow.Flow

interface ElectionsRepository {
    suspend fun getPollResults(): Flow<Results>
    suspend fun getAllCandidates(): Flow<List<CandidateEntity>>
}