package luis.sizzo.a20220611_luis_sizzo_nycschools.common

import android.content.Context
import android.graphics.Color

class Utilities {

    companion object{
        fun getRandomColor(context: Context): Int {
            val allColors = context.resources.getStringArray(luis.sizzo.a20220611_luis_sizzo_nycschools.R.array.colors)
            val rnds = (allColors.indices).random()
            return Color.parseColor(allColors[rnds])
        }
    }
}