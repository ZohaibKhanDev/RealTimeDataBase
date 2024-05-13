package com.example.realtimedatabase.a

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.compose.koinInject

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RealTimeScreen1(isInsert: MutableState<Boolean>) {
    val viewModel1: RealTimeViewModel1 = koinInject()
    val scope1 = rememberCoroutineScope()
    val context = LocalContext.current
    var tittle by remember {
        mutableStateOf("")
    }
    val res = viewModel1.res1.value
    var des by remember {
        mutableStateOf("")
    }
    var circularProgressBar by remember {
        mutableStateOf(false)
    }
    var isUpdate = remember {
        mutableStateOf(false)
    }
    if (circularProgressBar) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    }

    if (isInsert.value) {

       AlertDialog(onDismissRequest = { isInsert.value=false }, confirmButton = {  },
           text = {
               Column(
                   Modifier
                       .fillMaxWidth()
                       .padding(10.dp),
                   horizontalAlignment = Alignment.CenterHorizontally
               ) {
                   TextField(value = tittle, onValueChange = {
                       tittle = it
                   }, placeholder = {
                       Text(text = "Tittle")
                   },)


                   Spacer(modifier = Modifier.height(10.dp))


                   TextField(value = des, onValueChange = {
                       des = it
                   }, placeholder = {
                       Text(text = "Description")
                   },)


                   Spacer(modifier = Modifier.height(10.dp))


                   Button(
                       onClick = {
                           scope1.launch {
                               viewModel1.insert1(
                                   RealTimeModelResponse1.RealTimeItems1(
                                       tittle = tittle,
                                       description = des
                                   )
                               ).collect {
                                   when (it) {
                                       is ResultState1.Error -> {
                                           circularProgressBar = false
                                           Toast.makeText(context, "$it", Toast.LENGTH_SHORT).show()
                                       }

                                       ResultState1.Loading -> {
                                           circularProgressBar = true
                                       }

                                       is ResultState1.Success -> {
                                           isInsert.value = false
                                           circularProgressBar = false
                                           Toast.makeText(context, "${it.data}", Toast.LENGTH_SHORT)
                                               .show()
                                       }
                                   }
                               }
                           }
                       }, modifier = Modifier
                           .fillMaxWidth()
                           .align(Alignment.CenterHorizontally)
                   ) {
                       Text(text = "Save", fontSize = 17.sp, fontWeight = FontWeight.SemiBold)
                   }
               }
           }
           )
    }


    if (res.item.isNotEmpty()) {
        LazyColumn(modifier = Modifier.padding(top = 70.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            items(res.item, key = { it.key!! }) { res ->
                EachRow1(itemState = res.item!!,
                    onUpdate = {
                        isUpdate.value = true
                        viewModel1.setData1(res)
                    }
                ) {
                    scope1.launch(Dispatchers.Main) {
                        viewModel1.delete(res.key!!).collect {
                            when (it) {
                                is ResultState1.Error -> {
                                    isUpdate.value = false
                                    Toast.makeText(context, "$it", Toast.LENGTH_SHORT).show()
                                }

                                ResultState1.Loading -> {
                                    isUpdate.value = true
                                }

                                is ResultState1.Success -> {

                                    isUpdate.value = false
                                    Toast.makeText(context, "${it.data}", Toast.LENGTH_SHORT)
                                        .show()
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    if (res.Loading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    }

    if (res.error.isNotEmpty()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = "${res.error}")
        }
    }

    if (isUpdate.value) {
        Update1(isUpdate = isUpdate, itemState = viewModel1.updatedRes1.value, viewModel = viewModel1)
    }

}

@Composable
fun EachRow1(
    itemState: RealTimeModelResponse1.RealTimeItems1,
    onUpdate: () -> Unit = {},
    onDelete: () -> Unit = {}
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(1.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .clickable { onUpdate() }) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = itemState.tittle,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )

                    IconButton(
                        onClick = {
                            onDelete()
                        },
                        modifier = Modifier.align(Alignment.CenterVertically)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "",
                            tint = Color.Red
                        )
                    }

                }

                Text(
                    text = itemState.description,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.Gray
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Update1(
    isUpdate: MutableState<Boolean>,
    itemState: RealTimeModelResponse1,
    viewModel: RealTimeViewModel1
) {
    var tittle by remember {
        mutableStateOf(itemState.item?.tittle)
    }
    var des by remember {
        mutableStateOf(itemState.item?.description)
    }

    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    if (isUpdate.value) {
        AlertDialog(onDismissRequest = { isUpdate.value = false }) {

            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {


                tittle?.let {
                    TextField(value = it, onValueChange = {
                        tittle = it
                    }, placeholder = {
                        Text(text = "Tittle")
                    })
                }


                Spacer(modifier = Modifier.height(10.dp))


                des?.let {
                    TextField(value = it, onValueChange = {
                        des = it
                    }, placeholder = {
                        Text(text = "Description")
                    })
                }


                Spacer(modifier = Modifier.height(10.dp))


                Button(
                    onClick = {
                        scope.launch(Dispatchers.Main) {
                            viewModel.update(
                                RealTimeModelResponse1(
                                    item = tittle?.let {
                                        des?.let { it1 ->
                                            RealTimeModelResponse1.RealTimeItems1(
                                                it, it1
                                            )
                                        }
                                    }, key = itemState.key
                                )
                            ).collect {
                                when (it) {
                                    is ResultState1.Error -> {
                                        isUpdate.value = false
                                        Toast.makeText(context, "$it", Toast.LENGTH_SHORT).show()
                                    }

                                    ResultState1.Loading -> {
                                        isUpdate.value = true
                                    }

                                    is ResultState1.Success -> {

                                        isUpdate.value = false
                                        Toast.makeText(context, "${it.data}", Toast.LENGTH_SHORT)
                                            .show()
                                    }
                                }
                            }
                        }
                    }, modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally)
                ) {
                    Text(text = "Save", fontSize = 17.sp, fontWeight = FontWeight.SemiBold)
                }
            }
        }
    }
}