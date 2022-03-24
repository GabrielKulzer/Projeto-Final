package br.com.gabrielkulzer.mercadoqi.ui.productlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.gabrielkulzer.mercadoqi.data.db.entity.ProductEntity
import br.com.gabrielkulzer.mercadoqi.repository.ProductRepository
import kotlinx.coroutines.launch

//controla a view
class ProductListViewModel(
    private val repository:ProductRepository
) : ViewModel() {
    //reage as alterações dos produtos
    private val _allProductsEvent = MutableLiveData<List<ProductEntity>>()
    val allProductsEvent: LiveData<List<ProductEntity>>
    get() = _allProductsEvent

    fun getProducts() = viewModelScope.launch{
        _allProductsEvent.postValue(repository.getAllProducts())
    }

}