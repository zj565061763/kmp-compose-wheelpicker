package com.sd.demo.kmp.compose_wheelpicker

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.sd.lib.kmp.compose_wheelpicker.FVerticalWheelPicker
import com.sd.lib.kmp.compose_wheelpicker.rememberFWheelPickerState

@Composable
fun SampleCustomFocus(
  onClickBack: () -> Unit,
) {
  RouteScaffold(
    title = "SampleCustomFocus",
    onClickBack = onClickBack,
  ) {
    FVerticalWheelPicker(
      modifier = Modifier.width(128.dp),
      count = 50,
      focus = {
        Box(
          modifier = Modifier
            .fillMaxSize()
            .border(width = 1.dp, color = Color.Red)
        )
      },
    ) { index ->
      Text(index.toString())
    }
  }
}