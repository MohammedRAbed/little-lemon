package com.example.littlelemon

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.littlelemon.ui.theme.Green
import com.example.littlelemon.ui.theme.GreyLight
import com.example.littlelemon.ui.theme.MarkaziText
import com.example.littlelemon.ui.theme.Typography
import com.example.littlelemon.ui.theme.Yellow


@Composable
fun HomeScreen(navController: NavController) {
    val context = LocalContext.current
    val menuItemsDatabase by lazy {
        Room.databaseBuilder(context, MenuItemsDatabase::class.java, "database").build()
    }
    val databaseMenuItems by menuItemsDatabase.menuItemDao().getAll().observeAsState(initial = emptyList())

    //filtering for search
    var filteredMenuItems by remember { mutableStateOf(databaseMenuItems) }
    filteredMenuItems = databaseMenuItems
    var searchPhrase by remember { mutableStateOf("") }

    //filtering and grouping by category
    val groupedMenuItems = databaseMenuItems.groupBy { it.category }
    val categories = groupedMenuItems.keys.toList()
    var selectedCategory by remember { mutableStateOf("") }

    MaterialTheme(typography = Typography) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState()),
        ) {
            HomeHeader(navController)
            HomeHero(searchPhrase) {
                searchPhrase = it
                filteredMenuItems = if (it.isNotEmpty()) {
                    databaseMenuItems.filter { item -> item.title.contains(it, ignoreCase = true) }
                } else databaseMenuItems
            }
            CategoriesSection(categories) {
                selectedCategory = it
                filteredMenuItems = if (it.isNotEmpty()) {
                    databaseMenuItems.filter { item -> item.category == it }
                } else databaseMenuItems
            }
            HorizontalDivider(
                thickness = 1.dp,
                modifier = Modifier.padding(bottom = 10.dp, end = 20.dp, start = 20.dp)
            )
            MenuDishList(filteredMenuItems)
        }
    }
}

@Composable
fun HomeHeader(navController: NavController) {
    Box {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Image(
                painter = painterResource(id = R.drawable.littlelemon_logo),
                modifier = Modifier
                    .width(200.dp)
                    .height(64.dp),
                contentDescription = "Logo"
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Spacer(modifier = Modifier.weight(1f))
            Image(
                painter = painterResource(id = R.drawable.profile),
                contentDescription = null,
                modifier = Modifier
                    .size(64.dp)
                    .clickable {
                        navController.navigate(Profile.route)
                    }
                    .clip(RoundedCornerShape(16.dp))
                    .padding(end = 10.dp)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeHero(searchPhrase: String, onPhraseChange: (String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Green)
            .padding(15.dp)
    ) {
        RestaurantName(name = "Little Lemon")

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 25.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.padding(end = 5.dp)) {
                Text(
                    text = "Chicago",
                    color = Color.White,
                    style = Typography.displaySmall,
                    modifier = Modifier.padding(bottom = 25.dp)
                )
                Text(
                    text = "We are a family-owned Mediterranean restaurant, focused on traditional recipes served with a modern twist",
                    style = Typography.bodyLarge,
                    color = Color.White,
                    modifier = Modifier.fillMaxWidth(0.59f)
                )
            }

            Image(
                painter = painterResource(id = R.drawable.hero),
                contentDescription = "",
                modifier = Modifier
                    .width(200.dp)
                    .height(150.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .padding(start = 5.dp),
                contentScale = ContentScale.FillWidth
            )
        }

        OutlinedTextField(
            value = searchPhrase,
            onValueChange = onPhraseChange,
            leadingIcon = {
                Icon(imageVector = Icons.Default.Search, contentDescription = "Search Icon")
            },
            placeholder = {
                Text(text = "Enter search phrase", fontSize = 22.sp)
            },
            textStyle = TextStyle(fontSize = 22.sp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            shape = RoundedCornerShape(10.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,
                containerColor = GreyLight
            )
        )
    }
}

@Composable
fun RestaurantName(name: String) {
    Text(
        text = name,
        style = Typography.displayLarge,
        lineHeight = 20.sp,
        color = Yellow
    )
}


@Composable
fun CategoriesSection(categoriesList: List<String>, onCategorySelect: (String) -> Unit) {
    Column(
        modifier = Modifier.padding(start = 20.dp, end = 20.dp, top = 10.dp)
    ) {
        Text(
            text = "ORDER FOR DELIVERY!",
            fontSize = 24.sp,
            fontFamily = MarkaziText,
            fontWeight = FontWeight.Black
        )
        LazyRow(
            modifier = Modifier.padding(vertical = 10.dp)
        ) {
            items(categoriesList) {item->
                Category(category = item) {
                    onCategorySelect(item)
                }
            }
        }
    }
}


@Composable
fun Category(category: String, onCategorySelect: () -> Unit) {
    Button(
        modifier = Modifier.padding(horizontal = 7.dp),
        colors = ButtonDefaults.buttonColors(
            GreyLight,
            contentColor = Green
        ),
        onClick = onCategorySelect,
        shape = RoundedCornerShape(16.dp)
    ) {
        Text(
            text = category,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
        )
    }
}

@Composable
fun MenuDishList(menuItems: List<MenuItemNetwork>) {
    Column(modifier = Modifier.padding(horizontal = 20.dp)) {
        menuItems.forEach {
            MenuItem(item = it)
        }
    }
}

@Composable
fun MenuItem(item: MenuItemNetwork) {
    Column {
        Card(
            colors = CardDefaults.cardColors(Color(0x00000000)),
            onClick = { }
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = item.title,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.headlineMedium
                    )
                    Text(
                        text = item.description,
                        color = Color.Gray,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier
                            .padding(vertical = 5.dp)
                            .fillMaxWidth(0.75f)
                    )
                    Text(
                        text = "$${item.price}",
                        fontWeight = FontWeight.Bold,
                        color = Color.Gray,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                GlideImage(
                    // There is a problem with other images, so I made it greeksalad for them all.
                    imageUrl = item.image,
                    modifier = Modifier
                        .height(100.dp)
                        .clip(RoundedCornerShape(10.dp))
                )
            }
        }
        HorizontalDivider(
            modifier = Modifier.padding(start = 8.dp, end = 8.dp),
            thickness = 0.3.dp,
        )
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun HomeScreenPreview() {
    HomeScreen(rememberNavController())
}