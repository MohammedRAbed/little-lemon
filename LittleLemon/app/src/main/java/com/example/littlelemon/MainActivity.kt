package com.example.littlelemon

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.content.edit
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.example.littlelemon.ui.theme.LittleLemonTheme
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val sharedPreferences = getSharedPreferences(
            PREFS_KEY, Context.MODE_PRIVATE
        )
        val isFirstRun = sharedPreferences.getBoolean("isFirstRun", true)

        if(isFirstRun) {
            sharedPreferences.edit(commit = true) { putBoolean(FIRST_RUN,false) }
            val menuItemsDatabase by lazy {
                Room.databaseBuilder(applicationContext, MenuItemsDatabase::class.java, "database")
                    .build()
            }

            lifecycleScope.launch {
                val menuItems = fetchMenuData()
                saveToDatabase(menuItemsDatabase, menuItems)
            }
        }

        setContent {
            LittleLemonTheme {
                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(WindowInsets.navigationBars.asPaddingValues())
                ) { innerPadding ->
                    LittleLemonNavigation(
                        modifier = Modifier.padding(
                            top = innerPadding.calculateTopPadding(),
                            bottom = 10.dp
                        )
                    )
                }
            }
        }
    }

    private suspend fun fetchMenuData() : List<MenuItemNetwork> {
        val httpClient = HttpClient(Android) {
            install(ContentNegotiation) {
                json(
                    Json { ignoreUnknownKeys = true },
                    contentType = ContentType("text", "plain")
                )
            }
        }
        val response: MenuNetwork = httpClient
            .get("https://raw.githubusercontent.com/Meta-Mobile-Developer-PC/Working-With-Data-API/main/menu.json")
            .body()

        return response.menu
    }


    private suspend fun saveToDatabase(menuItemsDatabase: MenuItemsDatabase, menuItems: List<MenuItemNetwork>) {
        withContext(Dispatchers.IO) {
            val dbMenuItems = menuItems.map { it.toMenuItemEntity() }
            menuItemsDatabase.menuItemDao().insertAll(*dbMenuItems.toTypedArray())
        }
    }

    companion object {
        const val PREFS_KEY = "isLoggedPref"
        const val LOGGED_KEY = "isLogged"
        const val FIRST_RUN = "isFirstRun"
        const val FIRST_NAME_KEY = "firstName"
        const val LAST_NAME_KEY = "lastName"
        const val EMAIL_KEY = "email"
    }
}