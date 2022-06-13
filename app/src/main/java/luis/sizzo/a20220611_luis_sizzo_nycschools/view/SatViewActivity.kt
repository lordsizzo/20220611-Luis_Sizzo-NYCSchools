package luis.sizzo.a20220611_luis_sizzo_nycschools.view

import android.R
import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.*
import androidx.lifecycle.ViewModelProvider
import dagger.hilt.android.AndroidEntryPoint
import luis.sizzo.a20220611_luis_sizzo_nycschools.databinding.ActivitySatViewBinding
import luis.sizzo.a20220611_luis_sizzo_nycschools.model.UI_State
import luis.sizzo.a20220611_luis_sizzo_nycschools.model.local.sat_entity.SatEntity
import luis.sizzo.a20220611_luis_sizzo_nycschools.view_model.MainActivityViewModel

@AndroidEntryPoint
class SatViewActivity : AppCompatActivity() {

    var dbn: String = ""
    lateinit var bindingSat: ActivitySatViewBinding
    private val viewModel: MainActivityViewModel by lazy {
        ViewModelProvider(this).get(MainActivityViewModel::class.java)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingSat = ActivitySatViewBinding.inflate(layoutInflater)
        setContentView(bindingSat.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true);
        initViews()
        initObserver()
    }

    private fun initViews() {
        intent.getStringExtra("dbn").let {
            if (it != null) {
                dbn = it
            }
        }
        bindingSat.swipeRefresh.setOnRefreshListener {
            viewModel.getSat(dbn)
        }

        intent.getStringExtra("school_name").let {
            bindingSat.schoolName.text = it
        }
        intent.getStringExtra("description").let {
            bindingSat.schoolDescription.text = it
        }
    }

    @SuppressLint("SetTextI18n")
    private fun initObserver() {
        viewModel.getSatResponse.observe(this) {
            it.let { result ->
                try {
                    when (result) {
                        is UI_State.LOADING -> {
                            bindingSat.shimmerViewContainer.startShimmerAnimation()
                        }
                        is UI_State.SUCCESS<*> -> {

                            Log.d("SatViewActivity", "${result.response}")
                            val sat = result.response as? List<SatEntity>

                            sat?.let { response ->
                                response.filter {
                                    dbn == it.dbn
                                }.forEach {
                                    bindingSat.numOfSat.text = "Num of SAT: ${it.num_of_sat_test_takers}"
                                    bindingSat.readingScore.text = "Reading Score: ${it.sat_critical_reading_avg_score}"
                                    bindingSat.mathScore.text = "Math Score: ${it.sat_math_avg_score}"
                                    bindingSat.writingScore.text = "Writing Score: ${it.sat_writing_avg_score}"
                                }
                                bindingSat.shimmerViewContainer.stopShimmerAnimation();
                                bindingSat.shimmerViewContainer.setVisibility(View.GONE);
                                bindingSat.lyContent.setVisibility(View.VISIBLE);
                            } ?: showError("Error at casting")
                        }
                        is UI_State.ERROR -> {
                            result.error.localizedMessage?.let { error -> showError(error) }
                        }
                    }
                } catch (e: Exception) {
                    showError(e.toString())
                }
                bindingSat.swipeRefresh.isRefreshing = false
            }
        }
        viewModel.getSat(dbn)
    }

    private fun showError(message: String) {
        AlertDialog.Builder(this)
            .setTitle("Error occurred")
            .setMessage(message)
            .setNegativeButton("CLOSE") { dialog, _ ->
                dialog.dismiss()
            }
            .create()
            .show()
    }

    override fun onResume() {
        super.onResume()
        bindingSat.shimmerViewContainer.startShimmerAnimation()
    }

    override fun onPause() {
        bindingSat.shimmerViewContainer.stopShimmerAnimation()
        super.onPause()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // handle arrow click here
        if (item.getItemId() === R.id.home) {
            finish() // close this activity and return to preview activity (if there is any)
        }
        return super.onOptionsItemSelected(item)
    }

}