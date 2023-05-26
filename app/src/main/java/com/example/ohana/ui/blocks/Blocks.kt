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
import androidx.compose.ui.graphics.Color
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


@Composable
fun PrintBlock(block: PrintBlock, distance: Dp) {
    var value by remember(block.value1) { mutableStateOf(block.value1) }
    val minWidth by remember { mutableStateOf(10.dp) }
    Box(modifier = Modifier.padding(start=distance)){
        Box(modifier = Modifier
            .background(Color(0xFF8338EC), RoundedCornerShape(8.dp))
            .padding(12.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "print",
                    modifier = Modifier.padding(horizontal = 16.dp),
                    color = Color.White,
                    fontSize = 20.sp
                )

                Box(modifier = Modifier
                    .background(Color.White, shape = RoundedCornerShape(8.dp))
                    .padding(vertical = 8.dp, horizontal = 10.dp)
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
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                    )
                }
            }
        }
    }
}

//@Composable
//fun InputBlock(block: InputBlock, distance: Dp) {
//    var value by remember(block.value1) { mutableStateOf(block.value1) }
//    val minWidth by remember { mutableStateOf(10.dp) }
//    Box(modifier = Modifier.padding(start=distance)){
//        Box(modifier = Modifier
//            .background(Color(0xFF8338EC), RoundedCornerShape(8.dp))
//            .padding(12.dp)
//        ) {
//            Row(
//                verticalAlignment = Alignment.CenterVertically,
//            ) {
//                Text(
//                    text = "input",
//                    modifier = Modifier.padding(horizontal = 16.dp),
//                    color = Color.White,
//                    fontSize = 20.sp
//                )
//
//                Box(modifier = Modifier
//                    .background(Color.White, shape = RoundedCornerShape(8.dp))
//                    .padding(vertical = 8.dp, horizontal = 10.dp)
//                    .widthIn(min = minWidth)
//                    .height(IntrinsicSize.Min)
//                    .width(IntrinsicSize.Min)
//                    .wrapContentHeight()
//                ) {
//                    BasicTextField(
//                        value = value,
//                        onValueChange = {
//                            block.value1 = it
//                            value = it
//                            block.log = it
//                        },
//                        modifier = Modifier.fillMaxSize(),
//                        singleLine = true,
//                        maxLines = 1,
//                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
//                    )
//                }
//            }
//        }
//    }
//}
@Composable
fun EndBlock(distance: Dp){
    Box(
        modifier = Modifier.padding(start = distance)){
        Box(
            modifier = Modifier
                .background(Color(0xFFE76F51), shape = RoundedCornerShape(8.dp))
                .padding(horizontal = 24.dp, vertical = 5.dp)
        ){
            Text(
                text = "end of block",
                color = Color.White,
                fontSize = 8.sp
            )
        }
    }
}
@Composable
fun IfBlock(block: IfBlock, distance: Dp) {
    var value by remember(block.value1) { mutableStateOf(block.value1) }
    val minWidth by remember { mutableStateOf(10.dp) }
    Box(
        modifier = Modifier.padding(start = distance)){
        Box(
            modifier = Modifier
                .background(Color(0xFFE76F51), shape = RoundedCornerShape(8.dp))
                .padding(12.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "if",
                    modifier = Modifier.padding(horizontal = 16.dp),
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
                        onValueChange = {
                            block.value1 = it
                            value = it
                            block.condition = it
                        },
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
fun WhileBlock(block: WhileBlock, distance: Dp) {
    var value by remember(block.value1) { mutableStateOf(block.value1) }
    val minWidth by remember { mutableStateOf(10.dp) }
    Box(
        modifier = Modifier.padding(start = distance)){
        Box(
            modifier = Modifier
                .background(Color(0xFFE76F51), shape = RoundedCornerShape(8.dp))
                .padding(12.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "while",
                    modifier = Modifier.padding(horizontal = 16.dp),
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
                        onValueChange = {
                            block.value1 = it
                            value = it
                            block.condition = it
                        },
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
fun SetVariableBlock(block: SetVariableBlock, distance: Dp) {
    var value1 by remember(block.value1) { mutableStateOf(block.value1) }
    var value2 by remember(block.value2) { mutableStateOf(block.value2) }
    val minWidth by remember { mutableStateOf(10.dp) }
    Box(
        modifier = Modifier.padding(start = distance)){
        Box(
            modifier = Modifier
                .wrapContentSize()
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
                        block.name = value1
                        block.value1 = value1
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
                    modifier = Modifier.padding(horizontal = 16.dp),
                    color = Color.White,
                    fontSize = 30.sp
                )

                BasicTextField(
                    value = value2,
                    onValueChange = {
                        value2 = it
                        block.value = value2
                        block.value2 = value2
                    },
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

}
@Composable
fun SetArrBlock(block: SetArrBlock, distance: Dp) {
    var value1 by remember(block.value1) { mutableStateOf(block.value1) }
    var value2 by remember(block.value2) { mutableStateOf(block.value2) }
    val minWidth by remember { mutableStateOf(10.dp) }
    Box(
        modifier = Modifier.padding(start = distance)){
        Box(
            modifier = Modifier
                .wrapContentSize()
                .background(Color(0xFF2A9D8F), shape = RoundedCornerShape(8.dp))
                .padding(8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "arr",
                    color = Color.White,
                    fontSize = 20.sp ,
                    modifier = Modifier.padding( end = 8.dp),
                )
                BasicTextField(
                    value = value1,
                    onValueChange = { newValue ->
                        value1 = newValue.replace(Regex("[^a-z]"), "")
                        block.name = value1
                        block.value1 = value1
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
                    text = "size:",
                    modifier = Modifier.padding(start = 8.dp, end = 8.dp),
                    color = Color.White,
                    fontSize = 20.sp
                )

                BasicTextField(
                    value = value2,
                    onValueChange = {
                        value2 = it
                        block.size = value2
                        block.value2 = value2
                    },
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

}