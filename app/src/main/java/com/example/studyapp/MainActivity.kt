package com.example.studyapp

import android.R
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.input.KeyboardType

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            calculadora()
        }
    }
}


data class task(
    val text: String,
    var selected: Boolean = false
)


@Composable
fun StudyApp() {

    // ===== STATES =====


    var text by remember { mutableStateOf("") }
    val list = remember { mutableStateListOf<task>() }
    var counter by remember { mutableStateOf(10) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Gray)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text("Pratica do jetpack compose", fontSize = 20.sp)

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = text,
            onValueChange = { text = it },
            label = { Text("Type something") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = {
                if (list.isNotEmpty()) {
                    list.removeAll { it.selected }
                }
            }
        ) {
            Text("Limpar lista\n(Itens Selecionados ${list.count{it.selected}}) ")


        }

        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = {
                if (text.isNotBlank()) {
                    list.add(task(text))
                    text = ""
                }
            }
        ) {
            Text("Adicionar a lista")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Clicks: $counter",
            modifier = Modifier.clickable { counter++ }
        )

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            modifier = Modifier.fillMaxWidth()
        ) {
            items(list) { item ->
                val cor = if (item.selected) Color.Red else Color.White
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp)
                        .clickable {
                            val index = list.indexOf(item)
                            list[index] = item.copy(selected = !item.selected)


                        },
                    color = cor

                ) {
                    Text(
                        text = item.text,
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
        }
    }
}




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun calculadora(){
    var mostrar by remember{mutableStateOf(false)}
    var imc by remember { mutableStateOf(0.0) }
    var peso by remember { mutableStateOf("") }
    var altura by remember { mutableStateOf("") }
    Scaffold(
        topBar = {TopAppBar(colors = TopAppBarDefaults.largeTopAppBarColors(Color.Red),title = {
            Text("Calculadora de imc")
        }) }
    ) {paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),


        ) {

            Row(
                modifier = Modifier
                    .padding(30.dp)

            ) {


                Text(text = "Peso",
                    fontSize = 30.sp )
                Spacer(modifier = Modifier.width(160.dp))
                Text(text = "Altura",
                    fontSize = 30.sp, )



            }
            Row (
                 modifier = Modifier

            ) {
                TextField(
                    modifier = Modifier
                        .weight(1f)
                        .padding(8.dp),
                    value = peso,
                    onValueChange = { peso = it},
                    label = { Text("Ex: 165") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),

                )
                TextField(modifier = Modifier
                    .weight(1f)
                    .padding(8.dp),
                    value = altura,
                    onValueChange = { altura = it},
                    label = { Text("Ex: 65, 80") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )

            }
            Row(  modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center){
                Button(onClick = {
                    if (peso.isNotBlank()&& altura.isNotBlank()){
                        val p = peso.toDoubleOrNull()
                        val a = altura.toDoubleOrNull()

                        if (p != null && a != null && a > 0) {
                            val alturaMetros = a / 100
                            imc = p / (alturaMetros * alturaMetros)
                            mostrar = true
                        }
                    }
                }) {Text("Calcular") }
            }
            if (mostrar){
            Text(
                text = "IMC: %.2f".format(imc),
                fontSize = 22.sp,
                modifier = Modifier.padding(16.dp)
            )}
        }
    }
}
