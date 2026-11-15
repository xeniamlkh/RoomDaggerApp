package com.example.roomdaggerapp.mainscreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.database.domain.model.WordItem

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
                style = typography.headlineMedium
            )

            Spacer(modifier = Modifier.height(16.dp))

            AppOutlinedTextField(
                value = uiState.word,
                onValueChange = viewModel::onWordChange,
                label = "Word",
                onFocusEnded = viewModel::onWordFocusEnded
            )

            Spacer(modifier = Modifier.height(8.dp))

            AppOutlinedTextField(
                value = uiState.translation,
                onValueChange = viewModel::onTranslationChange,
                label = "Translation"
            )

            Spacer(modifier = Modifier.height(8.dp))

            AppOutlinedTextField(
                value = uiState.example,
                onValueChange = viewModel::onExampleChange,
                label = "Example"
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
                style = typography.titleLarge
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
                style = typography.titleMedium
            )

            Text(
                text = item.translation,
                style = typography.bodyLarge
            )

            Text(
                text = item.example,
                style = typography.bodyMedium
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

@Composable
fun AppOutlinedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    singleLine: Boolean = true,
    focusRequester: FocusRequester = FocusRequester(),
    onFocus: () -> Unit = {},
    onFocusEnded: () -> Unit = {}
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier
            .fillMaxWidth()
            .focusRequester(focusRequester)
            .onFocusChanged(
                onFocusChanged = {
                    if (it.isFocused) onFocus() else onFocusEnded()
                }
            ),
        label = { Text(text = label) },
        singleLine = singleLine,
        shape = RoundedCornerShape(14.dp)
    )
}