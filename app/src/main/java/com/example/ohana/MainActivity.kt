package com.example.ohana

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.IntOffset
import com.example.ohana.*
import com.example.ohana.logic.Block
import com.example.ohana.logic.Interpretator
import com.example.ohana.logic.PrintBlock
import com.example.ohana.logic.Scope
import com.example.ohana.logic.SetVariableBlock
import com.example.ohana.ui.blocks.*
import com.example.ohana.ui.user_interface.*

import android.graphics.Canvas
import android.graphics.Point
import android.view.View
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import androidx.core.content.res.ResourcesCompat
import org.burnoutcrew.reorderable.ReorderableItem
import org.burnoutcrew.reorderable.detectReorderAfterLongPress
import org.burnoutcrew.reorderable.rememberReorderableLazyListState
import org.burnoutcrew.reorderable.reorderable

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainMenu()
        }
    }
}

@Composable
fun MainMenu() {
    var isRunning by remember { mutableStateOf(false) }
    var logs = remember { mutableStateListOf<String>() }
    val blocks = remember { mutableStateListOf<Block>() }

    Scaffold(
        containerColor = Color(red = 64, green = 61, blue = 57, alpha = 255),
        floatingActionButton = {
            if (!isRunning) {
                RunButton {
                    isRunning = !isRunning
                    val int = Interpretator(Scope())
                    int.program._body = blocks
                    int.launch()
                    logs = int.scope.logs
                }
            } else {
                StopButton{
                    isRunning = !isRunning
                }
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


// Отрисовка всех блоков
@Composable
fun BlocksDrawer(blocks: MutableList<Block>) {
    val state = rememberReorderableLazyListState(onMove = { from, to ->
        blocks.apply {
            add(to.index, removeAt(from.index))
        }
    })
            LazyColumn(
                state = state.listState,
                modifier = Modifier
                    .fillMaxSize()
                    .reorderable(state)
                    .detectReorderAfterLongPress(state)) {
                itemsIndexed(blocks) { index, block ->
                    ReorderableItem(state, key = block) { isDragging ->
                        val elevation = animateDpAsState(if (isDragging) 16.dp else 0.dp)
                        Column(
                            modifier = Modifier
                                .shadow(elevation.value)) {
                            print(index)
                            when (block) {
                                is PrintBlock -> PrintBlock(
                                    block = block, index
                                )
                                is SetVariableBlock -> SetVariableBlock(
                                    block = block
                                )
                    }
                        }
                    }
                }
//                itemsIndexed(blocks) { index, block ->
//                    val draggableModifier = Modifier
//                        .pointerInput(Unit) {
//                            detectDragGestures(
//                                onDragStart = { offset ->
//                                    block.startDragAndDrop()
//                                    startDrag(index, offset)
//                                },
//                                onDragEnd = {
//                                    endDrag()
//                                }
//                            )
//                        }
//                        .offset { if (index == draggedIndex) dragOffset else IntOffset.Zero }
//
//                    when (block) {
//                        is PrintBlock -> PrintBlock(
//                            block = block,
//                            modifier = draggableModifier
//                        )
//                        is SetVariableBlock -> SetVariableBlock(
//                            block = block,
//                            modifier = draggableModifier
//                        )
//                    }
//                }
            }

    var draggedIndex by remember { mutableStateOf(-1) }
    var dragOffset by remember { mutableStateOf(IntOffset.Zero) }

//    fun startDrag(index: Int, offset: Offset) {
//        draggedIndex = index
//        dragOffset = dragOffset.copy(
//            x = offset.x.toInt(),
//            y = offset.y.toInt()
//        )
//    }
//
//    fun endDrag() {
//        draggedIndex = -1
//        dragOffset = IntOffset.Zero
//    }

//    DisposableEffect(state) {
//        val subscription = state.interactionSource.collect { interaction ->
//            if (interaction is PointerInputChange.Press && interaction.changes.any { it.pressed }) {
//                endDrag()
//            }
//        }
//        onDispose {
//            subscription.cancel()
//        }
//    }
}



