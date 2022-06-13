package luis.sizzo.a20220611_luis_sizzo_nycschools.view.adapters

import android.annotation.SuppressLint
import android.content.Intent
import android.view.*
import android.view.animation.AnimationUtils
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.RecyclerView
import luis.sizzo.a20220611_luis_sizzo_nycschools.R
import luis.sizzo.a20220611_luis_sizzo_nycschools.common.*
import luis.sizzo.a20220611_luis_sizzo_nycschools.common.Utilities.Companion.getRandomColor
import luis.sizzo.a20220611_luis_sizzo_nycschools.databinding.ItemsSchoolBinding
import luis.sizzo.a20220611_luis_sizzo_nycschools.model.local.schools_entity.SchoolEntity
import luis.sizzo.a20220611_luis_sizzo_nycschools.view.SatViewActivity

class SchoolsAdapter(private val items: List<SchoolEntity>) :
    RecyclerView.Adapter<SchoolsAdapter.CategoriesViewHolder>() {

    class CategoriesViewHolder(val binding: ItemsSchoolBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesViewHolder {
        return CategoriesViewHolder(
            ItemsSchoolBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: CategoriesViewHolder, position: Int) {
        try {
            holder.itemView.animation =
                AnimationUtils.loadAnimation(holder.itemView.context, R.anim.rotate)
                 holder.binding.nameSchool.text = items[position].school_name
            holder.binding.addressSchool.text = "${items[position].primary_address_line_1}, ${items[position].city}, ${items[position].state_code}"
            holder.binding.addressSchool.setBackgroundColor(getRandomColor(holder.binding.root.context));
            holder.binding.root.click{
                val intent = Intent(holder.binding.root.context, SatViewActivity::class.java)
                val bundle = bundleOf("dbn" to items[position].dbn,
                    "school_name" to items[position].school_name,
                    "description" to items[position].overview_paragraph)
                intent.putExtras(bundle)
                holder.binding.root.context.startActivity(intent)
            }
        } catch (e: Exception) {

            holder.binding.root.context.toast(e.toString())
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}