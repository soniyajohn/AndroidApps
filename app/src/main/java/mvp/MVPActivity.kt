package mvp

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
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.androidarchitctures.R
import mvc.ContriesController

class MVPActivity : AppCompatActivity(),  ViewInterface{

    private val countries:MutableList<String> = mutableListOf(
        "United States", "Canada", "United Kingdom", "Australia", "Germany",
        "France", "Japan", "India", "Brazil", "South Korea"
    )

    private lateinit var adapter: ArrayAdapter<String>
    private lateinit var presenter: CountriesPresenter
    private lateinit var retrybutton: Button
    private lateinit var progressBar: ProgressBar
    private lateinit var listView: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_mvpactivity)
        setTitle("MVP Activity screen")
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        retrybutton = findViewById(R.id.retrybutton)
        progressBar = findViewById(R.id.progressBar)
        presenter = CountriesPresenter(this)

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
    }

    fun onRetry(view: View){
        presenter.onRetry()
        retrybutton.visibility = View.GONE
        progressBar.visibility = View.VISIBLE
        listView.visibility = View.GONE
    }


    companion object{
        @JvmStatic
        fun getIntent(context: Context): Intent {
            return Intent(context, MVPActivity::class.java)
        }
    }

    override fun setValues(vals: List<String>) {
        countries.clear()
        countries.addAll(vals)
        adapter.notifyDataSetChanged()
        retrybutton.visibility = View.GONE
        progressBar.visibility = View.GONE
        listView.visibility = View.VISIBLE
    }

    override fun onError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
        retrybutton.visibility = View.VISIBLE
        progressBar.visibility = View.GONE
        listView.visibility = View.GONE
    }
}