package br.com.gabrielkulzer.mercadoqi.repository

import androidx.lifecycle.LiveData
import br.com.gabrielkulzer.mercadoqi.data.db.entity.ProductEntity

//interface a ser implementada por outras classes
//ligaação entre o view model e o banco de dados
interface ProductRepository {


    suspend fun insertProduct(name: String, price: Float, quantity: Int): Long

    suspend fun updateProduct(id: Long, name: String, price: Float, quantity: Int)

    suspend fun deleteProduct(id: Long)

    suspend fun deleteAllProducts()

    suspend fun getAllProducts(): List<ProductEntity>
}
