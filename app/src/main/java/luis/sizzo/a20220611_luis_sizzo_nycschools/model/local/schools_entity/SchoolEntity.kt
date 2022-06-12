package luis.sizzo.a20220611_luis_sizzo_nycschools.model.local.schools_entity

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "school_table", primaryKeys = ["dbn"])
data class SchoolEntity(
    @ColumnInfo(name = "dbn") val dbn: String,
    @ColumnInfo(name = "school_name") val school_name: String?,
    @ColumnInfo(name = "overview_paragraph") val overview_paragraph: String?,
    @ColumnInfo(name = "primary_address_line_1") val primary_address_line_1: String?,
    @ColumnInfo(name = "city") val city: String?,
    @ColumnInfo(name = "zip") val zip: String?,
    @ColumnInfo(name = "state_code") val state_code: String?
)
