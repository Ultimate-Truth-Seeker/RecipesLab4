package com.example.composelab4

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.example.composelab4.ui.theme.ComposeLab4Theme
import com.example.composelab4.ui.theme.Purple40

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposeLab4Theme {
                Scaffold (modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MyScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ComposeLab4Theme {
        Greeting("Android")
    }
}

@Composable
fun MyScreen(modifier: Modifier = Modifier) {
    // State to hold the name, URL, and list of items
    var name by remember { mutableStateOf("") }
    var url by remember { mutableStateOf("") }
    var itemList by remember { mutableStateOf(listOf<Pair<String, String>>()) }

    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "\nHealthy Living App\n",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight(500),
            textAlign = TextAlign.Center,
            color = Purple40
        )
        
        // TextField for name
        TextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Nombre de receta") },
            modifier = Modifier.fillMaxWidth(),
        )

        Spacer(modifier = Modifier.height(8.dp))

        // TextField for URL
        TextField(
            value = url,
            onValueChange = { url = it },
            label = { Text("URL de imágen") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Button to add item to the list
        Button(onClick = {
            // Valida si no está vacío o repetido
            if (name.isNotEmpty() && url.isNotEmpty() && !itemList.contains(Pair<String, String>(name, url))) {
                itemList = itemList + Pair(name, url)
                name = ""
                url = ""
            }
        }) {
            Text("Agregar")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // LazyColumn to display the list of items
        LazyColumn {
            items(itemList) { item ->
                RecipeCard(item.first, item.second) { itemList = itemList - item }
            }
        }
    }


}

@Composable
fun RecipeCard(name: String, imageUrl: String, onRemove: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onRemove() },
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            // Display the image
            Image(
                painter = rememberAsyncImagePainter(model = imageUrl),
                contentDescription = null,
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.surface)
            )

            Spacer(modifier = Modifier.width(16.dp))

            // Display the name
            Text(
                text = name,
                style = MaterialTheme.typography.headlineSmall
            )
        }
    }
}









