package es.usj.jjhernandez.mastertsa

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import es.usj.jjhernandez.mastertsa.ui.theme.MasterTSATheme
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {

    val number = (10000..100000).random()

    var text by mutableStateOf("$number")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LaunchedEffect("delay") {
                delay(3000L)
                text = "Repeat the number"
            }
            val context = LocalContext.current // <-- Get the context here
            MasterTSATheme {
                GuessGameScreen(text, onButtonClicked = {
                    Toast.makeText(context, compare(text, number), Toast.LENGTH_SHORT).show()
                }, onTextChanged = {
                    text = it
                })
            }
        }
    }

    fun compare(text: String, number: Int): String {
        return if (text.toInt() == number) {
            "Correct"
        } else {
            "Incorrect"
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun GuessGameScreen(text: String, onButtonClicked: () -> Unit, onTextChanged: (text: String) -> Unit) {
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text)
            Spacer(Modifier.padding(16.dp))
            TextField("", onValueChange = { onTextChanged(it) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number))
            Spacer(Modifier.padding(16.dp))
            Button(onClick = { onButtonClicked() }) {
                Text("Confirm")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GuessGameScreenPreview() {

    var text by remember { mutableStateOf("12345") }

    MasterTSATheme {

        GuessGameScreen(
            text = text,
            onButtonClicked = { },
            onTextChanged = { newText -> text = newText }
        )
    }
}
