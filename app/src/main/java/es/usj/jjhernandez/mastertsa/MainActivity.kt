package es.usj.jjhernandez.mastertsa

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import es.usj.jjhernandez.mastertsa.ui.theme.MasterTSATheme

class MainActivity : ComponentActivity() {

    var text by mutableStateOf("Click to call")

    private fun call () {
        text = "Calling..."
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MasterTSATheme {
                CallingScreen(
                    text,
                    {
                        call()
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CallingScreen(text: String, onButtonClicked: () -> Unit) {
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
            IconButton(onClick = { onButtonClicked() }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_android_black_24dp), // Your new icon
                    contentDescription = "Click to call"
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CountryListScreenPreview() {
    var text by remember { mutableStateOf("Click to call") }

    MasterTSATheme {
        CallingScreen(text, onButtonClicked = {
            text = "Calling..."
        })
    }
}
