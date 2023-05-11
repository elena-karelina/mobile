package com.example.ohana

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ohana.ui.theme.BlockHolder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

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
    var isRunning by remember { mutableStateOf(false) }
    val holder = BlockHolder()

    Scaffold(
        containerColor = Color(red = 64, green = 61, blue = 57, alpha = 255),
        floatingActionButton = {
            if (!isRunning) {
                RunButton({ isRunning = !isRunning })
            } else {
                StopButton({ isRunning = !isRunning })
            }
        },
        floatingActionButtonPosition = FabPosition.End
    ) { contentPadding ->
        val drawerState = rememberDrawerState(DrawerValue.Closed)
        val scope = rememberCoroutineScope()

        RightMenu(scope, drawerState)
        if (isRunning) {
            consoleLogs()
        }
        holder.blocks.forEach {

        }
        Box(modifier = Modifier.padding(contentPadding), contentAlignment = Alignment.BottomEnd) { }
    }
}

@Composable
fun RunButton(onClick: () -> Unit) {
    FloatingActionButton(
        onClick = { onClick() },
        containerColor = Color(red = 42, green = 157, blue = 143, alpha = 255),
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
        containerColor = Color(red = 230, green = 57, blue = 70, alpha = 255),
        shape = CircleShape
    ) {
        Image(
            painter = painterResource(id = R.drawable.stop),
            contentDescription = "Run button"
        )
    }
}

@Composable
fun consoleLogs() {
    Column(modifier = Modifier.fillMaxHeight(), verticalArrangement = Arrangement.Bottom) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .background(Color(red = 79, green = 76, blue = 73, alpha = 255))
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(20.dp)
                    .background(Color(red = 74, green = 71, blue = 68, alpha = 255))
            ) {
                Text(
                    "       console output",
                    color = Color(red = 179, green = 179, blue = 179, alpha = 255)
                )
            }
            LazyColumn(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .fillMaxSize()
            ) {
                items(count = 100) {
                    Text(
                        "       >>",
                        color = Color(red = 179, green = 179, blue = 179, alpha = 255)
                    )

                }
            }
        }
    }
}

@Composable
fun RightMenu(scope: CoroutineScope, drawerState: DrawerState) {
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        ModalNavigationDrawer(
            scrimColor = Color.Transparent,
            drawerState = drawerState,
            drawerContent = {
                ModalDrawerSheet(
                    drawerContainerColor = Color(
                        red = 74,
                        green = 71,
                        blue = 68,
                        alpha = 255
                    ),
                    modifier = Modifier.width(300.dp)
                ) {
                    MenuItem("Controllers", { CreateBlock() })
                    MenuItem("Operators", { CreateBlock() })
                    MenuItem("Variables", { CreateBlock() })
                    MenuItem("Events", { CreateBlock() })
                }
            },
            content = {
                Column(
                    horizontalAlignment = Alignment.Start
                ) {
                    OpenMenuButton(scope, drawerState)
                }
                CreateBlock()
            }
        )
    }
}

@Composable
fun MenuItem(text: String, onClick: @Composable () -> Unit) {
    val flag = remember { mutableStateOf(false) }
    NavigationDrawerItem(
        label = { Text(text, fontSize = 20.sp) },
        selected = false,
        onClick = { flag.value = true },
        shape = RectangleShape
    )
    if (flag.value) {
        onClick()
    }
}

@Composable
fun OpenMenuButton(scope: CoroutineScope, drawerState: DrawerState) {
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

@Composable
fun CreateBlock() {
    var offsetX by remember { mutableStateOf(450f) }
    var offsetY by remember { mutableStateOf(1100f) }

    Box(
        Modifier
            .offset { IntOffset(offsetX.roundToInt(), offsetY.roundToInt()) }
            .background(Color.Red)
            .size(50.dp)
            .pointerInput(Unit) {
                detectDragGestures { change, dragAmount ->
                    change.consume()
                    offsetX -= dragAmount.x // Маленький костыль, который вызван тем, что блок рисуется внутри content modelDrawer, который зеркально отражен, из-за этого += инвертировало движение по оси Ox
                    offsetY += dragAmount.y
                }
            }
    )
}
