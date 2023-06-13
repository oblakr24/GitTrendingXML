package com.rokoblak.trendinggit.data.service.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.rokoblak.trendinggit.data.service.db.model.GitRepoEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface ReposDao {

    @Query("SELECT * FROM gitrepoentity ORDER BY orderIdx")
    suspend fun getAll(): List<GitRepoEntity>

    @Query("SELECT * FROM gitrepoentity ORDER BY orderIdx")
    fun getAllFlow(): Flow<List<GitRepoEntity>>

    @Insert
    suspend fun insertAll(repos: List<GitRepoEntity>)

    @Query("DELETE FROM gitrepoentity")
    suspend fun deleteAll()
}
