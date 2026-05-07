package com.relapps.localstreaming.common

import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

/*
This class exists to be injected into repositories so that non-amortized actions such as json or
CSV parsing to domain objects can be pointed and launched on correct thread - not main

The separate provider exists so that repositories can be tested when provided with special test
coroutine dispatcher designed for coroutine automated tests
 */
class DispatcherProvider @Inject constructor(
    val main: CoroutineDispatcher,
    val io: CoroutineDispatcher,
    val default: CoroutineDispatcher,
)