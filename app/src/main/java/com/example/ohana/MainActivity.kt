package com.example.ohana

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import com.example.ohana.logic.Block
import com.example.ohana.logic.EndBlock
import com.example.ohana.logic.IfBlock
import com.example.ohana.logic.Interpretator
import com.example.ohana.logic.PrintBlock
import com.example.ohana.logic.Scope
import com.example.ohana.logic.SetArrBlock
import com.example.ohana.logic.SetVariableBlock
import com.example.ohana.logic.WhileBlock
import com.example.ohana.logic.parse
import com.example.ohana.ui.blocks.EndBlock
import com.example.ohana.ui.blocks.IfBlock
import com.example.ohana.ui.blocks.PrintBlock
import com.example.ohana.ui.blocks.SetArrBlock
import com.example.ohana.ui.blocks.SetVariableBlock
import com.example.ohana.ui.blocks.WhileBlock
import com.example.ohana.ui.theme.MainBackground
import com.example.ohana.ui.user_interface.Console
import com.example.ohana.ui.user_interface.RightMenu
import com.example.ohana.ui.user_interface.RunButton
import com.example.ohana.ui.user_interface.StopButton
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
        containerColor = MainBackground,
        floatingActionButton = {
            if (!isRunning) {
                RunButton {
                    isRunning = !isRunning
                    val scope = Scope()
                    try {
                        val int = Interpretator(scope)
                        int.program = parse(blocks)
                        int.launch()
                    } catch (e: Exception) {
                        scope.throwException("Oops... Something went wrong...")
                    }
                    logs = scope.logs
                }
            } else {
                StopButton {
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


@Composable
fun BlocksDrawer(blocks: MutableList<Block>) {
    var distance = 0.dp
    val state = rememberReorderableLazyListState(onMove = { from, to ->
        blocks.apply {
            add(to.index, removeAt(from.index))
        }
    })
    LazyColumn(
        state = state.listState,
        modifier = Modifier
            .fillMaxSize()

            .padding(20.dp)
            .reorderable(state)
            .detectReorderAfterLongPress(state)
    ) {
        itemsIndexed(blocks) { index, block ->
            ReorderableItem(state, key = block) { isDragging ->
                val elevation = animateDpAsState(if (isDragging) 16.dp else 0.dp)
                Column(
                    modifier = Modifier
                        .shadow(elevation.value)
                ) {
                    when (block) {
                        is PrintBlock -> PrintBlock(block = block, distance)
                        is WhileBlock -> {
                            WhileBlock(block = block, distance)
                            distance += 20.dp
                        }

                        is IfBlock -> {
                            IfBlock(block = block, distance)
                            distance += 20.dp
                        }

                        is EndBlock -> {
                            if (distance - 20.dp >= 0.dp) {
                                distance -= 20.dp
                            }
                            EndBlock(distance)
                        }

                        is SetVariableBlock -> SetVariableBlock(block = block, distance)
                        is SetArrBlock -> SetArrBlock(block = block, distance)
                    }
                }
            }
        }
    }
}
