package luis.sizzo.a20220611_luis_sizzo_nycschools.model.local.sat_entity

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "sat", primaryKeys = ["dbn"])
data class SatEntity(
    @ColumnInfo(name = "dbn") var dbn: String,
    @ColumnInfo(name = "num_of_sat_test_takers") var num_of_sat_test_takers: String?,
    @ColumnInfo(name = "sat_critical_reading_avg_score") var sat_critical_reading_avg_score: String?,
    @ColumnInfo(name = "sat_math_avg_score") var sat_math_avg_score: String?,
    @ColumnInfo(name = "sat_writing_avg_score") var sat_writing_avg_score: String?,
)