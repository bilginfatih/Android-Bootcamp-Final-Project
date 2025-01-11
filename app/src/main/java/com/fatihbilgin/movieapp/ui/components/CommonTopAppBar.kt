package com.fatihbilgin.movieapp.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import com.fatihbilgin.movieapp.R
import com.fatihbilgin.movieapp.ui.theme.BackGroundColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommonTopAppBar(
    title: String,
    navController: NavController,
    onBackClick: () -> Unit = { navController.popBackStack() },
    actions: @Composable (RowScope.() -> Unit)? = null,
    backgroundColor: Color = BackGroundColor,
    isTitleCentered: Boolean = true
) {
    TopAppBar(
        title = {
            if (isTitleCentered) {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = title,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
            } else {
                Text(
                    text = title,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
        },
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(
                    painter = painterResource(id = R.drawable.back),
                    contentDescription = "Back",
                    tint = Color.White
                )
            }
        },
        actions = {
            actions?.invoke(this)
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = backgroundColor)
    )
}
