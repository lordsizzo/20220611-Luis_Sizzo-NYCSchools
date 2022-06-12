package luis.sizzo.a20220611_luis_sizzo_nycschools.di

import dagger.*
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.*
import luis.sizzo.a20220611_luis_sizzo_nycschools.common.BASE_URL
import luis.sizzo.a20220611_luis_sizzo_nycschools.model.local.School_Sat_Dao
import luis.sizzo.a20220611_luis_sizzo_nycschools.model.remote.RemoteAPIConnection
import luis.sizzo.a20220611_luis_sizzo_nycschools.model.res.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
class ServiceModule {

    @Provides
    fun provideApiService(): RemoteAPIConnection =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RemoteAPIConnection::class.java)

    @Provides
    fun provideRepositoryLayer(service: RemoteAPIConnection, dao: School_Sat_Dao): Repository =
        RepositoryImpl(service, dao)

    @Provides
    fun provideCoroutineDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Provides
    fun provideCoroutineScope(dispatcher: CoroutineDispatcher): CoroutineScope =
        CoroutineScope(dispatcher)


}