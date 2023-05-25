package com.example.ohana

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
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
import org.burnoutcrew.reorderable.reorderable
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.graphicsLayer
import androidx.lifecycle.viewmodel.compose.viewModel
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
    val blocks = remember { mutableStateListOf<String>() }

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

        Box(modifier = Modifier.padding(contentPadding)) {
            RightMenu(scope = scope, drawerState = drawerState, blocks = blocks)

            if (isRunning) {
                Console(logs)
            }
        }
    }
}


@Composable
fun PrintBlock() {
    var value by remember { mutableStateOf("") }
    var minWidth by remember { mutableStateOf(10.dp) }
    var isDraggingBlock by remember { mutableStateOf(false) }
    var offsetX by remember { mutableStateOf(0f) }
    var offsetY by remember { mutableStateOf(0f) }

    Box(
//        modifier = Modifier
//            .offset {
//                IntOffset(
//                    offsetX.roundToInt(),
//                    offsetY.roundToInt()
//                )
//            }
//            .pointerInput(Unit) {
//                detectDragGestures(
//                    onDragStart = { isDraggingBlock = true },
//                    onDragEnd = { isDraggingBlock = false },
//                    onDrag = { _, dragAmount ->
//                        offsetX += dragAmount.x
//                        offsetY += dragAmount.y
//                    }
//                )
//            }
    ) {
        Box(
            modifier = Modifier
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

                Box(
                    modifier = Modifier
                        .background(Color.White, shape = RoundedCornerShape(8.dp))
                        .padding(vertical = 8.dp, horizontal = 10.dp)
                        .widthIn(min = minWidth)
                        .height(IntrinsicSize.Min)
                        .width(IntrinsicSize.Min)
                        .wrapContentHeight()

                ) {
                    BasicTextField(
                        value = value,
                        onValueChange = { value = it },
                        modifier = Modifier.fillMaxSize(),
                        singleLine = true,
                        maxLines = 1,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                    )
                }
            }
        }
    }
}

@Composable
fun InputBlock() {
    var value by remember { mutableStateOf("") }
    var minWidth by remember { mutableStateOf(10.dp) }
    var isDraggingBlock by remember { mutableStateOf(false) }
    var offsetX by remember { mutableStateOf(0f) }
    var offsetY by remember { mutableStateOf(0f) }

    Box(
        modifier = Modifier
            .background(Color(0xFF8338EC), shape = RoundedCornerShape(8.dp))
            .padding(10.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "Input",
                modifier = Modifier.padding(horizontal = 4.dp),
                color = Color.White,
                fontSize = 20.sp
            )

            Box(
                modifier = Modifier
                    .background(Color.White, shape = RoundedCornerShape(8.dp))
                    .padding(vertical = 8.dp, horizontal = 10.dp)
                    .widthIn(min = minWidth)
                    .height(IntrinsicSize.Min)
                    .width(IntrinsicSize.Min)
                    .wrapContentHeight()
                    .offset {
                        IntOffset(
                            offsetX.roundToInt(),
                            offsetY.roundToInt()
                        )
                    }
                    .pointerInput(Unit) {
                        detectDragGestures(
                            onDragStart = { isDraggingBlock = true },
                            onDragEnd = { isDraggingBlock = false },
                            onDrag = { _, dragAmount ->
                                offsetX += dragAmount.x
                                offsetY += dragAmount.y
                            }
                        )
                    }
            ) {
                BasicTextField(
                    value = value,
                    onValueChange = { value = it },
                    modifier = Modifier.fillMaxSize(),
                    singleLine = true,
                    maxLines = 1,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                )
            }
        }
    }
}
@Composable
fun ValueInputBlock() {
    var value1 by remember { mutableStateOf("") }
    var value2 by remember { mutableStateOf("") }
    var minWidth by remember { mutableStateOf(10.dp) }
    var isDragging by remember { mutableStateOf(false) }
    var offsetX by remember { mutableStateOf(0f) }
    var offsetY by remember { mutableStateOf(0f) }

    Box(
        modifier = Modifier
            .wrapContentSize()
            .offset { IntOffset(offsetX.roundToInt(), offsetY.roundToInt()) }
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragStart = { isDragging = true },
                    onDragEnd = { isDragging = false },
                    onDrag = { _, dragAmount ->
                        offsetX += dragAmount.x
                        offsetY += dragAmount.y
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
                    .wrapContentHeight()
                    .pointerInput(Unit) {
                        detectDragGestures(
                            onDragStart = { isDragging = true },
                            onDragEnd = { isDragging = false },
                            onDrag = { _, dragAmount ->
                                offsetX += dragAmount.x
                                offsetY += dragAmount.y
                            }
                        )
                    },
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
                    .wrapContentHeight()
                    .pointerInput(Unit) {
                        detectDragGestures(
                            onDragStart = { isDragging = true },
                            onDragEnd = { isDragging = false },
                            onDrag = { _, dragAmount ->
                                offsetX += dragAmount.x
                                offsetY += dragAmount.y
                            }
                        )
                    },
                singleLine = true,
                maxLines = 1,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
        }
    }
}

