package com.rokoblak.trendinggit.data.service.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.rokoblak.trendinggit.data.service.db.model.GitRepoEntity


const val REPOS_DB_VERSION = 1

@Database(entities = [GitRepoEntity::class], version = REPOS_DB_VERSION)
abstract class ReposDatabase : RoomDatabase() {

    abstract fun reposDao(): ReposDao

    companion object {
        const val NAME = "DB_REPOS"
    }
}
