package luis.sizzo.a20220611_luis_sizzo_nycschools.model

sealed class UI_State {
    object LOADING : UI_State()
    class SUCCESS<T>(val response : T) : UI_State()
    class ERROR(val error: Exception) : UI_State()
}