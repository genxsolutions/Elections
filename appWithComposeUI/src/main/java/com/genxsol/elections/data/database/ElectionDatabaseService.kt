package com.genxsol.elections.data.database

import com.genxsol.elections.data.database.entity.CandidateEntity
import kotlinx.coroutines.flow.Flow

class ElectionDatabaseService(
    private val electionDatabase: ElectionDatabase
) : DatabaseService {
    override suspend fun getAllCandidates(): Flow<List<CandidateEntity>> {
       return electionDatabase.getCandidateDao().getAllCandidates()
    }

    override suspend fun deleteAllAndInsertAll(candidates: List<CandidateEntity>) {
        electionDatabase.getCandidateDao().deleteAllAndInsertAll(candidates)
    }
}