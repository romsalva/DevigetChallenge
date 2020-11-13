package com.deviget.reddiget.di

import android.content.Context
import androidx.room.Room
import com.deviget.reddiget.Configuration
import com.deviget.reddiget.data.room.database.PostsRoomDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun providePostsRoomDatabase(@ApplicationContext applicationContext: Context) =
        Room.databaseBuilder(
            applicationContext,
            PostsRoomDatabase::class.java,
            Configuration.databaseName
        ).build()


    @Provides
    fun providePostsDao(database: PostsRoomDatabase) = database.postsDao()
}