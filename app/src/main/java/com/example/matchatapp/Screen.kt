package com.example.matchatapp

sealed class Screen(val route: String) {
    object FirstWelcomeScreen : Screen("first_welcome_screen")
    object SecondWelcomeScreen : Screen("second_welcome_screen")
    object GetStartedWelcomeScreen : Screen("get_started_welcome_screen")
    object EnterYourPhoneNumberScreen : Screen("enter_your_phone_number_screen")
    object VerifyPhoneNumberScreen : Screen("verify_your_phone_number_screen")
    object ChatListScreen : Screen("chat_list_screen")
    object AddGroupScreen : Screen("add_group_screen")
    object ProfileScreen : Screen("go_to_profile_screen")
    object ViewProfilePicScreen : Screen("view_profile_screen")
    object EnterPersonalInfoScreen : Screen("enter_personal_info_screen")
    object ChattingRoomScreen : Screen("chatting_room_screen")
}