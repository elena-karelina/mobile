package com.example.ohana.ui.user_interface

import android.app.Activity
import android.app.AlertDialog
import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ohana.BlocksDrawer
import com.example.ohana.logic.Block
import com.example.ohana.logic.EndBlock
import com.example.ohana.logic.IfBlock
import com.example.ohana.logic.PrintBlock
import com.example.ohana.logic.SetArrBlock
import com.example.ohana.logic.SetVariableBlock
import com.example.ohana.logic.WhileBlock
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
    val showDialog = remember { mutableStateOf(false) }


    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        ModalNavigationDrawer(
            scrimColor = Color.Transparent,
            drawerState = drawerState,
            drawerContent = {
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
                                item {
                                    MenuItem(
                                        text = "    If",
                                        onClick = { blocks.add(IfBlock("")); blocks.add(EndBlock()) })
                                }
                                item {
                                    MenuItem(text = "    While", onClick = {
                                        blocks.add(WhileBlock("")); blocks.add(
                                        EndBlock()
                                    )
                                    })
                                }
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
                                        onClick = { blocks.add(PrintBlock("")) })
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
                                        text = "    Assignment",
                                        onClick = { blocks.add(SetVariableBlock("", "")) })
                                }
                                item {
                                    MenuItem(
                                        text = "    Set array",
                                        onClick = { blocks.add(SetArrBlock("", "")) })
                                }
                            }
                            item { MenuItem(text = "Clear", onClick = { blocks.clear() }) }
                            item { MenuItem(text = "How to use", onClick = { showDialog.value = true })
                            }
                        }
                    }
                }
            },
            content = {
                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                    BlocksDrawer(blocks)
                    OpenMenuButton(scope, drawerState)
                    if (showDialog.value) {
                        val activity = LocalContext.current as? Activity
                        activity?.let {
                            сreateDialog(it)
                        }
                        showDialog.value = false
                    }

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
            color = Color.White,
            fontFamily = InterFont,
            fontWeight = FontWeight.Bold
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
                    fontWeight = FontWeight.Bold,
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
                            text = "     >> $it",
                            fontFamily = InterFont,
                            fontWeight = FontWeight.Bold,
                            color = ConsoleText
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun сreateDialog(activity: Activity?) {
    val builder = AlertDialog.Builder(activity)
    builder.setTitle("Привет!")
        .setMessage("Мы очень старались над созданием этого приложения и хотим предупретить, что для корректного использования Вам стоит знать некоторые его особенности. \n\n1. Пожалуйста, при вводе математических выражений пишите все сиволы через пробел: \"a = b * 8\" \n\n2. При обращении к элементу массива конкретного индекста вводите \"arr[i]\", иначе \"arr[2_+_1]\"")
        .setPositiveButton(
            "Спасибо!"
        ) { dialog, id ->
            Toast.makeText(activity, "Приятного пользования!", Toast.LENGTH_SHORT).show()
        }

    builder.create().show()
}