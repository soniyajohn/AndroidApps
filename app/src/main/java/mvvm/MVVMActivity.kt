package mvvm

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import com.example.androidarchitctures.R
import mvc.ContriesController
import mvp.CountriesPresenter

class MVVMActivity : AppCompatActivity() {

    private val countries:MutableList<String> = mutableListOf(
        "United States", "Canada", "United Kingdom", "Australia", "Germany",
        "France", "Japan", "India", "Brazil", "South Korea"
    )

    private lateinit var adapter: ArrayAdapter<String>

    // ViewModel property using the viewModels delegate
    private val vm: CountriesViewModel by viewModels()
    private val viewModel: CountriesViewModel by viewModels()
    private lateinit var retrybutton: Button
    private lateinit var progressBar: ProgressBar
    private lateinit var listView: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_mvvmactivity)
        setTitle("MVVM Activity screen")
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

            retrybutton = findViewById(R.id.retrybutton)
            progressBar = findViewById(R.id.progressBar)

            // Find the ListView from the layout
            listView = findViewById(R.id.countryListView)

            // Create an ArrayAdapter to bind the list of countries to the ListView
            //val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, countries)
            adapter = ArrayAdapter(this, R.layout.list_item_country, R.id.countryName, countries)

            // Set the adapter to the ListView
            listView.adapter = adapter

            // Optional: Handle item clicks

            listView.setOnItemClickListener { parent, view, position, id ->
                val country = parent.getItemAtPosition(position) as String
                // Show a toast or handle the clicked country
                println("Clicked on: $country")
        }
        observeViewModel()
    }

    private fun observeViewModel(){
        vm.countriesObservable.observe(this, Observer {
            vals ->
            countries.clear()
            countries.addAll(vals)
            adapter.notifyDataSetChanged()
            retrybutton.visibility = View.GONE
            progressBar.visibility = View.GONE
            listView.visibility = View.VISIBLE
        })

        vm.errorObservable.observe(this, Observer {
                message ->
                Toast.makeText(this, message, Toast.LENGTH_LONG).show()
                retrybutton.visibility = View.VISIBLE
                progressBar.visibility = View.GONE
                listView.visibility = View.GONE
        })
    }

    fun onRetry(view: View){
        vm.onRetry()
        retrybutton.visibility = View.GONE
        progressBar.visibility = View.VISIBLE
        listView.visibility = View.GONE
    }

    companion object{
        @JvmStatic
        fun getIntent(context: Context): Intent {
            return Intent(context, MVVMActivity::class.java)
        }
    }
}