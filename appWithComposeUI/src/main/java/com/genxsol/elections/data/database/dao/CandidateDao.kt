package com.genxsol.elections.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.genxsol.elections.data.database.entity.CandidateEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CandidateDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(candidates: List<CandidateEntity>)

    @Query("SELECT * FROM candidates")
    fun getAllCandidates(): Flow<List<CandidateEntity>>

    @Query("DELETE FROM candidates")
    fun deleteAll()

    @Transaction
    fun deleteAllAndInsertAll(candidates: List<CandidateEntity>) {
        deleteAll()
        return insertAll(candidates)
    }

}