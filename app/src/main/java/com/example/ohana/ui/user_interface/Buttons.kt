package com.example.ohana.ui.user_interface

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DrawerState
import androidx.compose.material3.FloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import com.example.ohana.R
import com.example.ohana.ui.theme.runButton
import com.example.ohana.ui.theme.stopButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun RunButton(onClick: () -> Unit) {
    FloatingActionButton(
        onClick = { onClick() },
        containerColor = runButton,
        shape = CircleShape
    ) {
        Image(
            painter = painterResource(id = R.drawable.run),
            contentDescription = "Run button"
        )
    }
}

@Composable
fun StopButton(onClick: () -> Unit) {
    FloatingActionButton(
        onClick = { onClick() },
        containerColor = stopButton,
        shape = CircleShape
    ) {
        Image(
            painter = painterResource(id = R.drawable.stop),
            contentDescription = "Run button"
        )
    }
}

@Composable
fun OpenMenuButton(
    scope: CoroutineScope,
    drawerState: DrawerState
) {
    Button(
        onClick = { scope.launch { drawerState.open() } },
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            contentColor = Color.Transparent,
            disabledContainerColor = Color.Transparent,
            disabledContentColor = Color.Transparent
        ),
        modifier = Modifier
            .wrapContentWidth()
            .fillMaxHeight(),

        shape = RectangleShape

    ) {
        Image(
            painterResource(id = R.drawable.open_interface),
            contentDescription = "Open menu button"
        )
    }
}