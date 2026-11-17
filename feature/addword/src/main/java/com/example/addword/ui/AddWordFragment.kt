package com.example.addword.ui

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.EditText
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.addword.R
import com.example.addword.databinding.FragmentAddWordBinding
import com.example.addword.di.AddWordComponent
import com.example.addword.di.DaggerAddWordComponent
import com.example.addword.navigation.AddWordHost
import kotlinx.coroutines.launch
class AddWordFragment : Fragment(R.layout.fragment_add_word) {

    private lateinit var component: AddWordComponent
    private val viewModel: AddWordViewModel by viewModels {
        component.viewModelFactory()
    }

    private var _binding: FragmentAddWordBinding? = null
    private val binding get() = _binding!!

    override fun onAttach(context: Context) {
        super.onAttach(context)
        component = DaggerAddWordComponent.factory()
            .create((context as AddWordHost).addWordDependencies())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentAddWordBinding.bind(view)

        with(binding) {
            backButton.setOnClickListener { findNavController().popBackStack() }

            wordInput.doAfterTextChanged { viewModel.onWordChange(it?.toString().orEmpty()) }
            translationInput.doAfterTextChanged { viewModel.onTranslationChange(it?.toString().orEmpty()) }
            exampleInput.doAfterTextChanged { viewModel.onExampleChange(it?.toString().orEmpty()) }

            wordInput.setOnFocusChangeListener { _, hasFocus ->
                if (!hasFocus) viewModel.onWordFocusEnded()
            }

            saveButton.setOnClickListener { viewModel.addWord() }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect(::render)
            }
        }
    }

    private fun render(state: AddWordUiState) = with(binding) {
        wordInput.setTextIfChanged(state.word)
        translationInput.setTextIfChanged(state.translation)
        exampleInput.setTextIfChanged(state.example)

        saveButton.isEnabled = state.word.isNotBlank() &&
                state.translation.isNotBlank() &&
                state.example.isNotBlank()
    }

    private fun EditText.setTextIfChanged(newText: String) {
        if (text?.toString() != newText) {
            setText(newText)
            setSelection(newText.length)
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}