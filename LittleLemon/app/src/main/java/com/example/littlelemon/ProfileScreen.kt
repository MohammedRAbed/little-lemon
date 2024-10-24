package com.example.littlelemon

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.edit
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.littlelemon.MainActivity.Companion.EMAIL_KEY
import com.example.littlelemon.MainActivity.Companion.FIRST_NAME_KEY
import com.example.littlelemon.MainActivity.Companion.LAST_NAME_KEY
import com.example.littlelemon.MainActivity.Companion.LOGGED_KEY
import com.example.littlelemon.MainActivity.Companion.PREFS_KEY
import com.example.littlelemon.ui.theme.OrangeDark
import com.example.littlelemon.ui.theme.Typography

@Composable
fun ProfileScreen(navController: NavController) {
    Column {
        OnBoardingHeader()
        UpdateInputBox(navController)
    }
}



@Composable
fun UpdateInputBox(navController: NavController) {
    val context = LocalContext.current

    val sharedPreferences = context.getSharedPreferences(
        PREFS_KEY, Context.MODE_PRIVATE
    )

    val firstName = sharedPreferences.getString(FIRST_NAME_KEY, "")
    val lastName = sharedPreferences.getString(LAST_NAME_KEY, "")
    val email = sharedPreferences.getString(EMAIL_KEY, "")

    Column(
        modifier = Modifier
            .padding(20.dp, vertical = 40.dp)
    ) {
        Text(
            text = "Profile information",
            style = Typography.titleMedium,
            modifier = Modifier.padding(bottom = 10.dp)
        )
        TextFieldBox(
            title = "First Name",
            placeholder = "Tilly",
            keyboardType = KeyboardType.Text,
            value = firstName!!,
            enabled = false
        )
        TextFieldBox(
            title = "Last Name",
            placeholder = "Doe",
            keyboardType = KeyboardType.Text,
            value = lastName!!,
            enabled = false
        )
        TextFieldBox(
            title = "Email",
            placeholder = "tillydoe@example.com",
            keyboardType = KeyboardType.Email,
            value = email!!,
            enabled = false
        )
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 30.dp),
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = OrangeDark,
                contentColor = Color.Black
            ),
            shape = RoundedCornerShape(10.dp),
            onClick = {
                "Logged out".showToast(context)
                sharedPreferences.edit(commit = true) {
                    putBoolean(LOGGED_KEY, false)
                    putString(FIRST_NAME_KEY, "")
                    putString(LAST_NAME_KEY, "")
                    putString(EMAIL_KEY, "")
                }
                navController.navigate(OnBoarding.route) {
                    popUpTo(Profile.route) {
                        inclusive = true
                    }
                }

            }
        ) {
            Text(text = "Logout", style = Typography.titleMedium)
        }
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun ProfileScreenPreview() {
    ProfileScreen(rememberNavController())
}