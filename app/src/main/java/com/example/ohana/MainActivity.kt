package com.example.ohana

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterStart
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.consumePositionChange
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainMenu()



        }
    }
}

@Preview
@Composable
fun MainMenu() {
    var isRunning by remember { mutableStateOf(false) }
    val logs = listOf("start")

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

        if (isRunning) {
            Console(logs)
        } else {
            RightMenu(scope, drawerState)
        }
        Box(modifier = Modifier.padding(contentPadding), contentAlignment = Alignment.BottomEnd) {
            ValueInputBlock()
            PrintBlock()
        }
    }
}

@Composable
fun PrintBlock() {
    var value by remember { mutableStateOf("") }

    var minWidth by remember { mutableStateOf(10.dp) }
    var offsetX by remember { mutableStateOf(0f) }
    var offsetY by remember { mutableStateOf(0f) }
    var isDragging by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .wrapContentSize()
            .offset { IntOffset(offsetX.roundToInt(), offsetY.roundToInt()) }
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragStart = { isDragging = true },
                    onDragEnd = { isDragging = false },
                    onDrag = { change, dragAmount ->
                        offsetX += dragAmount.x
                        offsetY += dragAmount.y
                        change.consumePositionChange()
                    }
                )
            }
            .background(Color(0xFF8338EC), shape = RoundedCornerShape(8.dp))
            .padding(10.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "Print",
                modifier = Modifier.padding(horizontal = 4.dp),
                color = Color.White,
                fontSize = 20.sp
            )

            BasicTextField(
                value = value,
                onValueChange = { value = it },
                modifier = Modifier
                    .background(Color.White, shape = RoundedCornerShape(8.dp))
                    .padding(vertical = 8.dp, horizontal = 10.dp)
                    .widthIn(min = minWidth)
                    .height(IntrinsicSize.Min)
                    .width(IntrinsicSize.Min)
                    .wrapContentHeight(),
                singleLine = true,
                maxLines = 1,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
            )
        }
    }
}
@Composable
fun ValueInputBlock() {
    var value1 by remember { mutableStateOf("") }
    var value2 by remember { mutableStateOf("") }

    var minWidth by remember { mutableStateOf(10.dp) }
    var offsetX by remember { mutableStateOf(0f) }
    var offsetY by remember { mutableStateOf(0f) }
    var isDragging by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .wrapContentSize()
            .offset { IntOffset(offsetX.roundToInt(), offsetY.roundToInt()) }
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragStart = { isDragging = true },
                    onDragEnd = { isDragging = false },
                    onDrag = { change, dragAmount ->
                        offsetX += dragAmount.x
                        offsetY += dragAmount.y
                        change.consumePositionChange()
                    }
                )
            }
            .background(Color(0xFF2A9D8F), shape = RoundedCornerShape(8.dp))
            .padding(10.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            BasicTextField(
                value = value1,
                onValueChange = { newValue ->
                    value1 = newValue.replace(Regex("[^a-z]"), "")
                },
                modifier = Modifier
                    .background(Color.White, shape = RoundedCornerShape(8.dp))
                    .padding(vertical = 8.dp, horizontal = 10.dp)
                    .height(IntrinsicSize.Min)
                    .width(IntrinsicSize.Min)
                    .widthIn(min = minWidth)
                    .wrapContentHeight(),
                singleLine = true,
                maxLines = 1,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                ),
                visualTransformation = VisualTransformation.None
            )


            Text(
                text = "=",
                modifier = Modifier.padding(horizontal = 4.dp),
                color = Color.White,
                fontSize = 30.sp
            )

            BasicTextField(
                value = value2,
                onValueChange = { value2 = it },
                modifier = Modifier
                    .background(Color.White, shape = RoundedCornerShape(8.dp))
                    .padding(vertical = 8.dp, horizontal = 10.dp)
                    .widthIn(min = minWidth)
                    .height(IntrinsicSize.Min)
                    .width(IntrinsicSize.Min)
                    .wrapContentHeight(),
                singleLine = true,
                maxLines = 1,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
        }
    }
}
@Composable
fun RightMenu(scope: CoroutineScope, drawerState: DrawerState) {
    val isControllersDropdownOpen = remember { mutableStateOf(false) }
    val isOperatorsDropdownOpen = remember { mutableStateOf(false) }
    val isVariablesDropdownOpen = remember { mutableStateOf(false) }
    val isEventsDropdownOpen = remember { mutableStateOf(false) }
    val isEventssDropdownOpen = remember { mutableStateOf(false) }

    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        ModalNavigationDrawer(
            scrimColor = Color.Transparent,
            drawerState = drawerState,
            drawerContent = {
                ModalDrawerSheet(
                    drawerContainerColor = Color(0xFF4A4744),
                    modifier = Modifier.fillMaxWidth(0.6f)
                ) {
                    LazyColumn(
                        verticalArrangement = Arrangement.Top,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp, bottom = 16.dp)
                    ) {
                        item {
                            MenuItem(
                                text = "Controllers",
                                onClick = { isControllersDropdownOpen.value = !isControllersDropdownOpen.value },
                                menuItemBackgroundColor = if (isControllersDropdownOpen.value) Color(0xFF4F4C49) else Color.Transparent,
                                isNested = false
                            )
                        }

                        if (isControllersDropdownOpen.value) {
                            item {
                                MenuItem("1", onClick = {})
                            }
                            item { MenuItem("2", onClick = {}) }
                        }
                        item {
                            MenuItem(
                                text = "Operators",
                                onClick = { isOperatorsDropdownOpen.value = !isOperatorsDropdownOpen.value },
                                menuItemBackgroundColor = if (isOperatorsDropdownOpen.value) Color(0xFF4F4C49) else Color.Transparent,
                                isNested = false
                            )
                        }
                        if (isOperatorsDropdownOpen.value) {
                            item { MenuItem("1", onClick = {}) }
                            item { MenuItem("2", onClick = {}) }
                        }
                        item {
                            MenuItem(
                                text = "Variables",
                                onClick = { isVariablesDropdownOpen.value = !isVariablesDropdownOpen.value },
                                menuItemBackgroundColor = if (isVariablesDropdownOpen.value) Color(0xFF4F4C49) else Color.Transparent,
                                isNested = false
                            )
                        }
                        if (isVariablesDropdownOpen.value) {
                            item { MenuItem("1", onClick = {}) }
                            item { MenuItem("2", onClick = {}) }
                        }
                        item {
                            MenuItem(
                                text = "Events",
                                onClick = { isEventsDropdownOpen.value = !isEventsDropdownOpen.value },
                                menuItemBackgroundColor = if (isEventsDropdownOpen.value) Color(0xFF4F4C49) else Color.Transparent,
                                isNested = false
                            )
                        }
                        if (isEventsDropdownOpen.value) {
                            item { MenuItem("1", onClick = {}) }
                            item { MenuItem("2", onClick = {}) }
                        }
                        item {
                            MenuItem(
                                text = "Eventss",
                                onClick = { isEventssDropdownOpen.value = !isEventssDropdownOpen.value },
                                menuItemBackgroundColor = if (isEventssDropdownOpen.value) Color(0xFF4F4C49) else Color.Transparent,
                                isNested = false
                            )
                        }
                        if (isEventssDropdownOpen.value) {
                            item { MenuItem("1", onClick = {}) }
                            item { MenuItem("2", onClick = {}) }
                        }
                    }
                }
            },
            content = {
                Column(
                    horizontalAlignment = Alignment.Start
                ) {
                    OpenMenuButton(scope, drawerState)
                }
            }
        )
    }
}

