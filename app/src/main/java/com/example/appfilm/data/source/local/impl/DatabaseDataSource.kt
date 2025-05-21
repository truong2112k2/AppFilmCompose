package com.example.appfilm.data.source.local.impl

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import com.example.appfilm.common.Resource
import com.example.appfilm.data.source.local.IDatabaseDataSource
import com.example.appfilm.data.source.local.database.MovieDao
import com.example.appfilm.data.source.local.model.MovieDb
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton

class DatabaseDataSource @Inject constructor(
    private val movieDao : MovieDao
): IDatabaseDataSource{

    override suspend fun insertAll(movies: List<MovieDb>): Resource<Boolean> {
       return try{
            val result = movieDao.insertAll(movies)

            if(result.isNotEmpty()){
                Resource.Success(true )
            }else{
                Resource.Success(false )

            }
        }catch (e: Exception){
           Resource.Error(e.message.toString())

        }
    }

    override fun getAllMovies(): Flow<List<MovieDb>> {
        return movieDao.getAllMovies()
    }

    override suspend fun cacheMovies(  context: Context, movies: List<MovieDb>): List<MovieDb> {
        return movies.map { movie ->
            val localPath = movie.poster_url?.let {
                saveImageToInternalStorage(context, it, movie._id ?: movie.name ?: UUID.randomUUID().toString())
            }
            Log.d("Check", localPath.toString())

            movie.copy(localPosterPath = localPath)
        }
    }

    override suspend fun saveImageToInternalStorage(context: Context, imageUrl: String, fileName: String): String? {
        return try {
            val url = URL(imageUrl)
            val connection: HttpURLConnection =
                withContext(Dispatchers.IO) {
                    url.openConnection()
                } as HttpURLConnection
            connection.doInput = true
            withContext(Dispatchers.IO) {
                connection.connect()
            }
            val input: InputStream = connection.inputStream
            val bitmap = BitmapFactory.decodeStream(input)

            val file = File(context.filesDir, "$fileName.jpg")
            val outputStream = withContext(Dispatchers.IO) {
                FileOutputStream(file)
            }
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
            withContext(Dispatchers.IO) {
                outputStream.flush()
            }
            withContext(Dispatchers.IO) {
                outputStream.close()
            }

            file.absolutePath
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}