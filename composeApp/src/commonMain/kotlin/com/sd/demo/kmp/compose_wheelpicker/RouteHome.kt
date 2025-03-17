package com.sd.demo.kmp.compose_wheelpicker

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun RouteHome(
  onClickSampleDatePicker: () -> Unit,
  onClickSampleDefault: () -> Unit,
  onClickSampleCustomFocus: () -> Unit,
  onClickSampleLargeUnfocusedCount: () -> Unit,
  onClickSampleScrollToIndex: () -> Unit,
  onClickSampleReverseLayout: () -> Unit,
) {
  Scaffold { padding ->
    Column(
      modifier = Modifier.fillMaxSize().padding(padding),
      horizontalAlignment = Alignment.CenterHorizontally,
    ) {
      Button(onClick = onClickSampleDatePicker) { Text(text = "SampleDatePicker") }
      Button(onClick = onClickSampleDefault) { Text(text = "SampleDefault") }
      Button(onClick = onClickSampleCustomFocus) { Text(text = "SampleCustomFocus") }
      Button(onClick = onClickSampleLargeUnfocusedCount) { Text(text = "SampleLargeUnfocusedCount") }
      Button(onClick = onClickSampleScrollToIndex) { Text(text = "SampleScrollToIndex") }
      Button(onClick = onClickSampleReverseLayout) { Text(text = "SampleReverseLayout") }
    }
  }
}