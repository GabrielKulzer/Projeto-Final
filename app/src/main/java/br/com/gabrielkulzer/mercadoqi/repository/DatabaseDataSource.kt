package br.com.gabrielkulzer.mercadoqi.repository

import br.com.gabrielkulzer.mercadoqi.data.db.dao.ProductDAO
import br.com.gabrielkulzer.mercadoqi.data.db.entity.ProductEntity


class DatabaseDataSource(private val productDAO: ProductDAO) : ProductRepository {

    override suspend fun insertProduct(name: String, price: Float, quantity: Int): Long {
        val product = ProductEntity(
            name = name,
            price = price,
            quantity = quantity
        )

        return productDAO.insert(product)
    }

    override suspend fun updateProduct(id: Long, name: String, price: Float, quantity: Int) {
        val product = ProductEntity(
            id = id,
            name = name,
            price = price,
            quantity = quantity
        )

         productDAO.update(product)
    }

    override suspend fun deleteProduct(id: Long) {
        productDAO.delete(id)
    }

    override suspend fun deleteAllProducts() {
        productDAO.deleteAll()
    }

    override suspend fun getAllProducts(): List<ProductEntity> {
        return productDAO.getAll()
    }
}