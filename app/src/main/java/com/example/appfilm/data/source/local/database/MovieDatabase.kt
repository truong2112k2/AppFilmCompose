package com.example.appfilm.data.source.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.appfilm.data.source.local.model.MovieDb

@Database(entities = [MovieDb::class], version = 1)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}
