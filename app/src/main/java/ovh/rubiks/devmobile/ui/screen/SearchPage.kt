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
fun SearchScreen() {
    var searchText by remember { mutableStateOf("") }

    val cardTitles = listOf(
        "Rock",
        "Jazz",
        "Hip Hop",
        "Pop",
        "R&B",
        "Podcasts"
    )

    val searchImageUrls = listOf(
        // Icon Rock
        "https://t.scdn.co/media/derived/rock_9ce79e0a4ef901bbd10494f5b855d3cc_0_0_274_274.jpg",
        // Icon Jazz
        "https://t.scdn.co/images/568f37f1cab54136939d63bd1f59d40c",
        // Icon Hip Hop
        "https://i.scdn.co/image/ab67706f00000002e48ac7e971606c850f5291ea",
        // Icon Pop
        "https://t.scdn.co/media/derived/pop-274x274_447148649685019f5e2a03a39e78ba52_0_0_274_274.jpg",
        // Icon R&B
        "https://t.scdn.co/media/derived/r-b-274x274_fd56efa72f4f63764b011b68121581d8_0_0_274_274.jpg",
        // Icon Podcasts
        "https://t.scdn.co/images/cd59d6084c4a4c5191aeaad187246f24.jpeg"
    )

    Scaffold(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize().padding(vertical = 60.dp, horizontal = 15.dp)
        ) {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.background
            ) {
                OutlinedTextField(
                    value = searchText,
                    onValueChange = { searchText = it },
                    label = { Text("Search") },
                    trailingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                    keyboardActions = KeyboardActions(onSearch = {
                        // Perform search or other actions here
                    }),
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }

            val columns = 2
            val rows = (cardTitles.size + columns - 1) / columns

            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(rows) { rowIndex ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        val startIndex = rowIndex * columns
                        val endIndex = minOf(startIndex + columns, cardTitles.size)
                        for (cardIndex in startIndex until endIndex) {
                            val imageUrl = searchImageUrls.getOrElse(cardIndex) { "" }
                            Card(
                                modifier = Modifier
                                    .padding(8.dp)
                                    .weight(1f)
                                    .clickable { /* Handle card click */ }
                            ) {
                                Column(
                                    modifier = Modifier.padding(16.dp)
                                ) {
                                    Text(
                                        text = cardTitles[cardIndex],
                                        modifier = Modifier.padding(bottom = 8.dp)
                                    )
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        AsyncImage(
                                            model = ImageRequest.Builder(LocalContext.current)
                                                .data(imageUrl)
                                                .build(),
                                            contentDescription = "Image",
                                            modifier = Modifier
                                                .size(80.dp)
                                                .aspectRatio(1f),
                                            contentScale = ContentScale.Crop
                                        )
                                        Text(
                                            text = searchText,
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
    }
}




private fun getImageUrlForQuery(query: String): String {
    // Replace this with your logic to get the image URL based on the search query
    return "https://example.com/image?query=$query"
}
