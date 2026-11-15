package com.example.roomdaggerapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.roomdaggerapp.ui.theme.RoomDaggerAppTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.getValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.database.domain.DatabaseEntryPoint
import com.example.database.domain.model.WordItem

class MainActivity : ComponentActivity() {

    private val viewModel: MainActivityViewModel by viewModels {
        MainActivityViewModelFactory(
            DatabaseEntryPoint.getDictionaryRepository()
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RoomDaggerAppTheme {
                MainActivityScreen(viewModel)
            }
        }
    }
}


@Composable
fun MainActivityScreen(
    viewModel: MainActivityViewModel
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val words by viewModel.words.collectAsStateWithLifecycle()

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {

            Text(
                text = "My Dictionary",
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = uiState.word,
                onValueChange = viewModel::onWordChange,
                label = { Text("Word") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = uiState.translation,
                onValueChange = viewModel::onTranslationChange,
                label = { Text("Translation") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = uiState.example,
                onValueChange = viewModel::onExampleChange,
                label = { Text("Example") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = viewModel::addWord,
                modifier = Modifier.fillMaxWidth(),
                enabled = uiState.word.isNotBlank() &&
                        uiState.translation.isNotBlank() &&
                        uiState.example.isNotBlank()
            ) {
                Text("Add word")
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Saved words (${words.size})",
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(modifier = Modifier.height(8.dp))

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(words) { item ->

                    WordItemCard(
                        item = item,
                        onDelete = {
                            viewModel.deleteWord(item)
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun WordItemCard(
    item: WordItem,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            Text(
                text = item.word,
                style = MaterialTheme.typography.titleMedium
            )

            Text(
                text = item.translation,
                style = MaterialTheme.typography.bodyLarge
            )

            Text(
                text = item.example,
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Button(onClick = onDelete) {
                    Text("Delete")
                }
            }
        }
    }
}