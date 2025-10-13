package es.usj.jjhernandez.mastertsa

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import es.usj.jjhernandez.mastertsa.ui.theme.MasterTSATheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MasterTSATheme {
                SumScreen()
            }
        }
    }
}

fun plus(a: Int, b: Int)= a + b
fun minus(a: Int, b: Int)= a - b

fun String.toSafeInt(): Int {
    return try {
        this.toInt()
    } catch (_: NumberFormatException) {
        0
    }
}

@Composable
fun SumScreen() {

    var text1 by remember { mutableStateOf("") }
    var text2 by remember { mutableStateOf("") }
    var operation by remember { mutableStateOf(true) }

    val result = if (operation) plus(text1.toSafeInt(), text2.toSafeInt()) else minus(text1.toSafeInt(), text2.toSafeInt())


    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            TextField(
                value = text1,
                onValueChange = { text1 = it },
                label = { Text("Introduce un valor...") },
                textStyle = TextStyle.Default,
                modifier = Modifier.padding(innerPadding)
            )
            Spacer(modifier = Modifier.padding(10.dp))
            TextField(
                value = text2,
                onValueChange = { text2 = it },
                label = { Text("Introduce un valor...") },
                textStyle = TextStyle.Default,
                modifier = Modifier.padding(innerPadding)
                )
            Spacer(modifier = Modifier.padding(10.dp))
            Switch(operation, {
                operation = it
            }, thumbContent = if(operation) { { Text("+") } } else { { Text("-") } })
            Text("Resultado: ${result}")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SumScreenPreview() {
    MasterTSATheme {
        SumScreen()
    }
}