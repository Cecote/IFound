package br.com.alura.orgs.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import br.com.alura.orgs.model.Itens
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemDao {
    @Query("SELECT * FROM Itens")
    fun buscaTodos() : Flow<List<Itens>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun salva(vararg item : Itens)

    @Delete
    fun remove(vararg item : Itens)

    @Query("SELECT * FROM Itens WHERE id = :id")
    fun buscaPorId(id: Long) : Flow<Itens?>
}