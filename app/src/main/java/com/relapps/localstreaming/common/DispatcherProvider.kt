package com.relapps.localstreaming.common

import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class DispatcherProvider @Inject constructor(
    val main: CoroutineDispatcher,
    val io: CoroutineDispatcher,
    val default: CoroutineDispatcher,
)