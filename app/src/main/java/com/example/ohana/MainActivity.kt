package com.example.ohana

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import com.example.ohana.logic.*
import com.example.ohana.ui.blocks.*
import com.example.ohana.ui.user_interface.*

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
    blocks.forEach {
        when(it) {
            is PrintBlock -> PrintBlock(block = it)
            is SetVariableBlock -> SetVariableBlock(block = it)
        }
    }
}

