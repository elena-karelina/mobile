package com.example.ohana.ui.blocks

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ohana.logic.IfBlock
import com.example.ohana.logic.PrintBlock
import com.example.ohana.logic.SetArrBlock
import com.example.ohana.logic.SetVariableBlock
import com.example.ohana.logic.WhileBlock
import com.example.ohana.ui.theme.controlerBlockBackground
import com.example.ohana.ui.theme.printBlockBackground
import com.example.ohana.ui.theme.variableBlockBackground

val blockTextFontSize = 20.sp
val blockPadding = 8.dp
val fieldBackgroundTextColor = Color(0xFFA5A5A5)

@Composable
fun PrintBlock(block: PrintBlock, distance: Dp) {
    var value by remember(block.value1) { mutableStateOf(block.value1) }
    val minWidth by remember { mutableStateOf(10.dp) }
    Box(modifier = Modifier.padding(start = distance)) {
        Box(
            modifier = Modifier
                .background(printBlockBackground, RoundedCornerShape(8.dp))
                .padding(blockPadding)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "print",
                    modifier = Modifier.padding(horizontal = blockPadding),
                    color = Color.White,
                    fontSize = blockTextFontSize
                )

                Box(
                    modifier = Modifier
                        .background(Color.White, shape = RoundedCornerShape(8.dp))
                        .padding(vertical = blockPadding, horizontal = blockPadding)
                        .widthIn(min = minWidth)
                        .height(IntrinsicSize.Min)
                        .width(IntrinsicSize.Min)
                        .wrapContentHeight()
                ) {
                    BasicTextField(
                        value = value,
                        onValueChange = {
                            block.value1 = it
                            value = it
                            block.log = it
                        },
                        modifier = Modifier.fillMaxSize(),
                        singleLine = true,
                        maxLines = 1,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    )
                }
            }
        }
    }
}

@Composable
fun EndBlock(distance: Dp) {
    Box(
        modifier = Modifier.padding(start = distance)
    ) {
        Box(
            modifier = Modifier
                .background(controlerBlockBackground, shape = RoundedCornerShape(8.dp))
                .padding(blockPadding, vertical = blockPadding / 2)
        ) {
            Text(
                text = "end of block",
                color = Color.White,
                fontSize = 10.sp
            )
        }
    }
}

