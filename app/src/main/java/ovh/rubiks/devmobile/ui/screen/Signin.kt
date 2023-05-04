package ovh.rubiks.devmobile.ui.screen

import android.content.ContentValues
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
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import ovh.rubiks.devmobile.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Signin() {
    var email = remember { mutableStateOf(TextFieldValue("")) }
    var password = remember { mutableStateOf(TextFieldValue("")) }
    var message = remember { mutableStateOf(TextFieldValue("")) }

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
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 5.dp)
            ) {
                Button(
                    modifier = Modifier
                        .padding(vertical = 10.dp, horizontal = 12.dp)
                        .fillMaxWidth(),
                    onClick = {
                        val firebaseAuth = Firebase.auth

                        firebaseAuth.signInWithEmailAndPassword(
                            email.value.text,
                            password.value.text
                        ).addOnCompleteListener {
                            val uid = it.result.user?.uid
                            if (it.isSuccessful && uid != null) {
                                // Log in success
                                Log.d(ContentValues.TAG, "signInWithEmail:success")
                                message.value = TextFieldValue("You are now logged in.")
                            } else if (it.isCanceled) {
                                // Canceled
                                Log.w(ContentValues.TAG, "signInWithEmail:failure", it.exception)
                                message.value =
                                    TextFieldValue("An error occured on sign in, please try again later.")
                            }
                        }.addOnFailureListener() {
                            // Log in failed
                            Log.w(ContentValues.TAG, "createUserWithEmail:failure", it)
                            message.value =
                                TextFieldValue("An error occured on sign in, please try again later.")
                        }

                    }) {
                    Text("Sign In")
                }
            }

            Row(
                horizontalArrangement = Arrangement.SpaceAround,
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 5.dp)
            ) {
                Text(message.value.text)
            }
        }
    }
}