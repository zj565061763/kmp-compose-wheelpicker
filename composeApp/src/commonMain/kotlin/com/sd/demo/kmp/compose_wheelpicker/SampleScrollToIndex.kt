package com.sd.demo.kmp.compose_wheelpicker

import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sd.lib.kmp.compose_wheelpicker.FVerticalWheelPicker
import com.sd.lib.kmp.compose_wheelpicker.SnappedIndex
import com.sd.lib.kmp.compose_wheelpicker.rememberFWheelPickerState
import kotlinx.coroutines.delay

@Composable
fun SampleScrollToIndex(
  onClickBack: () -> Unit,
) {
  val state = rememberFWheelPickerState()

  RouteScaffold(
    title = "SampleScrollToIndex",
    onClickBack = onClickBack,
  ) {
    FVerticalWheelPicker(
      modifier = Modifier.width(128.dp),
      state = state,
      count = 50,
    ) { index ->
      Text(index.toString())
    }
  }

  LaunchedEffect(state) {
    state.scrollToIndex(5)
    delay(3_000)
    state.animateScrollToIndex(10)
  }

  state.SnappedIndex {
    logMsg { "SnappedIndex:$it" }
  }
}