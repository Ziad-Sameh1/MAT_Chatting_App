package com.example.matchatapp.feature_firsttimeopen.presentation.get_started_welcome_screen.components

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
fun GetStartedWelcomeScreen(navController: NavController, configuration: Configuration) {

    CommonBackButton(navController = navController)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 40.dp), horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        CommonImage(
            configuration = configuration,
            imageDrawable = R.drawable.ic_get_started_vector,
            modifier = Modifier.weight(2f)
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .weight(2f), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CommonWelcomeHeader(
                stringResource = R.string.share_anything,
                modifier = Modifier.weight(2f)
            )
            CommonWelcomeDesc(
                stringResource = R.string.share_anything_desc,
                modifier = Modifier.weight(2f)
            )
            CommonWelcomeNavButton(
                navController = navController,
                navigateTo = Screen.EnterYourPhoneNumberScreen.route,
                popUpTo = Screen.VerifyPhoneNumberScreen.route,
                stringResource = R.string.get_started, modifier = Modifier.weight(3f)
            )
            /**
             * Page Indicator
             */
            Row(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 20.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                GetStartedScreenPageIndicator()
            }
        }
    }
}