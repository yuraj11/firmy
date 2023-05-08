package sk.devprog.firmy.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import sk.devprog.firmy.data.database.AppDatabase
import sk.devprog.firmy.data.database.CodeListDatabase
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Singleton
    @Provides
    fun provideCodeListDatabase(application: Application): CodeListDatabase =
        Room.databaseBuilder(application, CodeListDatabase::class.java, "codelist.db")
            .createFromAsset("codelist.db") // Pre-populated database
            .fallbackToDestructiveMigration()
            .build()

    @Singleton
    @Provides
    fun provideAppDatabase(application: Application): AppDatabase =
        Room.databaseBuilder(application, AppDatabase::class.java, "app_database.db")
            .build()
}
