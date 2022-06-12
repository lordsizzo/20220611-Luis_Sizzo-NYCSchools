package luis.sizzo.a20220611_luis_sizzo_nycschools.model.local

import androidx.room.Database
import androidx.room.RoomDatabase
import luis.sizzo.a20220611_luis_sizzo_nycschools.model.local.sat_entity.SatEntity
import luis.sizzo.a20220611_luis_sizzo_nycschools.model.local.schools_entity.SchoolEntity

@Database(
    entities = [
        SchoolEntity::class,
        SatEntity::class
    ],
    version = 1
)
abstract class School_Sat_DB : RoomDatabase() {

    abstract fun getSchoolsDao(): School_Sat_Dao
}