package com.sd.lib.kmp.compose_wheelpicker

import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import kotlinx.coroutines.flow.filterNot

@Composable
fun rememberFWheelPickerState(): FWheelPickerState {
  return remember { FWheelPickerState() }.also { state ->
    LaunchedEffect(state) {
      state.updateSnappedIndex()
    }
  }
}

@Composable
fun FWheelPickerState.SnappedIndex(block: (Int) -> Unit) {
  val state = this
  val blockUpdated by rememberUpdatedState(block)
  LaunchedEffect(state) {
    snapshotFlow { state.snappedIndex }
      .collect { blockUpdated(it) }
  }
}

class FWheelPickerState internal constructor() {
  internal val lazyListState = LazyListState()
  private var stateData by mutableStateOf<StateData?>(null)

  var snappedIndex by mutableStateOf(-1)
    private set

  /** [LazyListState.isScrollInProgress] */
  val isScrollInProgress: Boolean get() = lazyListState.isScrollInProgress

  /** [LazyListState.interactionSource] */
  val interactionSource: InteractionSource get() = lazyListState.interactionSource

  suspend fun animateScrollToIndex(index: Int) {
    lazyListState.animateScrollToItem(index)
  }

  suspend fun scrollToIndex(index: Int) {
    lazyListState.scrollToItem(index)
  }

  fun scrollPercentage(index: Int): Float? {
    val data = stateData ?: return null

    val unfocusedCount = data.unfocusedCount
    val itemSize = data.itemSize

    val diffIndex = (index - lazyListState.firstVisibleItemIndex).coerceAtMost(unfocusedCount + 1)
    val diff = diffIndex * itemSize - lazyListState.firstVisibleItemScrollOffset
    val total = (unfocusedCount + 1) * itemSize
    return diff / total
  }

  internal fun setData(
    count: Int,
    unfocusedCount: Int,
    itemSize: Float,
  ) {
    require(count > 0)
    stateData = StateData(
      count = count,
      unfocusedCount = unfocusedCount,
      itemSize = itemSize,
    )
  }

  internal fun resetData() {
    stateData = null
  }

  internal suspend fun updateSnappedIndex() {
    snapshotFlow {
      val currentIndex = if (stateData != null) lazyListState.firstVisibleItemIndex else -1
      currentIndex to isScrollInProgress
    }.filterNot { it.second }
      .collect { snappedIndex = it.first }
  }

  private data class StateData(
    val count: Int,
    val unfocusedCount: Int,
    val itemSize: Float,
  )
}