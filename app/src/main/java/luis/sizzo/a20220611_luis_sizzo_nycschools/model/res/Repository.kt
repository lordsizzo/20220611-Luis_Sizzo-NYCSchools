package luis.sizzo.a20220611_luis_sizzo_nycschools.model.res

import android.content.ContentValues
import android.util.Log
import kotlinx.coroutines.flow.*
import luis.sizzo.a20220611_luis_sizzo_nycschools.common.CheckConnection
import luis.sizzo.a20220611_luis_sizzo_nycschools.model.UI_State
import luis.sizzo.a20220611_luis_sizzo_nycschools.model.local.School_Sat_Dao
import luis.sizzo.a20220611_luis_sizzo_nycschools.model.local.sat_entity.SatEntity
import luis.sizzo.a20220611_luis_sizzo_nycschools.model.local.schools_entity.SchoolEntity
import luis.sizzo.a20220611_luis_sizzo_nycschools.model.remote.RemoteAPIConnection
import javax.inject.Inject

interface Repository {
    fun getSchools(): Flow<UI_State>
    fun getSat(dbn: String): Flow<UI_State>
}

class RepositoryImpl @Inject constructor(
    private val service: RemoteAPIConnection,
    private val schoolDao: School_Sat_Dao,
) : Repository {

    override fun getSchools() = flow {
        emit(UI_State.LOADING)
        try {
            if (CheckConnection().isConnected()) {
                if(service.getSat().isSuccessful){
                    val response = service.getSat()
                    response.body()?.let { result ->
                        schoolDao.deleteSat()
                        result.forEach {
                            val sat = SatEntity(
                                it.dbn,
                                it.num_of_sat_test_takers,
                                it.sat_critical_reading_avg_score,
                                it.sat_math_avg_score,
                                it.sat_writing_avg_score
                            )
                            schoolDao.insertAllSat(sat)
                        }
                    }
                }
                if (service.getSchools().isSuccessful) {
                    val response = service.getSchools()
                    response.body()?.let { result ->
                        schoolDao.delete()
                        result.forEach {
                            val school = SchoolEntity(
                                it.dbn,
                                it.school_name,
                                it.overview_paragraph,
                                it.primary_address_line_1,
                                it.city,
                                it.zip,
                                it.state_code
                            )
                            schoolDao.insertAll(school)
                        }
                        val cache = schoolDao.getAll()
                        emit(UI_State.SUCCESS(cache))
                    } ?: throw Exception("Response null")
                } else {
                    throw Exception("Network call failed")
                }
            } else {
                val cache = schoolDao.getAll()
                if (!cache.isEmpty()) {
                    emit(UI_State.SUCCESS(cache))
                } else {
                    throw Exception("Cache failed")
                }
            }
        } catch (e: Exception) {
            Log.d(ContentValues.TAG, "getSchools: ${e.message}")
            emit(UI_State.ERROR(e))
        }
    }

    override fun getSat(dbn: String) = flow {
        emit(UI_State.LOADING)
        try {
            val cache = schoolDao.findByDbn(dbn)
            Log.d("RepositoryImpl_getSat", cache.toString())
            if (!cache.isEmpty()){
                emit(UI_State.SUCCESS(cache))
            }
        } catch (e: Exception) {
            emit(UI_State.ERROR(e))
        }
    }
}