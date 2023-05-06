package com.example.ohana

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.checkScrollableContainerConstraints
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonDefaults.buttonColors
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawOutline
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.navigation.compose.rememberNavController
import com.example.ohana.ui.theme.OhanaTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            mainMenu()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun mainMenu() {
    var showLogs by remember { mutableStateOf(false) }

    Scaffold(
        containerColor = Color(red = 64, green = 61, blue = 57, alpha = 255),
        floatingActionButton = {
            if (!showLogs) {
                FloatingActionButton(
                    onClick = { showLogs = !showLogs },
                    containerColor = Color(red = 42, green = 157, blue = 143, alpha = 255),
                    shape = CircleShape
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.run),
                        contentDescription = "Run button"
                    )
                }
            }
            else {
                FloatingActionButton(
                    onClick = { showLogs = !showLogs },
                    containerColor = Color(red = 230, green = 57, blue = 70, alpha = 255),
                    shape = CircleShape
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.stop),
                        contentDescription = "Run button"
                    )
                }
            }
        },
        floatingActionButtonPosition = FabPosition.End
    ) { contentPadding ->
        val drawerState = rememberDrawerState(DrawerValue.Closed)
        val scope = rememberCoroutineScope()
        if (showLogs) {
            consoleLogs()
        } else {
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                ModalNavigationDrawer(
                    scrimColor = Color.Transparent,
                    drawerState = drawerState,
                    drawerContent = {
                        ModalDrawerSheet( // Контент самого меню
                            drawerContainerColor = Color(
                                red = 74,
                                green = 71,
                                blue = 68,
                                alpha = 255
                            ),
                            modifier = Modifier.width(300.dp)
                        ) {
                            NavigationDrawerItem(
                                label = { Text("Controllers", fontSize = 20.sp) },
                                selected = false,
                                onClick = { },
                                shape = RectangleShape
                            )
                        }
                    },
                    content = {
                        Column(
                            horizontalAlignment = Alignment.Start
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
                                    .fillMaxHeight(),
                                shape = RectangleShape

                            ) {
                                Image(
                                    painterResource(id = R.drawable.open_interface),
                                    contentDescription = "Open menu button"
                                )
                            }
                        }
                    }
                )
            }
        }


        Box(modifier = Modifier.padding(contentPadding), contentAlignment = Alignment.BottomEnd) {  }
    }
}

@Composable
fun consoleLogs() {
    Column (modifier = Modifier.fillMaxHeight(), verticalArrangement = Arrangement.Bottom){
        Column (
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .background(Color(red = 79, green = 76, blue = 73, alpha = 255))
        ) {
            Row  (
                modifier = Modifier
                    .fillMaxWidth()
                    .height(20.dp)
                    .background(Color(red = 74, green = 71, blue = 68, alpha = 255))
            ){
                Text("       console output", color = Color(red = 179, green = 179, blue = 179, alpha = 255))
            }
            Row {
                Text("       >>", color = Color(red = 179, green = 179, blue = 179, alpha = 255))
            }
        }
    }
}