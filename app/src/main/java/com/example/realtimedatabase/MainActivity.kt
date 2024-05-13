package com.example.realtimedatabase

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.realtimedatabase.ui.theme.RealTimeDataBaseTheme
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        startKoin {
            androidContext(this@MainActivity)
            androidLogger()
            modules(appModule)
        }
        setContent {
            RealTimeDataBaseTheme {
                var isInsert = remember {
                    mutableStateOf(false)
                }
                Scaffold(
                    floatingActionButton = {
                        FloatingActionButton(onClick = {
                            isInsert.value=!isInsert.value
                        }) {
                            Icon(imageVector = Icons.Default.Add, contentDescription ="")
                        }
                    }

                ) {
                    RealTimeScreen(isInsert)

                }
            }
        }
    }
}

