package com.sd.lib.kmp.compose_wheelpicker

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.DecayAnimationSpec
import androidx.compose.animation.core.spring
import androidx.compose.animation.rememberSplineBasedDecay
import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.gestures.snapping.SnapLayoutInfoProvider
import androidx.compose.foundation.gestures.snapping.snapFlingBehavior
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun FVerticalWheelPicker(
  modifier: Modifier = Modifier,
  count: Int,
  state: FWheelPickerState = rememberFWheelPickerState(),
  key: ((index: Int) -> Any)? = null,
  itemHeight: Dp = 48.dp,
  unfocusedCount: Int = 2,
  userScrollEnabled: Boolean = true,
  reverseLayout: Boolean = false,
  focus: @Composable () -> Unit = { FWheelPickerFocusVertical() },
  display: @Composable FWheelPickerDisplayScope.(index: Int) -> Unit = { DefaultWheelPickerDisplay(state, it) },
  content: @Composable (index: Int) -> Unit,
) {
  WheelPicker(
    modifier = modifier,
    isVertical = true,
    count = count,
    state = state,
    key = key,
    itemSize = itemHeight,
    unfocusedCount = unfocusedCount,
    userScrollEnabled = userScrollEnabled,
    reverseLayout = reverseLayout,
    focus = focus,
    display = display,
    content = content,
  )
}

@Composable
fun FHorizontalWheelPicker(
  modifier: Modifier = Modifier,
  count: Int,
  state: FWheelPickerState = rememberFWheelPickerState(),
  key: ((index: Int) -> Any)? = null,
  itemWidth: Dp = 48.dp,
  unfocusedCount: Int = 2,
  userScrollEnabled: Boolean = true,
  reverseLayout: Boolean = false,
  focus: @Composable () -> Unit = { FWheelPickerFocusHorizontal() },
  display: @Composable FWheelPickerDisplayScope.(index: Int) -> Unit = { DefaultWheelPickerDisplay(state, it) },
  content: @Composable (index: Int) -> Unit,
) {
  WheelPicker(
    modifier = modifier,
    isVertical = false,
    count = count,
    state = state,
    key = key,
    itemSize = itemWidth,
    unfocusedCount = unfocusedCount,
    userScrollEnabled = userScrollEnabled,
    reverseLayout = reverseLayout,
    focus = focus,
    display = display,
    content = content,
  )
}

@Composable
private fun WheelPicker(
  modifier: Modifier,
  isVertical: Boolean,
  count: Int,
  state: FWheelPickerState,
  key: ((index: Int) -> Any)?,
  itemSize: Dp,
  unfocusedCount: Int,
  userScrollEnabled: Boolean,
  reverseLayout: Boolean,
  focus: @Composable () -> Unit,
  display: @Composable FWheelPickerDisplayScope.(index: Int) -> Unit,
  content: @Composable (index: Int) -> Unit,
) {
  SafeBox(
    modifier = modifier,
    isVertical = isVertical,
    itemSize = itemSize,
    unfocusedCount = unfocusedCount,
  ) { safeUnfocusedCount ->
    InternalWheelPicker(
      isVertical = isVertical,
      count = count,
      state = state,
      key = key,
      itemSize = itemSize,
      unfocusedCount = safeUnfocusedCount,
      userScrollEnabled = userScrollEnabled,
      reverseLayout = reverseLayout,
      focus = focus,
      display = display,
      content = content,
    )
  }
}