@Composable
fun RightMenu(
    scope: CoroutineScope,
    drawerState: DrawerState,
    blocks: MutableList<String>
) {
    val isControllersDropdownOpen = remember { mutableStateOf(false) }
    val isStreamsDropdownOpen = remember { mutableStateOf(false) }
    val isVariablesDropdownOpen = remember { mutableStateOf(false) }


    // Установка обратного направления чтения для drawerContent
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        ModalNavigationDrawer(
            scrimColor = Color.Transparent,
            drawerState = drawerState,
            drawerContent = {
                // Установка обычного направления чтения для контента drawerContent
                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
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
                                    text = "CONTROLLERS",
                                    onClick = {
                                        isControllersDropdownOpen.value =
                                            !isControllersDropdownOpen.value
                                    },
                                    menuItemBackgroundColor = if (isControllersDropdownOpen.value) Color(
                                        0xFF4F4C49
                                    ) else Color.Transparent,
                                    isNested = false
                                )
                            }

                            if (isControllersDropdownOpen.value) {
                                item { MenuItem(text = "if", onClick = {}) }
                                item { MenuItem(text = "if-else", onClick = {}) }
                                item { MenuItem(text = "while", onClick = {}) }
                                item { MenuItem(text = "repeat", onClick = {}) }
                            }
                            item {
                                MenuItem(
                                    text = "STREAMS",
                                    onClick = {
                                        isStreamsDropdownOpen.value = !isStreamsDropdownOpen.value
                                    },
                                    menuItemBackgroundColor = if (isStreamsDropdownOpen.value) Color(
                                        0xFF4F4C49
                                    ) else Color.Transparent,
                                    isNested = false
                                )
                            }
                            if (isStreamsDropdownOpen.value) {
                                item {
                                    MenuItem(
                                        text = "print",
                                        onClick = { blocks.add("print") })
                                }
                                item {
                                    MenuItem(
                                        text = "input",
                                        onClick = { blocks.add("input") })
                                }
                            }
                            item {
                                MenuItem(
                                    text = "VARIABLES",
                                    onClick = {
                                        isVariablesDropdownOpen.value =
                                            !isVariablesDropdownOpen.value
                                    },
                                    menuItemBackgroundColor = if (isVariablesDropdownOpen.value) Color(
                                        0xFF4F4C49
                                    ) else Color.Transparent,
                                    isNested = false
                                )
                            }
                            if (isVariablesDropdownOpen.value) {
                                item {
                                    MenuItem(
                                        text = "set variable",
                                        onClick = { blocks.add("variable") })
                                }
                            }
                            item { MenuItem(text = "CLEAR", onClick = { blocks.clear() }) }
                        }
                    }
                }
            },
            content = {
                // Установка обычного направления чтения для content
                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {

                    MyList(blocks = blocks) { newBlocks ->
                        blocks.clear()
                        blocks.addAll(newBlocks)
                    }
                    OpenMenuButton(scope, drawerState)
                }
            }
        )
    }
}

@Composable
fun MyList(blocks: List<String>, onBlocksUpdate: (List<String>) -> Unit) {
    val state = rememberLazyListState()
    LazyColumn(state = state,
            modifier = Modifier.fillMaxSize(1f)) {
        itemsIndexed(blocks) { index, block ->
            var isDragging by remember { mutableStateOf(false) }

            if (isDragging) {
                Box(Modifier.graphicsLayer { /* apply transformations and effects */ })
            }

            val modifier = Modifier
                .draggable(
                    state = rememberDraggableState { delta ->
                        isDragging = true
                    },
                    orientation = Orientation.Vertical,
                    onDragStopped = {
                        isDragging = false
                    }
                )
                .pointerInput(Unit) {
                    detectTapGestures(
                        onTap = { /* handle tap */ },
                        onLongPress = { isDragging = true }
                    )
                }

            when (block) {
                "print" -> PrintBlock()
                "input" -> InputBlock()
                "variable" -> ValueInputBlock()
            }

            if (isDragging) {
                val newIndex = state.layoutInfo.visibleItemsInfo
                    .indexOfFirst { it.index != -1 && it.offset <= state.layoutInfo.viewportEndOffset }
                    .takeIf { it != -1 } ?: return@itemsIndexed

                if (newIndex != index) {
                    val newBlocks = blocks.toMutableList()
                    newBlocks.removeAt(index)
                    newBlocks.add(newIndex, block)
                    onBlocksUpdate(newBlocks)
                }
            }
        }
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



@Composable
fun Console(logs: List<String>) {
    Column(
        modifier = Modifier
            .background(Color.Transparent)
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