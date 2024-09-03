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
import androidx.compose.ui.tooling.preview.Preview
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
                Jogar() // Jogar roda toda a atividade do Jogo
            }
        }
    }
}

@Composable
fun Jogar() {
    val context = LocalContext.current

    // Variável que gerencia o número de cliques do usuário
    var cliques by remember { mutableStateOf(0) }

    // Gera um número aleatório do range que for desejado, no caso de 1 a 50
    fun generateRandomNumber(): Int {
        return Random.nextInt(1, 51)
    }

    // Gera aleatoriamente o número máximo de cliques
    var maximoCliques by remember { mutableStateOf(generateRandomNumber()) }

    // Controla a exibição do diálogo de desistencia ou conquista
    var exibirDialogo by remember { mutableStateOf(false) }
    var jogoFinalizado by remember { mutableStateOf(false) }

    // Função que estabelece a imagem conforme o número de cliques.
    val recursoImagem = when {
        cliques <= maximoCliques * 0.33 -> R.drawable.pobre // inicial
        cliques <= maximoCliques * 0.66 -> R.drawable.medio // mediana
        cliques < maximoCliques -> R.drawable.alto_4 // final
        else -> R.drawable.rico // conquista
    }

    // Função para reiniciar o jogo
    fun reiniciar() {
        cliques = 0
        maximoCliques = generateRandomNumber()
        jogoFinalizado = false
    }


    // Layout principal
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Botão de clicar
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

        // Mostra o número de cliques
        Text(text = "Número de cliques: $cliques", fontSize = 18.sp)

        // Mostra a imagem atual
        Image(
            painter = painterResource(id = recursoImagem),
            contentDescription = null,
            modifier = Modifier
                .padding(top = 16.dp)
                .size(size = 150.dp)
        )

        // Botão para desistir
        Button(onClick = {
            exibirDialogo = true
        }, modifier = Modifier.padding(top = 16.dp)) {
            Text(text = "Desistir")
        }
    }

    // Diálogo de desistência ou conquista
    if (exibirDialogo) {
        AlertDialog(
            onDismissRequest = { exibirDialogo = false },
            title = { Text(text = if (jogoFinalizado) "Parabéns!" else "Desistência") },
            text = {
                Text(
                    text = if (jogoFinalizado) {
                        "Você atingiu 100% dos cliques! Gostaria de jogar novamente?"
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
                        // Encerrar o jogo após atingir a conquista
                        jogoFinalizado = false
                    } else {
                        // Mostrar mensagem de fim de jogo usando Toast
                        Toast.makeText(context, "Fim de jogo", Toast.LENGTH_SHORT).show()
                    }
                }) {
                    Text("Não")
                }
            }
        )
    }
}

/*
@Preview(showBackground = true)
@Composable
fun JogarPreview() {
    Atividade1Theme {
        Jogar()
    }
}
*/