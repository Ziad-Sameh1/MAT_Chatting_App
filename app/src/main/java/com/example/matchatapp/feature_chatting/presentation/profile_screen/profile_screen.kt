package com.example.matchatapp.feature_chatting.presentation.profile_screen

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.example.matchatapp.R
import com.example.matchatapp.Screen
import com.example.matchatapp.feature_firsttimeopen.presentation.components.CommonBackButton
import com.example.matchatapp.feature_firsttimeopen.presentation.enter_personal_info.EnterPersonalInfoAlertDialog
import com.example.matchatapp.ui.theme.CardGray
import com.example.matchatapp.utils.Constants.TAG

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel,
    navController: NavController,
    focusManager: FocusManager, displayBack: Boolean
) {
    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) {
            // Update Local Image Uri
            viewModel.onLocalProfileImageUriChanges(it)
            viewModel.onIsProfilePicLoadingStateChanges(newValue = true)
            viewModel.getCloudProfileImageUri(it)
            // show image loading bar
        }
    Box() {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit) { detectTapGestures { focusManager.clearFocus() } }) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = if (isSystemInDarkTheme()) CardGray else MaterialTheme.colors.primary),
                verticalAlignment = Alignment.CenterVertically
            ) {
                CommonBackButton(
                    navController = navController,
                    tint = if (displayBack) MaterialTheme.colors.onPrimary else MaterialTheme.colors.onPrimary.copy(
                        alpha = 0f
                    )
                )
                Text(
                    text = stringResource(id = R.string.edit_profile),
                    color = MaterialTheme.colors.onPrimary,
                    style = MaterialTheme.typography.h6.copy(letterSpacing = 1.sp),
                    modifier = Modifier.weight(5f)
                )
                Text(
                    text = stringResource(id = R.string.edit),
                    color = if (!viewModel.isEditable.value) MaterialTheme.colors.onPrimary else MaterialTheme.colors.onPrimary.copy(
                        alpha = 0.5f
                    ),
                    style = MaterialTheme.typography.h6.copy(letterSpacing = 2.sp),
                    modifier = Modifier
                        .clickable {
                            if (!viewModel.isEditable.value) {
                                viewModel.onEditableStateChanges(true)
                            }
                        }
                        .weight(1f)
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .size(height = 180.dp, width = 500.dp)
                    .padding(top = 30.dp)
                    .clickable {
                        if (viewModel.isEditable.value) {
                            // TODO: Access Photos From Your Phone
                            launcher.launch("image/*")
                            /** This Launcher just opens the gallery and saved our selected image to our viewModel*/
                        } else {
                            // TODO: View Maximized Profile Pic
                            navController.navigate(Screen.ViewProfilePicScreen.route) {
                                popUpTo(Screen.ProfileScreen.route)
                            }
                        }
                    }
            ) {
                Image(
                    painter =
                    if (viewModel.cloudProfileImageUri.value != null) rememberImagePainter(
                        data = Uri.parse(viewModel.cloudProfileImageUri.value),
                        builder = {
                            transformations(CircleCropTransformation())
                            crossfade(true)
                            fallback(R.drawable.ic_default_user_profile_image_svg)
                            placeholder(R.drawable.ic_image_svgrepo_com)
                            error(R.drawable.ic_default_user_profile_image_svg)
                        }) else
                        painterResource(id = R.drawable.ic_default_user_profile_image_svg),
                    contentDescription = "Your Profile Picture",
                    modifier = Modifier
                        .clip(RoundedCornerShape(100.dp))
                        .align(
                            Alignment.Center
                        ), alpha = if (viewModel.isEditable.value) 0.2f else 1f
                )
                Image(
                    painter = painterResource(id = R.drawable.ic_edit_photo),
                    contentDescription = "Edit Your Profile Picture",
                    modifier = Modifier
                        .align(
                            Alignment.Center
                        )
                        .size(50.dp),
                    colorFilter = ColorFilter.tint(color = Color.White.copy(alpha = 0.5f)),
                    alpha = if (viewModel.isEditable.value && !viewModel.isProfilePicLoadingState.value) 1f else 0f
                )
                if (viewModel.isProfilePicLoadingState.value) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }
            Spacer(modifier = Modifier.height(50.dp))
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 70.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OutlinedTextField(
                    value = viewModel.userName.value,
                    onValueChange = { newValue -> viewModel.onUserNameChanges(newValue = newValue) },
                    label = {
                        Text(
                            text = stringResource(id = R.string.user_name),
                            color = if (viewModel.isEditable.value) MaterialTheme.colors.primary else MaterialTheme.colors.primary.copy(
                                alpha = 0.5f
                            )
                        )
                    },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = MaterialTheme.colors.primary,
                        focusedLabelColor = MaterialTheme.colors.primary,
                        cursorColor = MaterialTheme.colors.primary,
                        unfocusedBorderColor = MaterialTheme.colors.primary,
                        textColor = MaterialTheme.colors.primary
                    ),
                    textStyle = MaterialTheme.typography.body1.copy(letterSpacing = 1.sp),
                    enabled = viewModel.isEditable.value, maxLines = 1
                )
                Spacer(modifier = Modifier.height(30.dp))
                OutlinedTextField(
                    value = viewModel.userDescription.value,
                    onValueChange = { newValue -> viewModel.onUserDescriptionChanges(newValue = newValue) },
                    label = {
                        Text(
                            text = stringResource(id = R.string.description),
                            color = if (viewModel.isEditable.value) MaterialTheme.colors.primary else MaterialTheme.colors.primary.copy(
                                alpha = 0.5f
                            )
                        )
                    },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = MaterialTheme.colors.primary,
                        focusedLabelColor = MaterialTheme.colors.primary,
                        cursorColor = MaterialTheme.colors.primary,
                        unfocusedBorderColor = MaterialTheme.colors.primary,
                        textColor = MaterialTheme.colors.primary
                    ),
                    textStyle = MaterialTheme.typography.body1.copy(letterSpacing = 1.sp),
                    enabled = viewModel.isEditable.value, maxLines = 1
                )
                Spacer(modifier = Modifier.height(30.dp))
                OutlinedTextField(
                    value = viewModel.userPhoneNumber.value,
                    onValueChange = { /** Nothing to do here */ },
                    label = {
                        Text(
                            text = stringResource(id = R.string.phone_number),
                            color = MaterialTheme.colors.primary.copy(alpha = 0.5f)
                        )
                    },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = MaterialTheme.colors.primary,
                        focusedLabelColor = MaterialTheme.colors.primary,
                        cursorColor = MaterialTheme.colors.primary,
                        unfocusedBorderColor = MaterialTheme.colors.primary,
                        textColor = MaterialTheme.colors.primary
                    ),
                    textStyle = MaterialTheme.typography.body1.copy(letterSpacing = 1.sp),
                    enabled = false, maxLines = 1
                )
                Spacer(modifier = Modifier.height(50.dp))
                Button(
                    onClick = {
                        /*TODO: Save the New Info to the Database */
                        if (viewModel.verifyForm()) {
                            viewModel.onIsLoadingStateChanges(true) // show loading
                            viewModel.onAddOrUpdateUserData() // add data to database
                        }
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary),
                    modifier = Modifier
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    enabled = viewModel.isEditable.value
                ) {
                    Text(
                        text = stringResource(id = R.string.save),
                        style = MaterialTheme.typography.h6.copy(letterSpacing = 2.sp),
                        modifier = Modifier.padding(vertical = 6.dp)
                    )
                }
            }
        }
        if (viewModel.isErrorState.value) {
            EnterPersonalInfoAlertDialog(viewModel = viewModel)
        }
        if (viewModel.isSavedState.value) {
            viewModel.onEditableStateChanges(newValue = false)
            viewModel.onIsSavedStateChanges(newValue = false)
        }
        if (viewModel.isLoadingState.value) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
    }
}