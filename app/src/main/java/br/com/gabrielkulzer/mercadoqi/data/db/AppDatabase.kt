package br.com.gabrielkulzer.mercadoqi.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import br.com.gabrielkulzer.mercadoqi.data.db.dao.ProductDAO
import br.com.gabrielkulzer.mercadoqi.data.db.entity.ProductEntity
//entidade a ser utilizada no banco
@Database(entities = [ProductEntity::class], version = 1)
//classe só pode ser herdada, cria funcionalidades para que outras classes sejam utilizadas
abstract class AppDatabase : RoomDatabase() {

    abstract val productDAO: ProductDAO
    //apenas uma instancia
    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            synchronized(this) {
                var instance: AppDatabase? = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context,
                        AppDatabase::class.java,
                        "app_database"
                    ).build()
                }

                return instance
            }
        }
    }
}