package es.usj.jjhernandez.mastertsa

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import es.usj.jjhernandez.mastertsa.ui.theme.MasterTSATheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MasterTSATheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    Button(onClick = {
        Toast.makeText(context, "Button Clicked", Toast.LENGTH_SHORT).show()
    }) {
        Text(text = "Click Me")
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MasterTSATheme {
        Greeting("Android Is Awesome!")
    }
}
