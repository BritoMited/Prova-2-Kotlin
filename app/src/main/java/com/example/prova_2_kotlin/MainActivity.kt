package com.example.prova_2_kotlin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.prova_2_kotlin.ui.theme.Prova2KotlinTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            LayoutMain()

        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LayoutCadastroPlayers(navController: NavController, list: MutableList<Jogador>) {

    var nome by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(text = "Nome: ")
        TextField(value = nome, onValueChange = {nome = it}, label = { Text(text = "Insira o nome do Jogador: ")})

        Button(onClick = {

            val jogador = Jogador(nome, 0.0,0.0,0.0,0.0)
            list.add(jogador)

        }) {

            Text(text = "Cadastrar Jogador")
            
        }

        Button(onClick = {

            navController.navigate("listaJogadores")

        }) {
            Text(text = "Ir para lista de Jogadores")
        }

    }


}

@Composable
fun LayoutListaJogadores(navController: NavController, listaJogadores: List<Jogador>) {


    Column {

        LazyColumn{
            items(listaJogadores){
                    jogador ->

                Button(onClick = {

                    val jogadorJson = Jogador.toJson(jogador)

                    navController.navigate("detalhes/$jogadorJson")


                }) {

                    Text(text = "Jogador: ${jogador.nome} )")
                }

            }

        }


    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Button(onClick = {

            navController.navigate("cadastro")

        }) {
            Text(text = "Voltar para Cadastro")
        }
    }



}



@Composable
fun LayoutDetalhesJogadores(navController: NavController, jogadorResp: Jogador,  listaJogadores: List<Jogador>) {


    var poderTotal by remember { mutableStateOf(0.0) }
    var level by remember { mutableStateOf(0.0) }
    var equipamento by remember { mutableStateOf(0.0) }
    var modificador by remember { mutableStateOf(0.0) }



    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        poderTotal = level + equipamento + modificador
        jogadorResp.poderTotal = poderTotal


        Text(text = "Nome: ${jogadorResp.nome}",  fontSize = 22.sp)

        Text(text = "Poder Total: ${jogadorResp.poderTotal}",  fontSize = 22.sp)


        Row(modifier = Modifier
            .padding(15.dp)) {

            Button(onClick = {

                level--
                jogadorResp.level = level

            }) {
                Text(text = "-",  fontSize = 17.sp)
            }
            Text(text = "level: ${jogadorResp.level}", fontSize = 22.sp)
            Button(onClick = {

                level++
                jogadorResp.level = level

            }) {
                Text(text = "+",  fontSize = 17.sp)
            }
        }

        Row(modifier = Modifier
            .padding(15.dp)) {

            Button(onClick = {

                equipamento--
                jogadorResp.equipamento = equipamento

            }) {
                Text(text = "-",  fontSize = 17.sp)
            }
            Text(text = "equipamento: ${jogadorResp.equipamento}",  fontSize = 22.sp)
            Button(onClick = {

                equipamento++
                jogadorResp.equipamento = equipamento

            }) {
                Text(text = "+",  fontSize = 17.sp)
            }
        }

        Row(modifier = Modifier
            .padding(15.dp)) {

            Button(onClick = {

                modificador--
                jogadorResp.modificador = modificador

            }) {
                Text(text = "-",  fontSize = 17.sp)
            }
            Text(text = "Modificadores: ${jogadorResp.modificador}",  fontSize = 22.sp)
            Button(onClick = {

                modificador++
                jogadorResp.modificador = modificador

            }) {
                Text(text = "+",  fontSize = 17.sp)
            }
        }




    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Button(onClick = {
            // Salva o jogador na lista antes de navegar
            listaJogadores.find { it.nome == jogadorResp.nome }?.let {
                // Atualiza o jogador existente
                it.poderTotal = jogadorResp.poderTotal
                it.level = jogadorResp.level
                it.equipamento = jogadorResp.equipamento
                it.modificador = jogadorResp.modificador
                it.poderTotal = jogadorResp.poderTotal // Certifique-se de recalcular se necessário
            }

            navController.navigate("listaJogadores")
        }) {
            Text(text = "Voltar para Lista")
        }
    }
    


}

@Composable
fun LayoutMain() {

    val navController = rememberNavController()
    val listaJogadores = remember { mutableStateListOf<Jogador>() }


    NavHost(navController = navController, startDestination = "cadastro") {
        composable("cadastro") {
            LayoutCadastroPlayers(navController, listaJogadores)
        }

        composable("listaJogadores") {
            LayoutListaJogadores(navController, listaJogadores)
        }

        composable("detalhes/{jogadorJson}"){ backStackEntry ->

            val jogadorJson = backStackEntry.arguments?.getString("jogadorJson")
            val jogadorResp = Jogador.fromJson(jogadorJson)

            LayoutDetalhesJogadores(navController, jogadorResp, listaJogadores)
        }

    }
}




