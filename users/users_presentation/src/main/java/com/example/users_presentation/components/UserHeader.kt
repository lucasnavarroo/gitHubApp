package com.example.users_presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.core.LocalSpacing
import com.example.users_presentation.list.model.UserUI
import com.example.githubapp.R

@Composable
fun UserHeader(
    user: UserUI,
    iconSize: Dp = 80.dp,
    titleStyle: TextStyle = MaterialTheme.typography.h6,
    subTitleStyle: TextStyle = MaterialTheme.typography.subtitle1,
    isIconVisible: Boolean = true,
) {
    val spacing = LocalSpacing.current
    val uriHandler = LocalUriHandler.current

    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = user.avatarUrl,
            contentDescription = user.login,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(iconSize)
                .clip(RoundedCornerShape(40.dp))
        )
        Spacer(modifier = Modifier.width(spacing.spaceSmall))
        Column(
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .weight(1f)
        ) {
            Text(
                text = user.login,
                style = titleStyle,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                modifier = Modifier.clickable {
                    uriHandler.openUri(user.profileUrl)
                },
                text = user.profileUrl,
                style = subTitleStyle,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
        if (isIconVisible) {
            Image(
                painter = painterResource(id = R.drawable.baseline_chevron_right_24),
                contentDescription = user.login,
            )
        }
    }
}