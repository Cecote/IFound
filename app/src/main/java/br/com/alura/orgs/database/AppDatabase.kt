package br.com.alura.orgs.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import br.com.alura.orgs.database.converter.Converterd
import br.com.alura.orgs.database.dao.ItemDao
import br.com.alura.orgs.database.dao.UsuarioDao
import br.com.alura.orgs.model.Itens
import br.com.alura.orgs.model.Usuario

@Database(entities = [Itens::class, Usuario::class], version = 2, exportSchema = true)
@TypeConverters(Converterd::class)
abstract class AppDatabase : RoomDatabase(){
    abstract fun itemDao() : ItemDao

    abstract fun usuarioDao() : UsuarioDao

    companion object {
        fun instancia(context : Context) : AppDatabase{
            return Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                "ifound.db"
            ).addMigrations(MIGRATION_1_2)
                .allowMainThreadQueries()
                .build()
        }
    }
}