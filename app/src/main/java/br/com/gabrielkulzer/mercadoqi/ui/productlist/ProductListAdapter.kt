package br.com.gabrielkulzer.mercadoqi.ui.productlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.gabrielkulzer.mercadoqi.R
import br.com.gabrielkulzer.mercadoqi.data.db.entity.ProductEntity
import kotlinx.android.synthetic.main.product_item.view.*

class ProductListAdapter(
    private val products: List<ProductEntity>
) : RecyclerView.Adapter<ProductListAdapter.ProductListViewHolder>() {

    var onItemClick : ((entity: ProductEntity) -> Unit)? = null

    //inflando item de layout, responsável por criar os itens visuais
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.product_item,parent,false)

        return ProductListViewHolder(view)
    }
    //tem a finalidade de definir os atributos de exibição com base nos dados. Basicamente é invocado quando um item precisa ser exibido para o usuário.
    override fun onBindViewHolder(holder: ProductListViewHolder, position: Int) {
        holder.bindView(products[position])
    }
    //retorna a quantidade de itens no recycleview
    override fun getItemCount() = products.size
    //inner class para acessar as variaveis do listAdapter
    inner class ProductListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private val textViewProductName : TextView = itemView.text_product_name
        private val textViewProductPrice : TextView = itemView.float_product_price
        private val textViewProductQuantity : TextView = itemView.int_product_quantity


        fun bindView(product: ProductEntity){
            textViewProductName.text = product.name
            textViewProductPrice.text = product.price.toString()
            textViewProductQuantity.text = product.quantity.toString()

            //cadastra objeto do tipo view
            itemView.setOnClickListener{
                onItemClick?.invoke(product)
            }
        }
    }
}