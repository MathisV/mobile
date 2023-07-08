package ovh.rubiks.devmobile.ui.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LibraryScreen() {
    var libraryText by remember { mutableStateOf("") }

    val cardTitles = listOf(
        "Playlist Jazz",
        "Playlist Sport",
        "All Eyez On Me - 2Pac",
        "Playlist chill",
        "Random Access Memories - Daft Punk",
        "Podcasts de la semaine"
    )

    val imageUrls = listOf(
        // Icon Jazz
        "https://t.scdn.co/images/568f37f1cab54136939d63bd1f59d40c",
        // Icon Sport
        "https://i.scdn.co/image/ab67706c0000da84caabb495311669715b39e7eb",
        // Icon All Eyez On Me - 2Pac
        "https://i.scdn.co/image/ab67616d00001e02073aebff28f79959d2543596",
        // Icon Playlist chill
        "https://i.scdn.co/image/ab67706c0000da84672ab214077d7446c6dcd0ed",
        // Icon Random Access Memories - Daft Punk
        "https://i.scdn.co/image/ab67616d00001e029b9b36b0e22870b9f542d937",
        // Icon Podcasts de la semaine
        "https://i.scdn.co/image/ab67706f00000002c792d9c2e9d7ce6676de78d4"
    )

    Scaffold(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize().padding(vertical = 60.dp, horizontal = 55.dp)
        ) {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.background
            ) {
                OutlinedTextField(
                    value = libraryText,
                    onValueChange = { libraryText = it },
                    label = { Text("Library") },
                    trailingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                    keyboardActions = KeyboardActions(onSearch = {
                        // Perform search or other actions here
                    }),
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }

            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(cardTitles.size) { index ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                            .heightIn(max = 120.dp)
                            .clickable { /* Handle card click */ }
                    ) {
                        Row(
                            modifier = Modifier.fillMaxSize(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            val imageUrl = imageUrls.getOrElse(index) { "" }
                            AsyncImage(
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(imageUrl)
                                    .build(),
                                contentDescription = "Image",
                                modifier = Modifier
                                    .size(120.dp)
                                    .aspectRatio(1f)
                                    .padding(end = 16.dp),
                                contentScale = ContentScale.Crop
                            )
                            Column(
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(
                                    text = cardTitles[index],
                                    modifier = Modifier.padding(bottom = 8.dp)
                                )
                                Text(
                                    text = libraryText,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}









private fun getImageUrlForQuery(query: String): String {
    // Replace this with your logic to get the image URL based on the search query
    return "https://example.com/image?query=$query"
}
