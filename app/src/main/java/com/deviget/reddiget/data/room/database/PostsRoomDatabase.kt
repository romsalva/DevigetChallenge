package com.deviget.reddiget.data.room.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.deviget.reddiget.data.datamodel.Post
import com.deviget.reddiget.data.room.converter.Converters
import com.deviget.reddiget.data.room.dao.PostsDao

@Database(entities = [Post::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class PostsRoomDatabase : RoomDatabase() {

    abstract fun postsDao(): PostsDao

}
