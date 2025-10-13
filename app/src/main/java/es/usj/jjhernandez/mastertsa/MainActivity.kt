package es.usj.jjhernandez.mastertsa

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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

fun plus(a: Int, b: Int) = a + b
fun minus(a: Int, b: Int) = a - b
fun divide(a: Int, b: Int) = a / b
fun multiply(a: Int, b: Int) = a * b

fun String.toSafeInt(): Int {
    return try {
        this.toInt()
    } catch (_: NumberFormatException) {
        0
    }
}

fun result(operation: String, text1: String, text2: String): String {
    return when (operation) {
        "plus" -> "\n\t Plus: ${plus(text1.toSafeInt(), text2.toSafeInt())}"
        "minus" -> "\n\t Minus: ${minus(text1.toSafeInt(), text2.toSafeInt())}"
        "multiply" -> "\n\t Multiply: ${multiply(text1.toSafeInt(), text2.toSafeInt())}"
        else -> "\n\t Divide: ${divide(text1.toSafeInt(), text2.toSafeInt())}"
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SumScreen() {

    var text1 by remember { mutableStateOf("") }
    var text2 by remember { mutableStateOf("") }
    var operations = arrayOf("plus", "minus", "multiply", "divide")
    var operation by remember { mutableStateOf(operations[0]) }
    var expanded by remember { mutableStateOf(false) }

    var result = result(operation, text1, text2)

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
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }
            ) {
                TextField(
                    value = operation,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Selecciona una operación") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    modifier = Modifier
                        .menuAnchor(type = MenuAnchorType.PrimaryEditable, true)
                )
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    operations.forEach { option ->
                        DropdownMenuItem(
                            text = { Text(option) },
                            onClick = {
                                operation = option
                                expanded = false
                            }
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.padding(10.dp))
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