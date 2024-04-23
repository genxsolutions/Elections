package com.genxsol.elections.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.genxsol.elections.data.database.dao.CandidateDao
import com.genxsol.elections.data.database.entity.CandidateEntity

@Database(entities = [CandidateEntity::class], version = 1, exportSchema = false)
    abstract class ElectionDatabase : RoomDatabase() {
    abstract fun getCandidateDao(): CandidateDao
}