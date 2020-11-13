package com.deviget.reddiget.data.room.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.deviget.reddiget.Configuration
import com.deviget.reddiget.data.datamodel.Post
import com.deviget.reddiget.data.room.converter.Converters
import com.deviget.reddiget.data.room.dao.PostsDao

@Database(entities = [Post::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class PostsRoomDatabase : RoomDatabase() {

    abstract fun postsDao(): PostsDao

    companion object {
        @Volatile
        private var INSTANCE: PostsRoomDatabase? = null

        fun getDatabase(context: Context): PostsRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PostsRoomDatabase::class.java,
                    Configuration.databaseName
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }

}