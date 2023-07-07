package ovh.rubiks.devmobile.ui.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import ovh.rubiks.devmobile.R

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(navController: NavController) {
    val navControllerHome = rememberNavController()
    Scaffold(topBar = {
        TopAppBar(title = { Text(text = "DevMobile") }, actions = {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_av_timer_24),
                    contentDescription = "History"
                )
            }
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_settings_24),
                    contentDescription = "Settings"
                )
            }
        })
    }, bottomBar = {
        NavigationBar() {
            val navBackStackEntry by navControllerHome.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination
            NavigationBarItem(selected = currentDestination?.hierarchy?.any { it.route == "home" } == true, onClick = { navControllerHome.navigate("home");}, icon = {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_home_24),
                    contentDescription = "Home"
                )
            }, label = { Text(text = "Home") })
            NavigationBarItem(selected = currentDestination?.hierarchy?.any { it.route == "search" } == true, onClick = { navControllerHome.navigate("search") }, icon = {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_manage_search_24),
                    contentDescription = "Search"
                )
            }, label = { Text(text = "Search") })
            NavigationBarItem(selected = currentDestination?.hierarchy?.any { it.route == "library" } == true, onClick = { /*TODO*/ }, icon = {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_library_music_24),
                    contentDescription = "Library"
                )
            }, label = { Text(text = "Library") })
        }
    }) {

        NavHost(navControllerHome, startDestination = "library", modifier = Modifier.fillMaxSize()) {
            composable("home") { HomePage(navController = navController) }
            composable("search") { SearchScreen() }
            composable("library") { LibraryScreen() }
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePage(navController: NavController) {
    Scaffold(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .padding(it)
                .verticalScroll(rememberScrollState())
        ) {
            LazyRow(modifier = Modifier.fillMaxWidth()) {
                items(10) {
                    FilterChip(
                        selected = false,
                        onClick = { /*TODO*/ },
                        label = { Text(text = "Chip $it") },
                        modifier = Modifier.padding(5.dp)
                    )
                }
            }
            for (i in 1..3) {
                Row(
                    modifier = Modifier.heightIn(max = 100.dp),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    for (i in 1..2) {
                        Card(
                            modifier = Modifier
                                .weight(1f)
                                .padding(horizontal = 12.dp, vertical = 5.dp)
                                .clickable { },

                            ) {
                            Row(
                                modifier = Modifier.fillMaxSize(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Start
                            ) {
                                AsyncImage(
                                    model = ImageRequest.Builder(LocalContext.current)
                                        .data("https://images.unsplash.com/photo-1598875706250-21faaf804361?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1000&q=80")
                                        .build(),
                                    contentDescription = "Dog",
                                    modifier = Modifier.weight(.3f),
                                    contentScale = ContentScale.Crop
                                )
                                Text(
                                    text = "Dog",
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier
                                        .weight(.7f)
                                        .padding(10.dp)
                                )
                            }
                        }
                    }
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = 100.dp)
                    .padding(vertical = 10.dp, horizontal = 35.dp)
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data("https://plus.unsplash.com/premium_photo-1661892088256-0a17130b3d0d?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=880&q=80")
                        .build(),
                    contentDescription = "Dog",
                    modifier = Modifier
                        .clip(CircleShape)
                        .weight(.3f),
                    contentScale = ContentScale.FillWidth,
                )
                Column(
                    modifier = Modifier
                        .weight(.7f)
                        .padding(10.dp)
                ) {

                    Text(
                        text = "A beautiful dog",
                        textAlign = TextAlign.Left,
                    )
                    Text(
                        text = "Dog",
                        textAlign = TextAlign.Left,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = Color.White,
                    )
                }
            }
        }
    }
}