package es.usj.jjhernandez.mastertsa

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
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

fun result(operations: List<String>, text1: String, text2: String) : String {
    var result = ""
    if(operations.contains("plus"))
        result += "\n\t Plus: ${plus(text1.toSafeInt(), text2.toSafeInt())}"
    if(operations.contains("minus"))
        result += "\n\t Minus: ${minus(text1.toSafeInt(), text2.toSafeInt())}"
return result
}
@Composable
fun SumScreen() {

    var text1 by remember { mutableStateOf("") }
    var text2 by remember { mutableStateOf("") }
    var operations by remember { mutableStateOf(listOf<String>()) }

    var result = result(operations, text1, text2)

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
                modifier = Modifier.padding(innerPadding),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            )
            Spacer(modifier = Modifier.padding(10.dp))
            TextField(
                value = text2,
                onValueChange = { text2 = it },
                label = { Text("Introduce un valor...") },
                textStyle = TextStyle.Default,
                modifier = Modifier.padding(innerPadding),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            Spacer(modifier = Modifier.padding(10.dp))
            Row(
                Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Checkbox(
                    checked = operations.contains("plus"),
                    onCheckedChange = { checked ->
                        operations = if (checked) {
                            operations + "plus"
                        } else {
                            operations - "plus"
                        }
                    }
                )
                Text(
                    text = "Sumar",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }
            Row(
                Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Checkbox(
                    checked = operations.contains("minus"),
                    onCheckedChange = { checked ->
                        operations = if (checked) {
                            operations + "minus"
                        } else {
                            operations - "minus"
                        }
                    }
                )
                Text(
                    text = "Restar",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }
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