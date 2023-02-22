package br.com.alura.orgs.model

import android.graphics.Bitmap
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Itens(
        val itemPerdido: String,
        val situacao: String,
        val local: String,
        val contato: String,
        val img: Bitmap? = null,
        val descricao: String,
) : Parcelable
