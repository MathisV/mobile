package ovh.rubiks.devmobile.ui.screen

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import ovh.rubiks.devmobile.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Signin(navController: NavController) {
    var email = remember { mutableStateOf(TextFieldValue("")) }
    var password = remember { mutableStateOf(TextFieldValue("")) }
    var message by remember { mutableStateOf<String?>(null) }

    Scaffold { it ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 30.dp, horizontal = 35.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceAround,
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 5.dp)
            ) {
                TextField(
                    value = email.value,
                    placeholder = { Text("Email") },
                    onValueChange = { email.value = it },
                    maxLines = 1,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    trailingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_alternate_email_24),
                            contentDescription = "User"
                        )
                    })
            }
            Row(
                horizontalArrangement = Arrangement.SpaceAround,
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 5.dp)
            ) {
                TextField(
                    value = password.value,
                    placeholder = { Text("Password") },
                    onValueChange = { password.value = it },
                    maxLines = 1,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    trailingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_password_24),
                            contentDescription = "Password"
                        )
                    },
                    readOnly = false,
                    visualTransformation = PasswordVisualTransformation()
                )
            }
            Row(
                horizontalArrangement = Arrangement.SpaceAround,
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 2.dp)
            ) {
                Button(
                    modifier = Modifier
                        .padding(vertical = 0.dp, horizontal = 0.dp)
                        .fillMaxWidth(),
                    onClick = {
                        try {
                            signIn(
                                email.value.text,
                                password.value.text
                            ) { success, resultMessage ->
                                message = resultMessage
                                if (success) {
                                    // Navigate to the next screen
                                    navController.navigate("home")
                                }
                            }
                        } catch (e: Exception) {
                            message = e.message
                        }
                    }) {
                    Text("Sign In")
                }
            }
            Row(
                horizontalArrangement = Arrangement.SpaceAround,
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 2.dp)
            ) {
                Button(
                    modifier = Modifier
                        .padding(vertical = 0.dp, horizontal = 0.dp)
                        .fillMaxWidth(),
                    onClick = { navController.navigate("signup") }) {
                    Text("Sign Up")
                }
            }
            if (message != null) {
                Snackbar(
                    action = {
                        TextButton(onClick = { message = null }) {
                            Text("Fermer")
                        }
                    },
                    modifier = Modifier.padding(8.dp)
                ) {
                    Text(message!!)
                }
            }
        }
    }
}

fun signIn(email: String, password: String, onResult: (Boolean, String) -> Unit) {
    FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
        val message = if (task.isSuccessful) {
            Log.d("Signin", "Successful Sign In!")
            "Successful Sign In!"
        } else {
            Log.d("Signin", "Sign In Failed: ${task.exception?.message}")
            when (val exception = task.exception) {
                is FirebaseAuthInvalidCredentialsException -> "Password is incorrect or email is invalid."
                is FirebaseAuthInvalidUserException -> "Email doesn't exist or has been disabled."
                else -> exception?.message ?: "Unknown error occurred."
            }
        }
        onResult(task.isSuccessful, message)
    }
}