package br.com.alura.orgs.model

import android.graphics.Bitmap

data class Itens(
        val itemPerdido: String,
        val situacao: String,
        val local: String,
        val contato: String,
        val img: Bitmap? = null
)
