package com.sd.demo.kmp.compose_wheelpicker

import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sd.lib.kmp.compose_wheelpicker.FVerticalWheelPicker

@Composable
fun SampleLargeUnfocusedCount(
  onClickBack: () -> Unit,
) {
  RouteScaffold(
    title = "SampleLargeUnfocusedCount",
    onClickBack = onClickBack,
  ) {
    FVerticalWheelPicker(
      modifier = Modifier.width(128.dp),
      count = 50,
      unfocusedCount = 999,
    ) { index ->
      Text(index.toString())
    }
  }
}