package mvc

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
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.transition.Visibility
import com.example.androidarchitctures.R

class MVCActivity : AppCompatActivity() {

    private val countries:MutableList<String> = mutableListOf(
        "United States", "Canada", "United Kingdom", "Australia", "Germany",
        "France", "Japan", "India", "Brazil", "South Korea"
    )

    private lateinit var adapter:ArrayAdapter<String>
    private lateinit var controller:ContriesController
    private lateinit var retrybutton: Button
    private lateinit var progressBar: ProgressBar
    private lateinit var listView: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_mvcactivity)
        setTitle("MVC Activity screen")
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        retrybutton = findViewById(R.id.retrybutton)
        progressBar = findViewById(R.id.progressBar)
        controller = ContriesController(this)

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

    fun setValues(vals:List<String>){
        countries.clear()
        countries.addAll(vals)
        adapter.notifyDataSetChanged()
        retrybutton.visibility = View.GONE
        progressBar.visibility = View.GONE
        listView.visibility = View.VISIBLE
    }

    fun onRetry(view:View){
        controller.onRetry()
        retrybutton.visibility = View.GONE
        progressBar.visibility = View.VISIBLE
        listView.visibility = View.GONE
    }

    fun onError(message: String){
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
        retrybutton.visibility = View.VISIBLE
        progressBar.visibility = View.GONE
        listView.visibility = View.GONE
    }

    companion object{
    fun getIntent(context: Context):Intent{
        return Intent(context,MVCActivity::class.java)
        }

        fun showToast(context: android.content.Context, message: String) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }


    public fun onTest(view: View){
        ContextCompat.startActivity(this,MVCActivity.getIntent(this),null)
    }
}