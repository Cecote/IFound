package br.com.alura.orgs.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import br.com.alura.orgs.database.converter.Converterd
import br.com.alura.orgs.database.dao.ItemDao
import br.com.alura.orgs.model.Itens

@Database(entities = [Itens::class], version = 1)
@TypeConverters(Converterd::class)
abstract class AppDatabase : RoomDatabase(){
    abstract fun itemDao() : ItemDao

    companion object {
        fun instancia(context : Context) : AppDatabase{
            return Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                "ifound.db"
            ).allowMainThreadQueries()
                .build()
        }
    }
}