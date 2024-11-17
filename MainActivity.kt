package com.example.booknest

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.booknest.ui.BookApp
import com.example.booknest.ui.BookViewModel
import com.example.booknest.ui.DisplayScreen
import com.example.booknest.ui.LogoScreen
import com.example.booknest.ui.theme.BookNestTheme
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.DatabaseReference




// AppNavigation.kt
@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "logo_screen") {
        composable("logo_screen") { LogoScreen(navController) }
        composable("your_next_screen") { DisplayScreen(bookViewModel = BookViewModel()) }
    }
}

class MainActivity : ComponentActivity() {
    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(
            R.xml
                .activity_find_room)
        enableEdgeToEdge()
        setContent {
            AppNavigation()
            BookNestTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    BookApp(bookViewModel = BookViewModel())
                }
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    BookNestTheme {
        BookApp(bookViewModel = BookViewModel())
    }
}