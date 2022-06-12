package luis.sizzo.a20220611_luis_sizzo_nycschools.model.local

import androidx.room.*
import luis.sizzo.a20220611_luis_sizzo_nycschools.model.local.sat_entity.SatEntity
import luis.sizzo.a20220611_luis_sizzo_nycschools.model.local.schools_entity.SchoolEntity

@Dao
interface School_Sat_Dao {
    @Query("SELECT * FROM school_table")
    suspend fun getAll(): List<SchoolEntity>

    @Insert()
    suspend fun insertAll(schools: SchoolEntity)

    @Query("DELETE FROM school_table")
    suspend fun delete()

    @Query("SELECT * FROM sat")
    suspend fun getAllSat(): List<SchoolEntity>

    @Query("SELECT * FROM sat WHERE dbn LIKE :dbn")
    suspend fun findByDbn(dbn: String): List<SatEntity>

    @Insert()
    suspend fun insertAllSat(sat: SatEntity)

    @Query("DELETE FROM sat")
    suspend fun deleteSat()


}