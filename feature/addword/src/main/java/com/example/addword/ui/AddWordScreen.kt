package com.example.addword.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun AddWordScreen(
    viewModel: AddWordViewModel,
    onBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            TextButton(onClick = onBack) { Text("Back") }

            Text(
                text = "Add new word",
                style = MaterialTheme.typography.headlineMedium
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
                Text("Save word")
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