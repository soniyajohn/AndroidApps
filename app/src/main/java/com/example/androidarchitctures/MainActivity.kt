package com.example.androidarchitctures

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
//import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import com.example.androidarchitctures.ui.theme.AndroidArchitcturesTheme
import mvc.MVCActivity
import mvp.MVPActivity
import mvvm.MVVMActivity
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
    }


//class MainActivity : AppCompatActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
//        setContentView(R.layout.activity_mvcactivity)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }
//    }

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AndroidArchitcturesTheme {
        Greeting("Android")
    }
}

public fun onMvc(view: View){
    //Toast.makeText(this, "Button clicked!", Toast.LENGTH_SHORT).show()

//    val intent = MVCActivity.getIntent(this)
//    startActivity(intent)

    val intent = Intent(this, MVCActivity::class.java)
    startActivity(intent)

    //startActivity(MVCActivity.getIntent(this))
    //MVCActivity.showToast(this,"another class toast")
    //ContextCompat.startActivity(this,MVCActivity.getIntent(this),null)
}

fun onMvp(view: View){
    ContextCompat.startActivity(this, MVPActivity.getIntent(this),null)
}

fun onMvvm(view: View){
    ContextCompat.startActivity(this, MVVMActivity.getIntent(this),null)
}

fun onButtonClick(view: View) {
    Toast.makeText(this, "Button clicked!", Toast.LENGTH_SHORT).show()
}
}

