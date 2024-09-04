### README.md

# Projeto: Jogo de Cliques - Android com Jetpack Compose

Este projeto é uma aplicação Android simples, desenvolvida utilizando o framework Jetpack Compose, onde o objetivo do usuário é clicar em um botão até atingir um número aleatório de cliques gerado automaticamente. O projeto demonstra o uso de vários conceitos do Jetpack Compose, como estados, layouts, manipulação de eventos, exibição de imagens, e diálogos.

## Funcionalidades

- **Gerenciamento de cliques:** O número de cliques é rastreado e exibido na tela.
- **Mudança de imagem:** A imagem na tela muda com base no número de cliques, indicando o progresso do usuário.
- **Gerenciamento de estado:** O jogo termina quando o usuário atinge o número máximo de cliques. Um diálogo é exibido perguntando se o usuário deseja jogar novamente.
- **Diálogo de desistência:** O usuário pode optar por desistir a qualquer momento, exibindo um diálogo para confirmar sua decisão.

## Estrutura do Projeto

### 1. `MainActivity.kt`

Esta é a classe principal do aplicativo. Ela estende `ComponentActivity` e define o conteúdo da interface de usuário utilizando Jetpack Compose. A função `setContent` é utilizada para definir a função composable principal chamada `Jogar`.

```kotlin
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Atividade1Theme {
                Jogar()
            }
        }
    }
}
```

### 2. Função `Jogar()`

A função `Jogar` é responsável por toda a lógica e layout do jogo.

- **Variáveis de Estado:**
  - `cliques`: Armazena o número atual de cliques.
  - `maximoCliques`: Define o número aleatório de cliques que o usuário precisa atingir para ganhar.
  - `exibirDialogo`: Controla a exibição do diálogo de desistência ou vitória.
  - `jogoFinalizado`: Indica se o jogo foi finalizado.

- **Layout:**
  - Um `Column` é utilizado para organizar os elementos de forma vertical, centralizando-os na tela.
  - O botão "Clicar" incrementa o contador de cliques até atingir o número máximo.
  - Uma imagem é exibida e muda conforme o progresso do jogo.
  - O botão "Desistir" permite ao usuário sair do jogo a qualquer momento.

- **Diálogo:**
  - Um `AlertDialog` é exibido quando o usuário desiste ou finaliza o jogo, permitindo que ele reinicie o jogo ou encerre a sessão.

```kotlin
@Composable
fun Jogar() {
    val context = LocalContext.current

    var cliques by remember { mutableStateOf(0) }
    var maximoCliques by remember { mutableStateOf(Random.nextInt(1, 51)) }
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
        maximoCliques = Random.nextInt(1, 51)
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
                .size(150.dp)
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
                    if (!jogoFinalizado) {
                        Toast.makeText(context, "Fim de jogo", Toast.LENGTH_SHORT).show()
                    }
                }) {
                    Text("Não")
                }
            }
        )
    }
}
```

### 3. Função `JogarPreview()`

Esta função é usada para visualização da UI no Android Studio. Ela permite ver uma prévia do layout sem precisar rodar o aplicativo em um dispositivo real ou emulador.

```kotlin
@Preview(showBackground = true)
@Composable
fun JogarPreview() {
    Atividade1Theme {
        Jogar()
    }
}
```

## Como Executar

1. **Clonar o repositório:** Baixe o código fonte do projeto.
2. **Abrir no Android Studio:** Importe o projeto para o Android Studio.
3. **Executar:** Conecte um dispositivo Android ou inicie um emulador e rode o aplicativo.

## Contribuições

Contribuições são bem-vindas! Se você encontrar problemas ou tiver sugestões de melhorias, sinta-se à vontade para abrir uma issue ou enviar um pull request.

## Licença

Este projeto está licenciado sob os termos da licença MIT. Consulte o arquivo LICENSE para mais detalhes.

---

Com este README, qualquer pessoa deve ser capaz de entender o propósito do projeto, sua estrutura, e como utilizá-lo. Se precisar de mais alguma coisa, estou à disposição!
