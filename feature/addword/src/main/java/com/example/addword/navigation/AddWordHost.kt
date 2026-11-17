package com.example.addword.navigation

import com.example.addword.di.AddWordDependencies

interface AddWordHost {
    fun addWordDependencies(): AddWordDependencies
}