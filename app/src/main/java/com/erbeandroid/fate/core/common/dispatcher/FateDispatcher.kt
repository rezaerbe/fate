package com.erbeandroid.fate.core.common.dispatcher

import javax.inject.Qualifier
import kotlin.annotation.AnnotationRetention.RUNTIME

@Qualifier
@Retention(RUNTIME)
annotation class Dispatcher(val fateDispatcher: FateDispatcher)

enum class FateDispatcher {
    IO,
    Default
}