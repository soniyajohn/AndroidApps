package mvvm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import models.CountriesService
import retrofit2.HttpException
import okio.IOException


class CountriesViewModel:ViewModel() {


    // Job for coroutine
    private val job = Job()

    // Define co routine scope
    private val corotinescpe = CoroutineScope(Dispatchers.Main + job)

    // Livedata to hold list of countries
    private val _countries = MutableLiveData<List<String>>()
    val countriesObservable: LiveData<List<String>> get() = _countries

    // Livedata to hold error
    private val _error = MutableLiveData<String>()
    val errorObservable:LiveData<String> get() = _error

    private fun fetchCountries(){
        // launch coroutine in main thread
        corotinescpe.launch {
            try {

                // Call the suspend function in a background thread (IO Dispatcher)
                val countries = withContext(Dispatchers.IO){
                    CountriesService.service.getCountries()
                }

                // Convert json response to list of string
                val countryNames = mutableListOf<String>()
                for (country in countries) {
                    countryNames.add(country.countryName.common)  // Add the country name to the list
                }

                // Update UI on main thread after the API response
                _countries.postValue(countryNames)
            }
            catch (e: HttpException) {
                // Handle HTTP errors (e.g., 4xx or 5xx responses)
                _error.postValue("HTTP error: ${e.message()}")
            } catch (e: IOException) {
                // Handle network issues (e.g., no internet)
                _error.postValue("Network error: ${e.message}")
            }
            catch (e:Exception){
                // Handle error (e.g., network issues)
                _error.postValue(e.message ?: "An error occurred")
            }
        }
    }

    fun onRetry() {
        fetchCountries()
    }

}