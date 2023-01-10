package com.src.todo.presentation.utils

sealed class State<out T : Any> {
    class EmptyState<out T : Any> : State<T>()
    class LoadingState<out T : Any> : State<T>()
    class SuccessState<out T : Any>(val data: T) : State<T>()
}