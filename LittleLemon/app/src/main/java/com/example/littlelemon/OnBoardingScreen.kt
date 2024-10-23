package com.example.littlelemon

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.edit
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.littlelemon.MainActivity.Companion.EMAIL_KEY
import com.example.littlelemon.MainActivity.Companion.FIRST_NAME_KEY
import com.example.littlelemon.MainActivity.Companion.LAST_NAME_KEY
import com.example.littlelemon.MainActivity.Companion.LOGGED_KEY
import com.example.littlelemon.MainActivity.Companion.PREFS_KEY
import com.example.littlelemon.ui.theme.Typography
import com.example.littlelemon.ui.theme.Yellow

@Composable
fun OnBoardingScreen(navController: NavController) {
    MaterialTheme(typography = Typography) {
        Column {
            OnBoardingHeader()
            ContentBox(navController)
        }
    }
}

@Composable
fun ContentBox(navController: NavController) {
    WelcomeBox()
    RegisterInputBox(navController)
}

@Composable
fun WelcomeBox() {
    Column(
        modifier = Modifier
    ) {
        Text(
            text = "Let's get to know you",
            style = Typography.titleLarge,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF495E57))
                .padding(horizontal = 20.dp, vertical = 30.dp),
            color = Color.White,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun OnBoardingHeader() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        Image(
            painter = painterResource(id = R.drawable.littlelemon_logo),
            contentDescription = "",
            modifier = Modifier
                .width(200.dp)
                .height(64.dp)
        )
    }
}

@Composable
fun RegisterInputBox(navController: NavController) {
    val context = LocalContext.current

    val sharedPreferences = context.getSharedPreferences(
        PREFS_KEY, Context.MODE_PRIVATE
    )

    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    val inputIsFilled =
        !(firstName.isEmpty() || lastName.isEmpty() || email.isEmpty())

    Column(
        modifier = Modifier.padding(20.dp, vertical = 40.dp)
    ) {
        Text(
            text = "Personal information",
            style = Typography.titleMedium,
            modifier = Modifier.padding(bottom = 10.dp)
        )
        TextFieldBox(
            title = "First Name",
            placeholder = "Tilly",
            keyboardType = KeyboardType.Text,
            value = firstName,
        ) { firstName = it }
        TextFieldBox(
            title = "Last Name",
            placeholder = "Doe",
            keyboardType = KeyboardType.Text,
            value = lastName,
        ) { lastName = it }
        TextFieldBox(
            title = "Email",
            placeholder = "tillydoe@example.com",
            keyboardType = KeyboardType.Email,
            value = email,
        ) { email = it }
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
                containerColor = Yellow,
                contentColor = Color.Black
            ),
            shape = RoundedCornerShape(10.dp),
            onClick = {
                if (inputIsFilled) {
                    sharedPreferences.edit(commit = true) {
                        putBoolean(LOGGED_KEY, true)
                        putString(FIRST_NAME_KEY, firstName)
                        putString(LAST_NAME_KEY, lastName)
                        putString(EMAIL_KEY, email)
                    }
                    navController.navigate(Home.route) {
                        popUpTo(OnBoarding.route) {
                            inclusive = true
                        }
                    }
                    "Registration successful !".showToast(context)
                } else {
                    "Registration unsuccessful".showToast(context)
                }
            }
        ) {
            Text(text = "Register", style = Typography.titleMedium)
        }
    }
}

@Composable
fun TextFieldBox(
    title: String = "",
    placeholder: String = "",
    keyboardType: KeyboardType = KeyboardType.Text,
    enabled: Boolean = true,
    value: String,
    onValueChange: (String) -> Unit = {}
) {

    val focusManager = LocalFocusManager.current

    Column {
        Text(
            text = title,
            fontSize = 12.sp,
            fontWeight = FontWeight.Normal,
            modifier = Modifier.padding(top = 30.dp, bottom = 5.dp)
        )

        OutlinedTextField(
            value = value, onValueChange = { onValueChange(it) },
            modifier = Modifier.fillMaxWidth(),
            placeholder = {
                Text(text = placeholder)
            },
            enabled = enabled,
            maxLines = 1,
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            keyboardActions = KeyboardActions(onNext = {focusManager.moveFocus(FocusDirection.Down)})
        )
    }
}

fun String.showToast(context: Context) {
    Toast.makeText(context, this, Toast.LENGTH_SHORT).show()
}

@Preview(
    showBackground = true,
    showSystemUi = true,
)
@Composable
fun OnBoardingPreview() {
    OnBoardingScreen(rememberNavController())
}