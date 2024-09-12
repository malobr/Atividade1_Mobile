package com.example.atividade1

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.atividade1.ui.theme.Atividade1Theme
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Atividade1Theme {
                Jogar()
            }
        }
    }
}

@Composable
fun Jogar() {
    val context = LocalContext.current
    var cliques by remember { mutableStateOf(0) }

    fun generateRandomNumber(): Int {
        return Random.nextInt(1, 51)
    }

    var maximoCliques by remember { mutableStateOf(generateRandomNumber()) }
    var exibirDialogo by remember { mutableStateOf(false) }
    var jogoFinalizado by remember { mutableStateOf(false) }

    val recursoImagem = when {
        cliques <= maximoCliques * 0.33 -> R.drawable.pobre
        cliques <= maximoCliques * 0.66 -> R.drawable.medio
        cliques < maximoCliques -> R.drawable.alto_4
        else -> R.drawable.rico
    }

    fun reiniciar() {
        cliques = 0
        maximoCliques = generateRandomNumber()
        jogoFinalizado = false
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(onClick = {
            if (cliques < maximoCliques) {
                cliques++
            } else {
                jogoFinalizado = true
                exibirDialogo = true
            }
        }) {
            Text(text = "Clicar")
        }

        Text(text = "Número de cliques: $cliques", fontSize = 18.sp)

        Image(
            painter = painterResource(id = recursoImagem),
            contentDescription = null,
            modifier = Modifier
                .padding(top = 16.dp)
                .size(size = 150.dp)
        )

        Button(onClick = {
            exibirDialogo = true
        }, modifier = Modifier.padding(top = 16.dp)) {
            Text(text = "Desistir")
        }
    }

    if (exibirDialogo) {
        AlertDialog(
            onDismissRequest = { exibirDialogo = false },
            title = { Text(text = if (jogoFinalizado) "Parabéns!" else "Desistência") },
            text = {
                Text(
                    text = if (jogoFinalizado) {
                        "Você atingiu o número máximo de cliques! Gostaria de jogar novamente?"
                    } else {
                        "Novo jogo?"
                    }
                )
            },
            confirmButton = {
                Button(onClick = {
                    reiniciar()
                    exibirDialogo = false
                }) {
                    Text("Sim")
                }
            },
            dismissButton = {
                Button(onClick = {
                    exibirDialogo = false
                    if (jogoFinalizado) {
                        jogoFinalizado = false
                    } else {
                        Toast.makeText(context, "Fim de jogo", Toast.LENGTH_SHORT).show()
                    }
                }) {
                    Text("Não")
                }
            }
        )
    }
}