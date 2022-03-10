package com.example.matchatapp.feature_chatting.presentation.add_group_screen

import android.Manifest
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.matchatapp.R
import com.example.matchatapp.feature_chatting.presentation.add_group_screen.components.UsersListComposable
import com.example.matchatapp.feature_chatting.presentation.chatting_room.ChattingRoomViewModel
import com.example.matchatapp.feature_firsttimeopen.presentation.components.CommonBackButton
import com.example.matchatapp.ui.theme.CardGray
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun AddGroupScreen(
    navController: NavController,
    viewModel: AddGroupViewModel
) {
    val contactsPermissionState =
        rememberPermissionState(permission = Manifest.permission.READ_CONTACTS)

    var flag = rememberSaveable { mutableStateOf(true) }
    Box(modifier = Modifier.fillMaxSize()) {
        Column() {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = if (isSystemInDarkTheme()) CardGray else MaterialTheme.colors.primary),
                verticalAlignment = Alignment.CenterVertically
            ) {
                CommonBackButton(
                    navController = navController,
                    tint = MaterialTheme.colors.onPrimary
                )
                Text(
                    text = stringResource(id = R.string.add_new_chat_room),
                    color = MaterialTheme.colors.onPrimary,
                    style = MaterialTheme.typography.h6.copy(letterSpacing = 1.sp)
                )
            }
            if (contactsPermissionState.status.isGranted) {
                viewModel.onIsLoadingStateChanges(true)
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 30.dp)
                ) {
                    // TODO: Get List of contacts
                    if (flag.value) {
                        viewModel.getContacts()
                        flag.value = false
                    }
                    // TODO: Display List of Contacts
                    UsersListComposable(
                        viewModel = viewModel,
                        navController = navController
                    )
                }
                viewModel.onIsLoadingStateChanges(false)
            } else {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    if (contactsPermissionState.status.shouldShowRationale) {
                        Column() {
                            Text(
                                text = stringResource(id = R.string.contacts_show_rationale),
                                textAlign = TextAlign.Center,
                                color = MaterialTheme.colors.onSecondary,
                                style = MaterialTheme.typography.body1
                            )
                            Button(onClick = { contactsPermissionState.launchPermissionRequest() }) {
                                Text(text = stringResource(id = R.string.grant_the_permission))
                                if (contactsPermissionState.status.isGranted) {
                                    viewModel.getAllContacts()
                                }
                            }
                        }
                    } else {
                        Column() {
                            Text(
                                text = stringResource(id = R.string.no_contacts_available),
                                textAlign = TextAlign.Center,
                                color = MaterialTheme.colors.onSecondary,
                                style = MaterialTheme.typography.body1
                            )
                            Button(onClick = { contactsPermissionState.launchPermissionRequest() }) {
                                Text(text = stringResource(id = R.string.grant_the_permission))
                                if (contactsPermissionState.status.isGranted) {
                                    viewModel.getAllContacts()
                                    viewModel.onIsLoadingStateChanges(false)
                                }
                            }
                        }
                    }

                }
            }
        }
        if (viewModel.isLoadingState.value || viewModel.creatingRoomLoadingState.value) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
    }
}