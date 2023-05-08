package sk.devprog.firmy.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import sk.devprog.firmy.data.serialization.LocalDateSerializer
import java.time.LocalDate

@InstallIn(SingletonComponent::class)
@Module
object SerializationModule {

    @Provides
    fun provideJson(): Json =
        Json {
            ignoreUnknownKeys = true
            serializersModule = SerializersModule {
                contextual(LocalDate::class, LocalDateSerializer)
            }
        }
}
