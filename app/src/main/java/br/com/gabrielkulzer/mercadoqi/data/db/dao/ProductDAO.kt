package br.com.gabrielkulzer.mercadoqi.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import br.com.gabrielkulzer.mercadoqi.data.db.entity.ProductEntity

@Dao
//criando as funcionalidades para utilizar na entidade product
//uma interface somente pode especificar quais os métodos uma classe que implementa a interface deve definir
interface ProductDAO {

    //utilizando funções suspensas que podem ser iniciadas,pausadas e retomadas (coroutines)
    //cadastrando produto no banco
    @Insert
    suspend fun insert(product: ProductEntity): Long
    //Atualizando dados do produto
    @Update
    suspend fun update(product: ProductEntity)
    //deletando produto selecionado
    @Query("DELETE FROM product WHERE id = :id")
    suspend fun delete(id: Long)
    //deletando todos os produtos
    @Query("DELETE FROM product")
    suspend fun deleteAll()
    //selecionando todos os produtos
    @Query("SELECT * FROM product")
    suspend fun getAll(): List<ProductEntity>
}