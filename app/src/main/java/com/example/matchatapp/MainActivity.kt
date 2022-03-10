package com.example.matchatapp

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalFocusManager
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.matchatapp.feature_chatting.presentation.add_group_screen.AddGroupScreen
import com.example.matchatapp.feature_chatting.presentation.add_group_screen.AddGroupViewModel
import com.example.matchatapp.feature_chatting.presentation.chat_list.ChatListScreen
import com.example.matchatapp.feature_chatting.presentation.chat_list.ChatListViewModel
import com.example.matchatapp.feature_chatting.presentation.chatting_room.ChattingRoomScreen
import com.example.matchatapp.feature_chatting.presentation.chatting_room.ChattingRoomViewModel
import com.example.matchatapp.feature_chatting.presentation.profile_screen.ProfileScreen
import com.example.matchatapp.feature_chatting.presentation.profile_screen.ProfileViewModel
import com.example.matchatapp.feature_chatting.presentation.view_profile_pic_screen.ViewProfilePicScreen
import com.example.matchatapp.feature_firsttimeopen.presentation.enter_personal_info.EnterPersonalInfoScreen
import com.example.matchatapp.feature_firsttimeopen.presentation.enter_your_phonenumber.EnterYourPhoneNumberViewModel
import com.example.matchatapp.feature_firsttimeopen.presentation.enter_your_phonenumber.components.EnterYourPhoneNumberScreen
import com.example.matchatapp.feature_firsttimeopen.presentation.first_welcome_screen.components.FirstWelcomeScreen
import com.example.matchatapp.feature_firsttimeopen.presentation.get_started_welcome_screen.components.GetStartedWelcomeScreen
import com.example.matchatapp.feature_firsttimeopen.presentation.second_welcome_screen.components.SecondWelcomeScreen
import com.example.matchatapp.feature_firsttimeopen.presentation.verify_your_phone_number.VerifyPhoneNumberViewModel
import com.example.matchatapp.feature_firsttimeopen.presentation.verify_your_phone_number.components.VerifyPhoneNumberScreen
import com.example.matchatapp.feature_firsttimeopen.util.isStatusColorPrimary
import com.example.matchatapp.ui.theme.MatChatAppTheme
import com.example.matchatapp.ui.theme.Red500
import com.example.matchatapp.utils.Constants
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var systemUiController: SystemUiController
    private var dark = false
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var sharedEditor: SharedPreferences.Editor
    private var startDestination = ""
    private var verificationState: String = ""

    @RequiresApi(Build.VERSION_CODES.O)
    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val mainActivityViewModel: MainActivityViewModel by viewModels()
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        installSplashScreen().apply {
            setKeepVisibleCondition {
                mainActivityViewModel.loadingState.value
            }
        }
        setContent {
            MatChatAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val navController = rememberAnimatedNavController()
                    val configuration = LocalConfiguration.current
                    val focusManager = LocalFocusManager.current
                    // ViewModels
                    val enterYourPhoneNumberViewModel: EnterYourPhoneNumberViewModel by viewModels()
                    val verifyPhoneNumberViewModel: VerifyPhoneNumberViewModel by viewModels()
                    val chatListViewModel: ChatListViewModel by viewModels()
                    val addGroupViewModel: AddGroupViewModel by viewModels()
                    val profileViewModel: ProfileViewModel by viewModels()
                    val chattingRoomViewModel: ChattingRoomViewModel by viewModels()
                    systemUiController = rememberSystemUiController()
                    dark = isSystemInDarkTheme()
                    sharedPreferences = getPreferences(Context.MODE_PRIVATE)
                    sharedEditor = sharedPreferences.edit()
                    verificationState = isVerified()
                    when (verificationState) {
                        Constants.NOT_VERIFIED -> {
                            Log.i(Constants.TAG, "onCreate: Not Verified")
                            startDestination = Screen.FirstWelcomeScreen.route
                        }
                        Constants.VERIFIED -> {
                            Log.i(Constants.TAG, "onCreate: Verified")
                            startDestination = Screen.ChatListScreen.route
                        }
                    }
                    AnimatedNavHost(
                        navController = navController,
                        startDestination = startDestination
                    ) {
                        composable(
                            Screen.FirstWelcomeScreen.route,
                            exitTransition = {
                                when (targetState.destination.route) {
                                    Screen.SecondWelcomeScreen.route ->
                                        slideOutOfContainer(
                                            AnimatedContentScope.SlideDirection.Left,
                                            animationSpec = tween(700)
                                        )
                                    else -> null
                                }
                            },
                            popEnterTransition = {
                                when (initialState.destination.route) {
                                    Screen.SecondWelcomeScreen.route ->
                                        slideIntoContainer(
                                            AnimatedContentScope.SlideDirection.Right,
                                            animationSpec = tween(700)
                                        )
                                    else -> null
                                }
                            },
                        ) {
                            isStatusColorPrimary = false // to make the status bar white
                            changeStatusBarColor()
                            FirstWelcomeScreen(navController, configuration = configuration)
                        }
                        composable(
                            Screen.SecondWelcomeScreen.route,
                            enterTransition = {
                                when (initialState.destination.route) {
                                    Screen.FirstWelcomeScreen.route ->
                                        slideIntoContainer(
                                            AnimatedContentScope.SlideDirection.Left,
                                            animationSpec = tween(700)
                                        )
                                    else -> null
                                }
                            },
                            exitTransition = {
                                when (targetState.destination.route) {
                                    Screen.GetStartedWelcomeScreen.route -> slideOutOfContainer(
                                        AnimatedContentScope.SlideDirection.Left,
                                        animationSpec = tween(700)
                                    )
                                    else -> null
                                }
                            },
                            popEnterTransition = {
                                when (initialState.destination.route) {
                                    Screen.GetStartedWelcomeScreen.route ->
                                        slideIntoContainer(
                                            AnimatedContentScope.SlideDirection.Right,
                                            animationSpec = tween(700)
                                        )
                                    else -> null
                                }
                            },
                            popExitTransition = {
                                when (targetState.destination.route) {
                                    Screen.FirstWelcomeScreen.route ->
                                        slideOutOfContainer(
                                            AnimatedContentScope.SlideDirection.Right,
                                            animationSpec = tween(700)
                                        )
                                    else -> null
                                }
                            }
                        ) {
                            SecondWelcomeScreen(
                                navController = navController,
                                configuration = configuration
                            )
                        }
                        composable(
                            Screen.GetStartedWelcomeScreen.route,
                            enterTransition = {
                                when (initialState.destination.route) {
                                    Screen.SecondWelcomeScreen.route ->
                                        slideIntoContainer(
                                            AnimatedContentScope.SlideDirection.Left,
                                            animationSpec = tween(700)
                                        )
                                    else -> null
                                }
                            },
                            exitTransition = {
                                when (targetState.destination.route) {
                                    Screen.EnterYourPhoneNumberScreen.route -> slideOutOfContainer(
                                        AnimatedContentScope.SlideDirection.Left,
                                        animationSpec = tween(700)
                                    )
                                    else -> null
                                }
                            },
                            popEnterTransition = {
                                when (initialState.destination.route) {
                                    Screen.EnterYourPhoneNumberScreen.route ->
                                        slideIntoContainer(
                                            AnimatedContentScope.SlideDirection.Right,
                                            animationSpec = tween(700)
                                        )
                                    else -> null
                                }
                            },
                            popExitTransition = {
                                when (targetState.destination.route) {
                                    Screen.SecondWelcomeScreen.route ->
                                        slideOutOfContainer(
                                            AnimatedContentScope.SlideDirection.Right,
                                            animationSpec = tween(700)
                                        )
                                    else -> null
                                }
                            }
                        ) {
                            GetStartedWelcomeScreen(
                                navController = navController,
                                configuration = configuration
                            )
                        }
                        composable(
                            Screen.EnterYourPhoneNumberScreen.route,
                            enterTransition = {
                                when (initialState.destination.route) {
                                    Screen.GetStartedWelcomeScreen.route -> slideIntoContainer(
                                        AnimatedContentScope.SlideDirection.Left,
                                        animationSpec = tween(700)
                                    )
                                    else -> null
                                }
                            },
                            exitTransition = {
                                when (targetState.destination.route) {
                                    Screen.VerifyPhoneNumberScreen.route + "/{phoneNumber}" -> slideOutOfContainer(
                                        AnimatedContentScope.SlideDirection.Left,
                                        animationSpec = tween(700)
                                    )
                                    else -> null
                                }
                            },
                            popEnterTransition = {
                                when (initialState.destination.route) {
                                    Screen.VerifyPhoneNumberScreen.route + "/{phoneNumber}" -> slideIntoContainer(
                                        AnimatedContentScope.SlideDirection.Right,
                                        animationSpec = tween(700)
                                    )
                                    else -> null
                                }
                            },
                            popExitTransition = {
                                when (targetState.destination.route) {
                                    Screen.GetStartedWelcomeScreen.route -> slideOutOfContainer(
                                        AnimatedContentScope.SlideDirection.Right,
                                        animationSpec = tween(700)
                                    )
                                    else -> null
                                }
                            }
                        ) {
                            EnterYourPhoneNumberScreen(
                                navController = navController,
                                enterYourPhoneNumberViewModel
                            )
                        }
                        composable(
                            Screen.VerifyPhoneNumberScreen.route + "/{phoneNumber}",
                            enterTransition = {
                                when (initialState.destination.route) {
                                    Screen.EnterYourPhoneNumberScreen.route -> slideIntoContainer(
                                        AnimatedContentScope.SlideDirection.Left,
                                        animationSpec = tween(700)
                                    )
                                    else -> null
                                }
                            },
                            popExitTransition = {
                                when (targetState.destination.route) {
                                    Screen.EnterYourPhoneNumberScreen.route -> slideOutOfContainer(
                                        AnimatedContentScope.SlideDirection.Right,
                                        animationSpec = tween(700)
                                    )
                                    else -> null
                                }
                            }
                        ) {
                            VerifyPhoneNumberScreen(
                                navController = navController,
                                phoneNumber = it.arguments?.getString("phoneNumber"),
                                verifyPhoneNumberViewModel = verifyPhoneNumberViewModel,
                                sharedPreferences = sharedPreferences,
                                enterYourPhoneNumberViewModel = enterYourPhoneNumberViewModel,
                                sharedEditor = sharedEditor
                            )
                        }
                        composable(Screen.ChatListScreen.route) {
                            ChatListScreen(
                                systemUiController = systemUiController,
                                viewModel = chatListViewModel,
                                navController = navController
                            )
                        }
                        composable(Screen.AddGroupScreen.route) {
                            AddGroupScreen(
                                navController = navController,
                                viewModel = addGroupViewModel
                            )
                        }
                        composable(Screen.ProfileScreen.route) {
                            ProfileScreen(
                                viewModel = profileViewModel,
                                navController = navController,
                                focusManager = focusManager, displayBack = true
                            )
                        }
                        composable(Screen.ViewProfilePicScreen.route, enterTransition = {
                            when (initialState.destination.route) {
                                Screen.ProfileScreen.route -> scaleIn(
                                    animationSpec = tween(500),
                                    initialScale = 0f
                                )
                                else -> null
                            }
                        }, exitTransition = {
                            when (targetState.destination.route) {
                                Screen.ProfileScreen.route -> scaleOut(
                                    animationSpec = tween(500)
                                )
                                else -> null
                            }
                        }) {
                            ViewProfilePicScreen(
                                viewModel = profileViewModel,
                                navController = navController
                            )
                        }
                        composable(Screen.EnterPersonalInfoScreen.route) {
                            EnterPersonalInfoScreen(
                                viewModel = profileViewModel,
                                navController = navController,
                                focusManager = focusManager
                            )
                        }
                        composable(
                            Screen.ChattingRoomScreen.route + "?userId={userId}&userName={userName}&userPicUri={userPicUri}&chatRoomId={chatRoomId}",
                            arguments = listOf(
                                navArgument("userId") {
                                    type = NavType.StringType
                                    defaultValue = ""
                                },
                                navArgument("userName") {
                                    type = NavType.StringType
                                    defaultValue = ""
                                },
                                navArgument("userPicUri") {
                                    type = NavType.StringType
                                    defaultValue = ""
                                },
                                navArgument("chatRoomId") {
                                    type = NavType.StringType
                                    defaultValue = ""
                                })
                        ) {
                            ChattingRoomScreen(
                                configuration = configuration,
                                viewModel = chattingRoomViewModel,
                                navController = navController,
                                userId = it.arguments?.getString("userId")!!,
                                userName = it.arguments?.getString("userName") ?: "",
                                userPicUri = it.arguments?.getString("userPicUri") ?: "",
                                focusManager = focusManager
                            )
                        }
                    }
                }
            }
        }
    }

    private fun isVerified(): String {
        return when {
            sharedPreferences.getString(
                Constants.VERIFICATION_STATUS,
                Constants.NOT_VERIFIED
            ) == Constants.NOT_VERIFIED -> {
                Constants.NOT_VERIFIED
            }
            sharedPreferences.getString(
                Constants.VERIFICATION_STATUS,
                Constants.NOT_VERIFIED
            ) == Constants.VERIFIED -> {
                Constants.VERIFIED
            }
            else -> ""
        }
    }

    private fun changeStatusBarColor() {
        if (!dark) {
            if (isStatusColorPrimary) {
                systemUiController.setStatusBarColor(
                    color = Red500
                )
            } else {
                systemUiController.setStatusBarColor(
                    color = Color.White
                )
            }
        }
    }


}