@Composable
fun MenuItem(
    text: String,
    onClick: () -> Unit,
    menuItemBackgroundColor: Color = Color.Transparent,
    isNested: Boolean = false
) {
    val horizontalPadding = if (isNested) 16.dp else 8.dp

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(menuItemBackgroundColor)
            .clickable(onClick = onClick)
            .padding(horizontal = horizontalPadding, vertical = 16.dp)
    ) {
        Text(
            text = text,
            fontSize = 20.sp,
            color = Color.White
        )
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
fun MyCanvas() {
    Canvas(modifier = Modifier.fillMaxSize()) {
        drawCircle(
            color = Color.Blue,
            radius = 100f,
            center = Offset(size.width / 2, size.height / 2),
            style = Stroke(width = 4f)
        )
    }
}


@Composable
fun Console(logs: List<String>) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Bottom
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .background(Color(red = 74, green = 71, blue = 73, alpha = 255))
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(20.dp)
                    .background(Color(red = 74, green = 71, blue = 68, alpha = 255))
            ) {
                Text(
                    "     console output",
                    color = Color(red = 179, green = 179, blue = 179, alpha = 255)
                )
            }
            LazyColumn(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .fillMaxSize()
            ) {
                item {
                    logs.forEach() {
                        Text(
                            "    >> " + it,
                            color = Color(red = 179, green = 179, blue = 179, alpha = 255)
                        )
                    }
                }
            }
        }
    }
}
