package models

import com.google.gson.annotations.SerializedName


//data class Country(
//    @SerializedName("name") val countryName:String
//)


data class Country(
    @SerializedName("name") val countryName: Name,  // Change to a nested class for 'name'
)

data class Name(
    val common: String  // Add a property for the full name
)