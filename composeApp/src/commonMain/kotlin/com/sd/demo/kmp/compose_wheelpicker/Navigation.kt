package com.sd.demo.kmp.compose_wheelpicker

import kotlinx.serialization.Serializable

sealed interface AppRoute {
  @Serializable
  data object Home : AppRoute

  @Serializable
  data object SampleDatePicker : AppRoute

  @Serializable
  data object SampleDefault : AppRoute

  @Serializable
  data object SampleCustomFocus : AppRoute

  @Serializable
  data object SampleLargeUnfocusedCount : AppRoute

  @Serializable
  data object SampleReverseLayout : AppRoute

  @Serializable
  data object SampleScrollToIndex : AppRoute
}