package luis.sizzo.a20220611_luis_sizzo_nycschools.di

import android.content.Context
import androidx.room.Room
import dagger.*
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import luis.sizzo.a20220611_luis_sizzo_nycschools.model.local.School_Sat_DB
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalDBModule {

    private const val NameDatabase = "schools_sat_database"

    @Provides
    @Singleton
    fun provideRoom(@ApplicationContext context: Context) =
        Room.databaseBuilder(
            context,
            School_Sat_DB::class.java,
            NameDatabase
        ).build()

    @Provides
    @Singleton
    fun provideSchoolsDao(db: School_Sat_DB) = db.getSchoolsDao()

}