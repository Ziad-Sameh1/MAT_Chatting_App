package com.example.matchatapp.feature_chatting.presentation.view_profile_pic_screen

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import coil.size.Precision
import coil.size.Scale
import coil.transform.CircleCropTransformation
import com.example.matchatapp.R
import com.example.matchatapp.feature_chatting.presentation.profile_screen.ProfileViewModel
import com.example.matchatapp.feature_firsttimeopen.presentation.components.CommonBackButton
import com.example.matchatapp.ui.theme.CardGray

@Composable
fun ViewProfilePicScreen(viewModel: ProfileViewModel, navController: NavController) {
    Column() {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = if (isSystemInDarkTheme()) CardGray else MaterialTheme.colors.primary),
            verticalAlignment = Alignment.CenterVertically
        ) {
            CommonBackButton(
                navController = navController,
                tint = MaterialTheme.colors.onPrimary,
            )
            Text(
                text = viewModel.userName.value,
                color = MaterialTheme.colors.onPrimary,
                style = MaterialTheme.typography.h6.copy(letterSpacing = 1.sp),
            )
        }
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter =
                if (viewModel.cloudProfileImageUri.value != null) rememberImagePainter(
                    data = Uri.parse(viewModel.cloudProfileImageUri.value),
                    builder = {
                        crossfade(true)
                        fallback(R.drawable.ic_default_user_profile_image_svg)
                        placeholder(R.drawable.ic_image_svgrepo_com)
                        error(R.drawable.ic_default_user_profile_image_svg)
                    }) else
                    painterResource(id = R.drawable.ic_default_user_profile_image_svg),
                contentDescription = "Your Profile Picture",
                modifier = Modifier
                    .size(400.dp),
                contentScale = ContentScale.Crop,
            )
        }
    }
}