package com.sd.demo.kmp.compose_wheelpicker

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun App() {
  MaterialTheme {
    val navController = rememberNavController()
    NavHost(
      navController = navController,
      startDestination = AppRoute.Home,
    ) {
      composable<AppRoute.Home> {
        RouteHome(
          onClickSampleDatePicker = { navController.navigate(AppRoute.SampleDatePicker) },
          onClickSampleDefault = { navController.navigate(AppRoute.SampleDefault) },
          onClickSampleCustomFocus = { navController.navigate(AppRoute.SampleCustomFocus) },
          onClickSampleLargeUnfocusedCount = { navController.navigate(AppRoute.SampleLargeUnfocusedCount) },
          onClickSampleScrollToIndex = { navController.navigate(AppRoute.SampleScrollToIndex) },
          onClickSampleReverseLayout = { navController.navigate(AppRoute.SampleReverseLayout) },
        )
      }
      composable<AppRoute.SampleDatePicker> { SampleDatePicker(onClickBack = { navController.popBackStack() }) }
      composable<AppRoute.SampleDefault> { SampleDefault(onClickBack = { navController.popBackStack() }) }
      composable<AppRoute.SampleCustomFocus> { SampleCustomFocus(onClickBack = { navController.popBackStack() }) }
      composable<AppRoute.SampleLargeUnfocusedCount> { SampleLargeUnfocusedCount(onClickBack = { navController.popBackStack() }) }
      composable<AppRoute.SampleScrollToIndex> { SampleScrollToIndex(onClickBack = { navController.popBackStack() }) }
      composable<AppRoute.SampleReverseLayout> { SampleReverseLayout(onClickBack = { navController.popBackStack() }) }
    }
  }
}

expect fun logMsg(tag: String = "kmp-compose-wheelpicker", block: () -> String)