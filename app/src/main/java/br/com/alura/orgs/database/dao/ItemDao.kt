package br.com.alura.orgs.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import br.com.alura.orgs.model.Itens

@Dao
interface ItemDao {
    @Query("SELECT * FROM Itens")
    fun buscaTodos() : List<Itens>

    @Insert
    fun salva(vararg item : Itens)
}