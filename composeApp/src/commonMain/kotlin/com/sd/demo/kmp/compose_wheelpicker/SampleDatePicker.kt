package com.sd.demo.kmp.compose_wheelpicker

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sd.lib.kmp.compose_wheelpicker.FVerticalWheelPicker
import com.sd.lib.kmp.compose_wheelpicker.SnappedIndex
import com.sd.lib.kmp.compose_wheelpicker.rememberFWheelPickerState
import com.sd.lib.kmp.datetime.DateSelector
import com.sd.lib.kmp.datetime.fCurrentDate
import com.sd.lib.kmp.datetime.fDate
import com.sd.lib.kmp.datetime.selectDayOfMonthWithIndex
import com.sd.lib.kmp.datetime.selectMonthWithIndex
import com.sd.lib.kmp.datetime.selectYearWithIndex
import kotlinx.datetime.LocalDate

@Composable
fun SampleDatePicker(
  onClickBack: () -> Unit,
) {
  var date by remember { mutableStateOf(fDate(2023, 2, 28)) }
  var showPicker by remember { mutableStateOf(false) }

  RouteScaffold(
    title = "SampleDatePicker",
    onClickBack = onClickBack,
  ) {
    Button(onClick = { showPicker = true }) {
      Text(text = date.toString())
    }

    Spacer(Modifier.weight(1f))

    AnimatedVisibility(
      visible = showPicker,
      enter = slideInVertically { it },
      exit = slideOutVertically { it },
    ) {
      Picker(
        initialDate = date,
        onDone = {
          showPicker = false
          date = it
        },
      )
    }
  }
}

@Composable
private fun Picker(
  modifier: Modifier = Modifier,
  initialDate: LocalDate,
  onDone: (LocalDate) -> Unit,
) {
  var date by remember { mutableStateOf<LocalDate?>(null) }

  Column(modifier = modifier.fillMaxWidth()) {
    PickerView(
      startDate = remember { fDate(2000, 1, 1) },
      endDate = fCurrentDate(),
      initialDate = initialDate,
      onDate = {
        date = it
      },
    )
    Button(
      modifier = Modifier.align(Alignment.CenterHorizontally),
      onClick = {
        date?.also { onDone(it) }
      },
    ) {
      Text(text = "Done")
    }
  }
}

@Composable
private fun PickerView(
  modifier: Modifier = Modifier,
  startDate: LocalDate,
  endDate: LocalDate,
  initialDate: LocalDate,
  onDate: (LocalDate) -> Unit,
) {
  var selectorState: DateSelector.State? by remember { mutableStateOf(null) }

  val selector = remember(startDate, endDate) {
    DateSelector(
      startDate = startDate,
      endDate = endDate,
      initialDate = initialDate,
      onStateChanged = { selectorState = it },
    )
  }

  val state = selectorState ?: return

  state.date.also { selectorDate ->
    val onDateUpdated by rememberUpdatedState(onDate)
    LaunchedEffect(selectorDate) {
      onDateUpdated(selectorDate)
    }
  }

  val yearState = rememberFWheelPickerState()
  val monthState = rememberFWheelPickerState()
  val dayOfMonthState = rememberFWheelPickerState()

  yearState.SnappedIndex { selector.selectYearWithIndex(it) }
  monthState.SnappedIndex { selector.selectMonthWithIndex(it) }
  dayOfMonthState.SnappedIndex { selector.selectDayOfMonthWithIndex(it) }

  LaunchedEffect(yearState) {
    yearState.scrollToIndex(state.indexOfYear)
  }
  LaunchedEffect(monthState) {
    monthState.scrollToIndex(state.indexOfMonth)
  }
  LaunchedEffect(dayOfMonthState) {
    dayOfMonthState.scrollToIndex(state.indexOfDayOfMonth)
  }

  Row(
    modifier = modifier
      .fillMaxWidth()
      .padding(16.dp),
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.spacedBy(16.dp),
  ) {
    // Year
    val listYear = state.listYear
    FVerticalWheelPicker(
      modifier = Modifier.weight(1f),
      state = yearState,
      count = listYear.size,
    ) { index ->
      listYear.getOrNull(index)?.let { value ->
        Text(text = value.toString())
      }
    }

    // Month
    val listMonth = state.listMonth
    FVerticalWheelPicker(
      modifier = Modifier.weight(1f),
      state = monthState,
      count = listMonth.size,
    ) { index ->
      listMonth.getOrNull(index)?.let { value ->
        Text(text = value.toString().padStart(2, '0'))
      }
    }

    // Day of month
    val listDayOfMonth = state.listDayOfMonth
    FVerticalWheelPicker(
      modifier = Modifier.weight(1f),
      state = dayOfMonthState,
      count = listDayOfMonth.size,
    ) { index ->
      listDayOfMonth.getOrNull(index)?.let { value ->
        Text(text = value.toString().padStart(2, '0'))
      }
    }
  }
}