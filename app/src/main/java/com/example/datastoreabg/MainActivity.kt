package com.example.datastoreabg

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.datastoreabg.ui.theme.DataStoreAbgTheme
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DataStoreAbgTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MyApp()
                }
            }
        }
    }
}

@Composable
fun MyApp() {
    val context = LocalContext.current

    var nameValue by remember { mutableStateOf("") }
    var emailValue by remember { mutableStateOf("") }

    var nameInput by remember { mutableStateOf("") }
    var emailInput by remember { mutableStateOf("") }

    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(context) {
        nameValue = SettingsDataStore.getNameFlow(context).first()
        emailValue = SettingsDataStore.getEmailFlow(context).first()
    }

    Column (
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
    ){
        TextField(value = nameInput,
            onValueChange = { nameInput = it },
            label = {
                Text(text = "Name")
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(value = emailInput,
            onValueChange = { emailInput = it },
            label = {
                Text(text = "Email")
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row (
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Button(onClick = {
                coroutineScope.launch {
                    SettingsDataStore.saveName(context, nameInput)
                    SettingsDataStore.saveEmail(context, emailInput)
                }
            }) {
                Text("SAVE")
            }

            Button(onClick = {
                coroutineScope.launch {
                    nameInput = SettingsDataStore.getNameFlow(context).first()
                    emailInput = SettingsDataStore.getEmailFlow(context).first()
                }
            }) {
                Text("RETRIEVE")
            }

            Button(onClick = {
                nameInput = ""
                emailInput = ""
            }) {
                Text("CLEAR")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    DataStoreAbgTheme {
        MyApp()
    }
}