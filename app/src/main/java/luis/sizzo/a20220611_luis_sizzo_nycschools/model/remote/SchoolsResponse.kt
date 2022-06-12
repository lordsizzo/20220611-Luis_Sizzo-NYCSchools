package luis.sizzo.a20220611_luis_sizzo_nycschools.model.remote


data class SchoolsResponse(
    val dbn: String,
    val school_name: String,
    val overview_paragraph: String,
    val primary_address_line_1: String,
    val city: String,
    val zip: String,
    val state_code: String,
)