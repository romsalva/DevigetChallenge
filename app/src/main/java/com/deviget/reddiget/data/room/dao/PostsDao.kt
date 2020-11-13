package com.deviget.reddiget.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.deviget.reddiget.data.datamodel.Post
import kotlinx.coroutines.flow.Flow

@Dao
interface PostsDao {

    @Query("SELECT * FROM post")
    fun allPosts(): Flow<List<Post>>

    @Query("SELECT * FROM post WHERE id = :id")
    fun postById(id: String): Flow<Post>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(posts: List<Post>)

}