package com.example.prova_2_kotlin

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.gson.Gson

data class Jogador(

    var nome : String,
    var level : Double,
    var poderTotal : Double,
    var equipamento : Double,
    var modificador : Double


) {

    companion object {
        private val gson = Gson()


        fun fromJson(json: String?): Jogador {
            return gson.fromJson(json, Jogador::class.java)
        }


        fun toJson(jogador: Jogador): String {
            return gson.toJson(jogador)
        }
    }


}