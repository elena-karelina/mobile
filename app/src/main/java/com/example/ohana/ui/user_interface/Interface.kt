package com.example.ohana.ui.user_interface

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ohana.BlocksDrawer
import com.example.ohana.logic.Block
import com.example.ohana.logic.IfBlock
import com.example.ohana.logic.PrintBlock
import com.example.ohana.logic.SetVariableBlock
import com.example.ohana.ui.theme.ConsoleBackground
import com.example.ohana.ui.theme.ConsoleText
import com.example.ohana.ui.theme.ConsoleTextBackground
import com.example.ohana.ui.theme.InterFont
import com.example.ohana.ui.theme.MenuBlockBackground
import com.example.ohana.ui.theme.RightMenuBackground
import kotlinx.coroutines.CoroutineScope

@Composable
fun RightMenu(
    scope: CoroutineScope,
    drawerState: DrawerState,
    blocks: MutableList<Block>
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
                        drawerContainerColor = RightMenuBackground,
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

                                    onClick = {
                                        isControllersDropdownOpen.value =
                                            !isControllersDropdownOpen.value
                                    },
                                    menuItemBackgroundColor = if (isControllersDropdownOpen.value)
                                        MenuBlockBackground
                                    else Color.Transparent,
                                    isNested = false
                                )
                            }

                            if (isControllersDropdownOpen.value) {
                                item { MenuItem(text = "    if", onClick = { blocks.add(IfBlock(" ")) }) }
                                item { MenuItem(text = "    if-else", onClick = {}) }
                                item { MenuItem(text = "    while", onClick = {}) }
                                item { MenuItem(text = "    repeat", onClick = {}) }
                            }
                            item {
                                MenuItem(
                                    text = "Streams",
                                    onClick = {
                                        isStreamsDropdownOpen.value = !isStreamsDropdownOpen.value
                                    },
                                    menuItemBackgroundColor = if (isStreamsDropdownOpen.value)
                                        MenuBlockBackground
                                    else Color.Transparent,
                                    isNested = false
                                )
                            }
                            if (isStreamsDropdownOpen.value) {
                                item {
                                    MenuItem(
                                        text = "    Print",
                                        onClick = { blocks.add(PrintBlock(""))  })
                                }
                                item {
                                    MenuItem(
                                        text = "    Input",
                                        onClick = {  })
                                }
                            }
                            item {
                                MenuItem(
                                    text = "Variables",
                                    onClick = {
                                        isVariablesDropdownOpen.value =
                                            !isVariablesDropdownOpen.value
                                    },
                                    menuItemBackgroundColor = if (isVariablesDropdownOpen.value)
                                        MenuBlockBackground
                                    else Color.Transparent,
                                    isNested = false
                                )
                            }
                            if (isVariablesDropdownOpen.value) {
                                item {
                                    MenuItem(
                                        text = "    Set variable",
                                        onClick = { blocks.add(SetVariableBlock("", "")) })
                                }
                            }
                            item { MenuItem(text = "Clear", onClick = { blocks.clear() }) }
                        }
                    }
                }
            },
            content = {
                // Установка обычного направления чтения для content
                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {

                    // TODO вставить отрисовку блоков сюда
                    BlocksDrawer(blocks)

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
        if (text == "Clear") {
            Text(
                text = text,
                fontSize = 30.sp,
                color = Color.White,
                fontFamily = InterFont
            )
        }
        else {
                Text(
                    text = text,
                    fontSize = 20.sp,
                    color = Color.White,
                    fontFamily = InterFont
                )
        }

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
                .background(ConsoleBackground)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(20.dp)
                    .background(ConsoleTextBackground)
            ) {
                Text(
                    text = "     console output",
                    fontFamily = InterFont,
                    color = ConsoleText
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
                            text = "    >> " + it,
                            fontFamily = InterFont,
                            color = ConsoleText
                        )
                    }
                }
            }
        }
    }
}