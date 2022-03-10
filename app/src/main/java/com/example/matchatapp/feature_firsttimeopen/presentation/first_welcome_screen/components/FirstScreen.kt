package com.example.matchatapp.feature_firsttimeopen.presentation.first_welcome_screen.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.matchatapp.R
import com.example.matchatapp.feature_firsttimeopen.presentation.components.CommonImage
import com.example.matchatapp.feature_firsttimeopen.presentation.components.CommonWelcomeNavButton
import com.example.matchatapp.feature_firsttimeopen.presentation.components.CommonWelcomeDesc
import com.example.matchatapp.feature_firsttimeopen.presentation.components.CommonWelcomeHeader
import com.example.matchatapp.Screen

@Composable
fun FirstWelcomeScreen(navController: NavController, configuration: Configuration) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 40.dp), horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        CommonImage(
            configuration = configuration,
            imageDrawable = R.drawable.ic_secured_chat,
            modifier = Modifier.weight(2f)
        )

        /**
         * Content After the image
         * */
        Column(
            modifier = Modifier
                .fillMaxSize()
                .weight(2f), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CommonWelcomeHeader(modifier = Modifier.weight(2f), stringResource = R.string.secured_chat)
            CommonWelcomeDesc(stringResource = R.string.secured_chat_desc, modifier = Modifier.weight(2f))
            CommonWelcomeNavButton(
                navController = navController,
                navigateTo = Screen.SecondWelcomeScreen.route,
                popUpTo = Screen.FirstWelcomeScreen.route,
                stringResource = R.string.next, modifier = Modifier.weight(3f)
            )

            /**
             * page Indicator
             * */
            Row(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 20.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                FirstScreenPageIndicator()
            }
        }
    }
}