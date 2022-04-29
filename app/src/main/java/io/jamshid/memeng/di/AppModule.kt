package io.jamshid.memeng.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.jamshid.memeng.data.local.AppDatabase
import io.jamshid.memeng.data.local.dao.EnglishDao
import io.jamshid.memeng.utils.core.CustomDialog
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(app: Application): AppDatabase {
        return Room.databaseBuilder(
            app,
            AppDatabase::class.java,
            "words"
        ).build()
    }

    @Provides
    @Singleton
    fun provideDao(
        appDatabase: AppDatabase
    ): EnglishDao {
        return appDatabase.englishDao()
    }

}