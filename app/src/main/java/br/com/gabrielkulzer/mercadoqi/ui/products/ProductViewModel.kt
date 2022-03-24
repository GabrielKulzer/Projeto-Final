package br.com.gabrielkulzer.mercadoqi.ui.products

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.gabrielkulzer.mercadoqi.R
import br.com.gabrielkulzer.mercadoqi.repository.ProductRepository
import kotlinx.coroutines.launch
import java.lang.Exception

//Recebe o repositório
class ProductViewModel(private val repository: ProductRepository) : ViewModel() {

    private val _productStateEventData = MutableLiveData<ProductState>()
    val productStateEventData: LiveData<ProductState>
    get() = _productStateEventData

    private val _messageEventData = MutableLiveData<Int>()
    val messageEventData: LiveData<Int>
    get() = _messageEventData

    //se id for maior que 0 é update senão é insert
    fun addOrUpdateProduct(name:String,price:Float,quantity:Int,id:Long = 0){
        if (id > 0){
        updateProduct(id,name,price,quantity)
        }else{
            insertProduct(name, price, quantity)
        }
    }

    private fun updateProduct(id:Long, name:String, price: Float, quantity: Int) = viewModelScope.launch{
        try{
            repository.updateProduct(id, name, price, quantity)

            _productStateEventData.value = ProductState.Updated
            _messageEventData.value = R.string.product_updated_sucessfully

            //exibe mensagem ao dar erro
        }catch(ex: Exception){
            _messageEventData.value = R.string.product_error_to_update
            Log.e(TAG,ex.toString())
        }
    }

    private fun insertProduct(name:String, price: Float, quantity: Int) = viewModelScope.launch{
        try{
        val id = repository.insertProduct(name, price, quantity)
            if (id > 0){
            _productStateEventData.value = ProductState.Inserted
                _messageEventData.value = R.string.product_insert_sucessfully
            }
            //exibe mensagem ao dar erro
        }catch(ex: Exception){
        _messageEventData.value = R.string.product_error_to_insert
            Log.e(TAG,ex.toString())
        }
    }

     fun deleteProduct(id: Long) = viewModelScope.launch{
        try{
            if(id > 0){
                repository.deleteProduct(id)
                _productStateEventData.value = ProductState.Deleted
                _messageEventData.value = R.string.product_deleted_sucessfully
            }
        }catch (ex: Exception){
            _messageEventData.value = R.string.product_error_to_delete
            Log.e(TAG, ex.toString())
        }
    }

    //classe representa o estado da view
    sealed class ProductState{
        object Inserted : ProductState()
        object Updated : ProductState()
        object Deleted : ProductState()
    }
    //define a tag para o try catch
    companion object{
        private val TAG = ProductViewModel::class.java.simpleName
    }
}