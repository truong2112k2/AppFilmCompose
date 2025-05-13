package com.example.appfilm.data.source.local.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.appfilm.data.source.local.model.MovieDb
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(movies: List<MovieDb>): LongArray

    @Query("SELECT * FROM movies")
    fun getAllMovies(): Flow<List<MovieDb>> // Trả về Flow
}
