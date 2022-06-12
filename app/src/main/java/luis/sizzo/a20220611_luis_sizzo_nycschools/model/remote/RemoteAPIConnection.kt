package luis.sizzo.a20220611_luis_sizzo_nycschools.model.remote

import luis.sizzo.a20220611_luis_sizzo_nycschools.common.*
import luis.sizzo.a20220611_luis_sizzo_nycschools.model.local.sat_entity.SatEntity
import luis.sizzo.a20220611_luis_sizzo_nycschools.model.local.schools_entity.SchoolEntity
import retrofit2.*
import retrofit2.http.*

interface RemoteAPIConnection{

    @GET(END_POINT_SCHOOLS)
    suspend fun getSchools(
    ): Response<List<SchoolEntity>>

    @GET(END_POINT_SAT)
    suspend fun getSat(
    ): Response<List<SatEntity>>

}