package com.sd.lib.kmp.compose_wheelpicker

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp

/**
 * The default implementation of focus view in vertical.
 */
@Composable
fun FWheelPickerFocusVertical(
  modifier: Modifier = Modifier,
  thickness: Dp = DividerDefaults.Thickness,
  color: Color = DividerDefaults.color,
) {
  Box(modifier = modifier.fillMaxSize()) {
    HorizontalDivider(
      modifier = Modifier.align(Alignment.TopCenter),
      thickness = thickness,
      color = color,
    )
    HorizontalDivider(
      modifier = Modifier.align(Alignment.BottomCenter),
      thickness = thickness,
      color = color,
    )
  }
}

/**
 * The default implementation of focus view in horizontal.
 */
@Composable
fun FWheelPickerFocusHorizontal(
  modifier: Modifier = Modifier,
  thickness: Dp = DividerDefaults.Thickness,
  color: Color = DividerDefaults.color,
) {
  Box(modifier = modifier.fillMaxSize()) {
    VerticalDivider(
      modifier = Modifier.align(Alignment.CenterStart),
      thickness = thickness,
      color = color,
    )
    VerticalDivider(
      modifier = Modifier.align(Alignment.CenterEnd),
      thickness = thickness,
      color = color,
    )
  }
}