package luis.sizzo.a20220611_luis_sizzo_nycschools.common

import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.*
import com.google.android.material.snackbar.Snackbar
import luis.sizzo.a20220611_luis_sizzo_nycschools.view.adapters.SchoolsAdapter

fun RecyclerView.settingsGrid(adapter: SchoolsAdapter){
    this.layoutManager = GridLayoutManager(this.context, 2)
    this.adapter = adapter
}

fun RecyclerView.settingsLinearVertical(adapter: SchoolsAdapter){
    this.layoutManager = LinearLayoutManager(this.context)
    this.adapter = adapter
}

fun Context.toast(message: String, lenght: Int = Toast.LENGTH_SHORT){
    Toast.makeText(this, message, lenght).show()
}

fun View.snack(message: String, lenght: Int = Snackbar.LENGTH_SHORT){
    Snackbar.make(this, message, lenght).show()
}

fun View.click(listener: (View) -> Unit){
    this.setOnClickListener{
        listener(it)
    }
}
