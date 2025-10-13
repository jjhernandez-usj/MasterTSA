package es.usj.jjhernandez.mastertsa

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import es.usj.jjhernandez.mastertsa.ui.theme.MasterTSATheme

val countryName = arrayOf(
    "Argentina", "Brazil", "Bolivia", "Chile", "Colombia",
    "Ecuador", "Guayana Francesa", "Guyana", "Islas Falkland",
    "Paraguay", "Peru", "Suriname", "Uruguay", "Venezuela"
)
val countryPopulation = arrayOf(
    "43132000", "204519000", "10520000", "18006000", "45549000",
    "16279000", "262000", "747000", "3000", "7003000",
    "31153000", "560000", "3310000", "30620000"
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MasterTSATheme {
                CountryListScreen()
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CountryListScreen() {
    var selected by remember { mutableIntStateOf(-1) }

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.5f)
            ) {
                stickyHeader {
                    Text(
                        "Países",
                        Modifier
                            .fillMaxWidth()
                            .background(Color.LightGray)
                            .padding(8.dp)
                    )
                }
                items(countryName.size) { item ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { selected = item }
                            .padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = countryName[item],
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (selected != -1) {
                Text(
                    text = "La población de ${countryName[selected]} es ${countryPopulation[selected]} habitantes",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CountryListScreenPreview() {
    MasterTSATheme {
        CountryListScreen()
    }
}
