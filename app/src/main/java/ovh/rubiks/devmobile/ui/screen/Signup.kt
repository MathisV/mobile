package ovh.rubiks.devmobile.ui.screen

import android.content.ContentValues.TAG
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
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import ovh.rubiks.devmobile.R
import java.lang.Exception

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Signup(navController: NavController) {
    val db = Firebase.firestore

    var firstname = remember { mutableStateOf(TextFieldValue("")) }
    var lastname = remember { mutableStateOf(TextFieldValue("")) }
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
                    value = firstname.value,
                    placeholder = { Text("Firstname") },
                    onValueChange = { firstname.value = it },
                    maxLines = 1,
                    trailingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_user_24),
                            contentDescription = "User"
                        )
                    })
            }
            Row(
                horizontalArrangement = Arrangement.SpaceAround,
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 5.dp)
            ) {
                TextField(
                    value = lastname.value,
                    placeholder = { Text("Lastname") },
                    onValueChange = { lastname.value = it },
                    maxLines = 1,
                    trailingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_user_24),
                            contentDescription = "User"
                        )
                    })
            }
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
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 5.dp)
            ) {
                Button(
                    modifier = Modifier.padding(vertical = 10.dp, horizontal = 12.dp).fillMaxWidth(),
                    onClick = {
                        try {
                            signUp(
                                email.value.text,
                                password.value.text,
                                firstname.value.text,
                                lastname.value.text
                            ) { success, resultMessage ->
                                message = resultMessage
                                if (success) {
                                    // Navigate to the next screen
                                    navController.navigate("signin")
                                }
                            }
                        } catch (e: Exception) {
                            message = e.message
                        }
                }) {
                    Text("Sign up")
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

fun signUp(email: String, password: String, firstName: String, lastName: String, onResult: (Boolean, String) -> Unit) {
    val auth = FirebaseAuth.getInstance()
    val db = FirebaseFirestore.getInstance()

    auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
        if (task.isSuccessful) {
            val userId = auth.currentUser?.uid
            if (userId != null) {
                val user = hashMapOf(
                    "firstName" to firstName,
                    "lastName" to lastName
                )
                db.collection("users").document(userId)
                    .set(user)
                    .addOnSuccessListener {
                        onResult(true, "Successful Sign Up!")
                    }
                    .addOnFailureListener { e ->
                        onResult(false, e.message ?: "Error during Firestore operation")
                    }
            }
        } else {
            val message = when (val exception = task.exception) {
                is FirebaseAuthWeakPasswordException -> "Password is too weak."
                is FirebaseAuthInvalidCredentialsException -> "Email is malformed."
                is FirebaseAuthUserCollisionException -> "Account with this email already exists."
                else -> exception?.message ?: "Unknown error occurred."
            }
            onResult(false, message)
        }
    }
}