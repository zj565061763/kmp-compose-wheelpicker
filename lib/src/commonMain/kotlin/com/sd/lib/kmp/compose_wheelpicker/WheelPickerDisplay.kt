package com.sd.lib.kmp.compose_wheelpicker

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import kotlin.math.abs

interface FWheelPickerDisplayScope {
  @Composable
  fun Content(index: Int)
}

@Composable
fun FWheelPickerDisplayScope.DefaultWheelPickerDisplay(
  state: FWheelPickerState,
  index: Int,
) {
  val percentage = state.scrollPercentage(index)
  val alphaPercentage = if (percentage != null) (1f - abs(percentage)).coerceAtLeast(0.5f) else 0f

  Box(
    modifier = Modifier.graphicsLayer {
      alpha = alphaPercentage
      scaleX = alphaPercentage
      scaleY = alphaPercentage
    },
    contentAlignment = Alignment.Center
  ) {
    Content(index)
  }
}