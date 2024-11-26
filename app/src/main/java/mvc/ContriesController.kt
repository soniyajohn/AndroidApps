package mvc

import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.androidarchitctures.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import models.CountriesService
import models.Country
import okio.IOException
import retrofit2.HttpException

public class ContriesController(mvcActivity: MVCActivity) {


   // Job for coroutine
    private val job = Job()

    // Define co routine scope
    private val corotinescpe = CoroutineScope(Dispatchers.Main + job)
    private val parentActivity:MVCActivity = mvcActivity

    init {
        fetchCountries()
    }

    private fun fetchCountries(){
        // launch coroutine in main thread
        corotinescpe.launch {
            try {

                // Call the suspend function in a background thread (IO Dispatcher)
                val countries = withContext(Dispatchers.IO){
                    CountriesService.service.getCountries()
                }

                // Update UI on main thread after the API response
                updateView(countries)

            }
            catch (e: HttpException) {
                // Handle HTTP errors (e.g., 4xx or 5xx responses)
                showError("HTTP error: ${e.message()}")
            } catch (e: IOException) {
                // Handle network issues (e.g., no internet)
                showError("Network error: ${e.message}")
            }
            catch (e:Exception){
                // Handle error (e.g., network issues)
                showError(e.message ?: "An error occurred")
            }
        }
    }

    private fun updateView(countries: List<Country>) {

        val countryNames = mutableListOf<String>()

        for (country in countries) {
            countryNames.add(country.countryName.common)  // Add the country name to the list
        }
        parentActivity.setValues((countryNames))

        // Update the ListView with the fetched data
        //val adapter = ArrayAdapter(parentActivity, R.layout.list_item_country, R.id.countryName, countries)

    }

    private fun showError(message: String) {
        //Toast.makeText(parentActivity, message, Toast.LENGTH_LONG).show()
        parentActivity.onError(message)
    }

    fun onRetry() {
        fetchCountries()
    }
}