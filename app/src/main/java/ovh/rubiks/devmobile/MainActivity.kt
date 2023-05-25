package ovh.rubiks.devmobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import ovh.rubiks.devmobile.ui.screen.Home
import ovh.rubiks.devmobile.ui.screen.SearchScreen
import ovh.rubiks.devmobile.ui.screen.Signin
import ovh.rubiks.devmobile.ui.screen.Signup
import ovh.rubiks.devmobile.ui.theme.DevMobileTheme

class MainActivity() : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)




        setContent {
            LaunchedEffect(key1 = Unit, block = {
                val firebaseAuth = Firebase.auth

                firebaseAuth.createUserWithEmailAndPassword("test@efficom.fr", "test12345").addOnCompleteListener {
                    if (it.isSuccessful) {
                        // Log in success


                    } else if (it.isCanceled) {
                        // Canceled

                    }
                }
            })

            DevMobileTheme {
                val navController = rememberNavController()
                NavHost(navController, startDestination = "search") {
                    composable("home") { Home(navController = navController) }
                    composable("signin") { Signin(navController = navController) }
                    composable("signup") { Signup(navController = navController) }
                    composable("search") { SearchScreen()}
                }
            }
        }
    }
}


@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!", modifier = modifier, textAlign = TextAlign.Center
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    DevMobileTheme {
        Greeting("Android")
    }
}