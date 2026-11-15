package com.example.network.domain

import com.example.network.domain.repository.ITatoebaApi
import com.example.network.net.NetworkFactory

object NetworkEntryPoint {

    @Volatile private var repository: ITatoebaApi? = null

    fun init(): ITatoebaApi =
        repository ?: NetworkFactory
            .createTatoebaApi()
            .also { repository = it }

    fun getNetworkApi(): ITatoebaApi =
        repository ?: error("NetworkEntryPoint.init() was not called")
}