@Composable
private fun InternalWheelPicker(
  isVertical: Boolean,
  count: Int,
  state: FWheelPickerState,
  key: ((index: Int) -> Any)?,
  itemSize: Dp,
  unfocusedCount: Int,
  userScrollEnabled: Boolean,
  reverseLayout: Boolean,
  focus: @Composable () -> Unit,
  display: @Composable FWheelPickerDisplayScope.(index: Int) -> Unit,
  content: @Composable (index: Int) -> Unit,
) {
  if (count <= 0) {
    state.resetData()
    return
  }

  val density = LocalDensity.current
  LaunchedEffect(state, count, unfocusedCount, itemSize, density) {
    state.setData(
      count = count,
      unfocusedCount = unfocusedCount,
      itemSize = with(density) { itemSize.toPx() },
    )
  }

  val displayScope = remember { FWheelPickerDisplayScopeImpl() }.apply {
    this.content = content
  }

  val totalSize = remember(itemSize, unfocusedCount) { itemSize * (unfocusedCount * 2 + 1) }

  Box(
    modifier = Modifier.run { if (isVertical) height(totalSize) else width(totalSize) },
    contentAlignment = Alignment.Center,
  ) {
    val lazyListScope: LazyListScope.() -> Unit = {
      repeat(unfocusedCount) {
        item(contentType = "placeholder") {
          ItemSizeBox(
            isVertical = isVertical,
            itemSize = itemSize,
          )
        }
      }

      items(
        count = count,
        key = key,
      ) { index ->
        ItemSizeBox(
          isVertical = isVertical,
          itemSize = itemSize,
        ) {
          displayScope.display(index)
        }
      }

      repeat(unfocusedCount) {
        item(contentType = "placeholder") {
          ItemSizeBox(
            isVertical = isVertical,
            itemSize = itemSize,
          )
        }
      }
    }

    if (isVertical) {
      LazyColumn(
        modifier = Modifier.matchParentSize(),
        state = state.lazyListState,
        horizontalAlignment = Alignment.CenterHorizontally,
        reverseLayout = reverseLayout,
        userScrollEnabled = userScrollEnabled,
        flingBehavior = rememberSnapFlingBehavior(state.lazyListState),
        content = lazyListScope,
      )
    } else {
      LazyRow(
        modifier = Modifier.matchParentSize(),
        state = state.lazyListState,
        verticalAlignment = Alignment.CenterVertically,
        reverseLayout = reverseLayout,
        userScrollEnabled = userScrollEnabled,
        flingBehavior = rememberSnapFlingBehavior(state.lazyListState),
        content = lazyListScope,
      )
    }

    ItemSizeBox(
      modifier = Modifier.align(Alignment.Center),
      isVertical = isVertical,
      itemSize = itemSize,
    ) {
      focus()
    }
  }
}

@Composable
private fun rememberSnapFlingBehavior(
  lazyListState: LazyListState,
  decayAnimationSpec: DecayAnimationSpec<Float> = rememberSplineBasedDecay(),
  snapAnimationSpec: AnimationSpec<Float> = remember { spring() },
): FlingBehavior {
  val snapLayoutInfoProvider = remember(lazyListState) { SnapLayoutInfoProvider(lazyListState) }
  return remember(snapLayoutInfoProvider, decayAnimationSpec, snapLayoutInfoProvider) {
    snapFlingBehavior(
      snapLayoutInfoProvider = snapLayoutInfoProvider,
      decayAnimationSpec = decayAnimationSpec,
      snapAnimationSpec = snapAnimationSpec,
    )
  }
}

@Composable
private fun SafeBox(
  modifier: Modifier = Modifier,
  isVertical: Boolean,
  itemSize: Dp,
  unfocusedCount: Int,
  content: @Composable (safeUnfocusedCount: Int) -> Unit,
) {
  require(itemSize > 0.dp) { "itemSize > 0.dp required" }
  require(unfocusedCount >= 0) { "unfocusedCount >= 0 required" }

  BoxWithConstraints(
    modifier = modifier,
    contentAlignment = Alignment.Center,
    propagateMinConstraints = true,
  ) {
    val maxSize = if (isVertical) maxHeight else maxWidth
    val result = remember(maxSize, itemSize, unfocusedCount) {
      val totalSize = itemSize * (unfocusedCount * 2 + 1)
      if (totalSize <= maxSize) {
        unfocusedCount
      } else {
        (((maxSize - itemSize) / 2f) / itemSize).toInt().coerceAtLeast(0)
      }
    }
    content(result)
  }
}

@Composable
private fun ItemSizeBox(
  modifier: Modifier = Modifier,
  isVertical: Boolean,
  itemSize: Dp,
  content: @Composable () -> Unit = { },
) {
  Box(
    modifier = modifier.run { if (isVertical) height(itemSize) else width(itemSize) },
    contentAlignment = Alignment.Center,
  ) {
    content()
  }
}

private class FWheelPickerDisplayScopeImpl() : FWheelPickerDisplayScope {
  var content: @Composable (index: Int) -> Unit by mutableStateOf({})

  @Composable
  override fun Content(index: Int) {
    content(index)
  }
}