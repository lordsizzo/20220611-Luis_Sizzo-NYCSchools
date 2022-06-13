package luis.sizzo.a20220611_luis_sizzo_nycschools.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.*
import dagger.hilt.android.AndroidEntryPoint
import luis.sizzo.a20220611_luis_sizzo_nycschools.common.*
import luis.sizzo.a20220611_luis_sizzo_nycschools.databinding.ActivityMainBinding
import luis.sizzo.a20220611_luis_sizzo_nycschools.model.UI_State
import luis.sizzo.a20220611_luis_sizzo_nycschools.model.local.schools_entity.SchoolEntity
import luis.sizzo.a20220611_luis_sizzo_nycschools.view.adapters.SchoolsAdapter
import luis.sizzo.a20220611_luis_sizzo_nycschools.view_model.MainActivityViewModel

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var howShowIt = true
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainActivityViewModel by lazy {
        ViewModelProvider(this).get(MainActivityViewModel::class.java)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
        initObserver()
    }

    private fun initViews() {
        binding.swipeRefresh.setOnRefreshListener {
            viewModel.getSchools()
        }

        binding.listView.click {
            howShowIt = false
            viewModel.getStateView(false)
        }
        binding.tableView.click {
            howShowIt = true
            viewModel.getStateView(true)
        }
    }

    private fun initObserver() {
        viewModel.stateView.observe(this) {
            Log.d("MainActivity", "Result of observe: $it")
            howShowIt = it
            if (it) {
                binding.recyclerView.layoutManager = LinearLayoutManager(this)
                binding.listView.visibility = View.VISIBLE
                binding.tableView.visibility = View.GONE

            } else {
                binding.recyclerView.layoutManager = GridLayoutManager(this, 2)
                binding.listView.visibility = View.GONE
                binding.tableView.visibility = View.VISIBLE
            }
        }
        viewModel.getStateView(howShowIt)
        viewModel.getSchoolResponse.observe(this) {
            it.let { result ->
                try {
                    when (result) {
                        is UI_State.LOADING -> {
                            binding.shimmerViewContainer.startShimmerAnimation()
                        }
                        is UI_State.SUCCESS<*> -> {

                            Log.d("MainActivitySuccess", "${result.response}")
                            val schools = result.response as? List<SchoolEntity>

                            schools?.let {

                                SchoolsAdapter(it).apply {
                                    if (howShowIt)
                                        binding.recyclerView.settingsLinearVertical(this)
                                    else
                                        binding.recyclerView.settingsGrid(this)

                                }
                                binding.shimmerViewContainer.stopShimmerAnimation();
                                binding.shimmerViewContainer.setVisibility(View.GONE);
                            } ?: showError("Error at casting")
                        }
                        is UI_State.ERROR -> {
                            result.error.localizedMessage?.let { error -> showError(error) }
                        }
                    }

                } catch (e: Exception) {
                    showError(e.toString())

                }
                binding.swipeRefresh.isRefreshing = false
            }
        }
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
        binding.shimmerViewContainer.startShimmerAnimation()
    }

    override fun onPause() {
        binding.shimmerViewContainer.stopShimmerAnimation()
        super.onPause()
    }

}