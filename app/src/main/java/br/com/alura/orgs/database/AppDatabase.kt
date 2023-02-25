package br.com.alura.orgs.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import br.com.alura.orgs.database.converter.Converterd
import br.com.alura.orgs.database.dao.ItemDao
import br.com.alura.orgs.model.Itens

@Database(entities = [Itens::class], version = 1)
@TypeConverters(Converterd::class)
abstract class AppDatabase : RoomDatabase(){
    abstract fun itemDao() : ItemDao
}