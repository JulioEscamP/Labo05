package com.JJEP_00117220.labo5

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.JJEP_00117220.labo5.Model.Card
import com.JJEP_00117220.labo5.Model.Task
import com.JJEP_00117220.labo5.ui.theme.Labo5Theme
import com.JJEP_00117220.labo5.ui.viewmodel.GeneralViewModel
import java.util.Date


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Labo5Theme {
                Navigation()
            }
        }
    }
}

@Composable
fun Navigation(){
    val navController = rememberNavController()
    val viewModel: GeneralViewModel = viewModel()

    NavHost(navController = navController, startDestination = "pantallaPrincipal") {
        composable("pantallaPrincipal") {
            MainScreen(
                onNavigateToSegundaPantalla = { navController.navigate("segundaPantalla") },
                viewModel = viewModel
            )
        }
        composable("segundaPantalla") {
            SecondaryScreen(onNavigateBack = { navController.popBackStack() })
        }
    }
}

@Composable
fun MainScreen(onNavigateToSegundaPantalla: () -> Unit, viewModel: GeneralViewModel) {

    val tasks = viewModel.tasks.collectAsState()
    val lista = remember { mutableStateListOf<Card>() }

    tasks.value.forEach { task ->
        Log.d("Task", task.toString())
        lista.add(
            Card(
                pos = task.id,
                title = task.title,
                description = task.description,
                endDate = task.endDate,
                checked = task.isCompleted
            )
        )
    }

    val newCard = remember {
        mutableStateOf(
            Card(
                pos = 0,
                title = "",
                description = "",
                endDate = Date(),
                checked = false
            )
        )
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Pantalla Principal")
        Button(onClick = onNavigateToSegundaPantalla) {
            Text(text = "Ir a Segunda Pantalla")
        }

        Button(onClick = {
            val task = Task(
                id = newCard.value.pos,
                title = newCard.value.title,
                description = newCard.value.description,
                endDate = newCard.value.endDate,
                isCompleted = newCard.value.checked
            )
            viewModel.addTask(task)
            newCard.value = Card(0, "", "", Date(), false)
        }) {
            Text(text = "Agregar Nueva Tarea")
        }


    }


}

@Composable
fun SecondaryScreen(onNavigateBack: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Segunda Pantalla")
        Button(onClick = onNavigateBack) {
            Text(text = "Regresar")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Labo5Theme {
        Navigation()
    }
}