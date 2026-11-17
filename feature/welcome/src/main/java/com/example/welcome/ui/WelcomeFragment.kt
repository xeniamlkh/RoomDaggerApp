package com.example.welcome.ui

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.welcome.R
import com.example.welcome.databinding.FragmentWelcomeBinding
import com.example.welcome.di.WelcomeComponent
import com.example.welcome.navigation.WelcomeHost
import kotlinx.coroutines.launch

class WelcomeFragment : Fragment(R.layout.fragment_welcome) {
    private lateinit var component: WelcomeComponent
    private val viewModel: WelcomeViewModel by viewModels {
        component.welcomeViewModelFactory()
    }
    private val adapter = WordAdapter(onDelete = { viewModel.deleteWord(it) })
    private var _binding: FragmentWelcomeBinding? = null
    private val binding get() = _binding!!

    override fun onAttach(context: Context) {
        super.onAttach(context)
        component = (context as WelcomeHost)
            .welcomeComponentFactory()
            .create()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentWelcomeBinding.bind(view)

        binding.wordsList.adapter = adapter
        binding.addWordButton.setOnClickListener {
            (requireActivity() as WelcomeHost).openAddWord()
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.words.collect { words ->
                    adapter.submitList(words)
                    binding.savedWordsTitle.text =
                        getString(R.string.saved_words, words.size)
                }
            }
        }
    }

    override fun onDestroyView() {
        binding.wordsList.adapter = null
        _binding = null
        super.onDestroyView()
    }
}