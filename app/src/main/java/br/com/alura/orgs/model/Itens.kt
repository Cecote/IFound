package br.com.alura.orgs.model

import android.graphics.Bitmap
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class Itens(
    @PrimaryKey(autoGenerate = true) var id: Long = 0L,
    var itemPerdido: String,
    var situacao: String,
    var local: String,
    var contato: String,
    var img: Bitmap? = null,
    var descricao: String,
) : Parcelable
