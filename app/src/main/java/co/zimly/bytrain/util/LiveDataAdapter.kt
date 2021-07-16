package co.zimly.bytrain.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.LiveData

// Non-nullable version of `observeAsState()`
@Composable
fun <T> LiveData<T>.observeAsState(): State<T> = observeAsState(value!!)
