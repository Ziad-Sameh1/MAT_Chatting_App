package com.example.matchatapp.feature_firsttimeopen.presentation.second_welcome_screen.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.matchatapp.R
import com.example.matchatapp.feature_firsttimeopen.presentation.components.*
import com.example.matchatapp.Screen

@Composable
fun SecondWelcomeScreen(navController: NavController, configuration: Configuration) {
    /**
     * Back Button
     */
    CommonBackButton(navController = navController)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 40.dp), horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        /**
         * Screen Image
         */
        CommonImage(
            configuration = configuration,
            imageDrawable = R.drawable.ic_free_vector,
            modifier = Modifier.weight(2f)
        )
        /**
         * Screen Content
         */
        Column(
            modifier = Modifier
                .fillMaxSize()
                .weight(2f), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CommonWelcomeHeader(
                stringResource = R.string.simple_reliable,
                modifier = Modifier.weight(2f)
            )
            CommonWelcomeDesc(
                stringResource = R.string.simple_reliable_desc,
                modifier = Modifier.weight(2f)
            )
            CommonWelcomeNavButton(
                navController = navController,
                navigateTo = Screen.GetStartedWelcomeScreen.route,
                popUpTo = Screen.SecondWelcomeScreen.route,
                stringResource = R.string.next, modifier = Modifier.weight(3f)
            )
            /**
             * Page Indicator
             * */
            Row(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 20.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                SecondPagePageIndicator()
            }
        }
    }
}