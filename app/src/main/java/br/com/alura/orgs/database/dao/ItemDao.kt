package br.com.alura.orgs.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import br.com.alura.orgs.model.Itens

@Dao
interface ItemDao {
    @Query("SELECT * FROM Itens")
    fun buscaTodos() : List<Itens>

    @Insert
    fun salva(vararg item : Itens)

    @Delete
    fun remove(vararg item : Itens)

    @Update
    fun edita(vararg item : Itens)
    @Query("SELECT * FROM Itens WHERE id = :id")
    fun buscaPorId(id: Long) : Itens?
}