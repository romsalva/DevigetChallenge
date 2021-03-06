package com.deviget.reddiget.data.room.dao

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.deviget.reddiget.data.datamodel.Post
import kotlinx.coroutines.flow.Flow

@Dao
interface PostsDao {

    @Query("SELECT * FROM post WHERE hidden = 0")
    fun allShownPosts(): DataSource.Factory<Int, Post>

    @Query("SELECT * FROM post WHERE id = :id")
    fun postById(id: String): Flow<Post>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(posts: List<Post>)

    @Query("UPDATE post SET read = :read WHERE id = :id")
    suspend fun setRead(id: String, read: Boolean)

    @Query("UPDATE post SET hidden = :hidden WHERE id = :id")
    suspend fun setHidden(id: String, hidden: Boolean)

    @Query("UPDATE post SET hidden = 1 WHERE read = 1")
    suspend fun hideRead()

    @Query("DELETE FROM post")
    suspend fun deleteAllPosts()

}