@Composable
fun IfBlock(block: IfBlock, distance: Dp) {
    var value by remember(block.value1) { mutableStateOf(block.value1) }
    val minWidth by remember { mutableStateOf(10.dp) }
    var isFocused by remember { mutableStateOf(false) }
    Box(
        modifier = Modifier.padding(start = distance)
    ) {
        Box(
            modifier = Modifier
                .background(controlerBlockBackground, shape = RoundedCornerShape(8.dp))
                .padding(blockPadding)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "if",
                    modifier = Modifier.padding(horizontal = blockPadding),
                    color = Color.White,
                    fontSize = blockTextFontSize
                )

                Box(
                    modifier = Modifier
                        .background(Color.White, shape = RoundedCornerShape(8.dp))
                        .padding(vertical = blockPadding, horizontal = blockPadding)
                        .widthIn(min = minWidth)
                        .height(IntrinsicSize.Min)
                        .width(IntrinsicSize.Min)
                        .wrapContentHeight()
                ) {
                    BasicTextField(
                        textStyle = TextStyle(
                            color = if (value.isEmpty() && !isFocused) fieldBackgroundTextColor else Color.Black
                        ),
                        value = if (value.isEmpty() && !isFocused) "condition" else value,
                        onValueChange = {
                            block.value1 = it
                            value = it
                            block.condition = it
                        },
                        modifier = Modifier
                            .fillMaxSize()
                            .onFocusChanged { isFocused = it.isFocused },
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
fun WhileBlock(block: WhileBlock, distance: Dp) {
    var value by remember(block.value1) { mutableStateOf(block.value1) }
    val minWidth by remember { mutableStateOf(10.dp) }
    var isFocused by remember { mutableStateOf(false) }
    Box(
        modifier = Modifier.padding(start = distance)
    ) {
        Box(
            modifier = Modifier
                .background(controlerBlockBackground, shape = RoundedCornerShape(8.dp))
                .padding(blockPadding)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "while",
                    modifier = Modifier.padding(horizontal = blockPadding),
                    color = Color.White,
                    fontSize = blockTextFontSize
                )

                Box(
                    modifier = Modifier
                        .background(Color.White, shape = RoundedCornerShape(8.dp))
                        .padding(vertical = blockPadding, horizontal = blockPadding)
                        .widthIn(min = minWidth)
                        .height(IntrinsicSize.Min)
                        .width(IntrinsicSize.Min)
                        .wrapContentHeight()
                ) {
                    BasicTextField(
                        textStyle = TextStyle(
                            color = if (value.isEmpty() && !isFocused) fieldBackgroundTextColor else Color.Black
                        ),
                        value = if (value.isEmpty() && !isFocused) "condition" else value,
                        onValueChange = {
                            block.value1 = it
                            value = it
                            block.condition = it
                        },
                        modifier = Modifier
                            .fillMaxSize()
                            .onFocusChanged { isFocused = it.isFocused },
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
fun SetVariableBlock(block: SetVariableBlock, distance: Dp) {
    var value1 by remember(block.value1) { mutableStateOf(block.value1) }
    var value2 by remember(block.value2) { mutableStateOf(block.value2) }
    val minWidth by remember { mutableStateOf(10.dp) }
    var isFocused1 by remember { mutableStateOf(false) }
    var isFocused2 by remember { mutableStateOf(false) }


    Box(
        modifier = Modifier.padding(start = distance)
    ) {
        Box(
            modifier = Modifier
                .wrapContentSize()
                .background(variableBlockBackground, shape = RoundedCornerShape(8.dp))
                .padding(blockPadding)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                BasicTextField(
                    textStyle = TextStyle(
                        color = if (value1.isEmpty() && !isFocused1) fieldBackgroundTextColor else Color.Black
                    ),
                    value = if (value1.isEmpty() && !isFocused1) "name" else value1,
                    onValueChange = { newValue ->
                        value1 = newValue
                        block.name = value1
                        block.value1 = value1
                    },
                    modifier = Modifier
                        .onFocusChanged { isFocused1 = it.isFocused }
                        .background(Color.White, shape = RoundedCornerShape(8.dp))
                        .padding(vertical = blockPadding, horizontal = blockPadding)
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
                    modifier = Modifier.padding(horizontal = blockPadding),
                    color = Color.White,
                    fontSize = blockTextFontSize
                )

                BasicTextField(
                    textStyle = TextStyle(
                        color = if (value2.isEmpty() && !isFocused2) fieldBackgroundTextColor else Color.Black
                    ),
                    value = if (value2.isEmpty() && !isFocused2) "value" else value2,
                    onValueChange = {
                        value2 = it
                        block.value = value2
                        block.value2 = value2
                    },
                    modifier = Modifier
                        .onFocusChanged { isFocused2 = it.isFocused }
                        .background(Color.White, shape = RoundedCornerShape(8.dp))
                        .padding(vertical = blockPadding, horizontal = blockPadding)
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

}

@Composable
fun SetArrBlock(block: SetArrBlock, distance: Dp) {
    var value1 by remember(block.value1) { mutableStateOf(block.value1) }
    var value2 by remember(block.value2) { mutableStateOf(block.value2) }
    val minWidth by remember { mutableStateOf(10.dp) }
    var isFocused1 by remember { mutableStateOf(false) }
    var isFocused2 by remember { mutableStateOf(false) }
    Box(
        modifier = Modifier.padding(start = distance)
    ) {
        Box(
            modifier = Modifier
                .wrapContentSize()
                .background(variableBlockBackground, shape = RoundedCornerShape(8.dp))
                .padding(8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "arr",
                    color = Color.White,
                    fontSize = blockTextFontSize,
                    modifier = Modifier.padding(end = blockPadding),
                )
                BasicTextField(
                    textStyle = TextStyle(
                        color = if (value1.isEmpty() && !isFocused1) fieldBackgroundTextColor else Color.Black
                    ),
                    value = if (value1.isEmpty() && !isFocused1) "name" else value1,
                    onValueChange = { newValue ->
                        value1 = newValue.replace(Regex("[^a-z]"), "")
                        block.name = value1
                        block.value1 = value1
                    },
                    modifier = Modifier
                        .onFocusChanged { isFocused1 = it.isFocused }
                        .background(Color.White, shape = RoundedCornerShape(8.dp))
                        .padding(vertical = blockPadding, horizontal = blockPadding)
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
                    text = " ",
                    modifier = Modifier.padding(start = blockPadding, end = blockPadding),
                    color = Color.White,
                    fontSize = blockTextFontSize
                )

                BasicTextField(
                    textStyle = TextStyle(
                        color = if (value2.isEmpty() && !isFocused2) fieldBackgroundTextColor else Color.Black
                    ),
                    value = if (value2.isEmpty() && !isFocused2) "size" else value2,
                    onValueChange = {
                        value2 = it
                        block.size = value2
                        block.value2 = value2
                    },
                    modifier = Modifier
                        .onFocusChanged { isFocused2 = it.isFocused }
                        .background(Color.White, shape = RoundedCornerShape(8.dp))
                        .padding(vertical = blockPadding, horizontal = blockPadding)
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

}