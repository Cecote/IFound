package br.com.alura.orgs.model

import android.graphics.Bitmap
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class Itens(
        @PrimaryKey(autoGenerate = true) val id: Long = 0L,
        val itemPerdido: String,
        val situacao: String,
        val local: String,
        val contato: String,
        val img: Bitmap? = null,
        val descricao: String,
) : Parcelable
