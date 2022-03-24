package br.com.gabrielkulzer.mercadoqi.ui.products


import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import br.com.gabrielkulzer.mercadoqi.R
import br.com.gabrielkulzer.mercadoqi.data.db.AppDatabase
import br.com.gabrielkulzer.mercadoqi.data.db.dao.ProductDAO
import br.com.gabrielkulzer.mercadoqi.repository.DatabaseDataSource
import br.com.gabrielkulzer.mercadoqi.repository.ProductRepository
import br.com.gabrielkulzer.mercadoqi.extension.hideKeyBoard
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import kotlinx.android.synthetic.main.product_fragment.*
import kotlinx.android.synthetic.main.product_list_fragment.*

//inflando layout com fragment
class ProductFragment : Fragment(R.layout.product_fragment) {

    private val viewModel: ProductViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val productDAO: ProductDAO =
                    AppDatabase.getInstance(requireContext()).productDAO

                val repository: ProductRepository = DatabaseDataSource(productDAO)
                return ProductViewModel(repository) as T
            }
        }
    }

    private val args: ProductFragmentArgs by navArgs()

    //manipula os elementos que estão o layout
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //se for nulo não entra no let
        args.product?.let { product ->
            button_product.text = getString(R.string.product_btn_update)
            input_name.setText(product.name)
            input_price.setText(product.price)
            input_quantity.setText2(product.quantity)

            button_delete_product.visibility= View.VISIBLE
        }
        observeEvents()
        setListeners()

    }
    //Observa qual função foi utilizada, limpa campos, esconde o teclado e volta a tela de listas
    private fun observeEvents() {
        viewModel.productStateEventData.observe(viewLifecycleOwner) { productState ->
            when (productState) {
                //observando insert
                is ProductViewModel.ProductState.Inserted,
                //observando update
                is ProductViewModel.ProductState.Updated,
                    //observando delete
                is ProductViewModel.ProductState.Deleted -> {
                    clearFields()
                    hideKeyboard()
                    requireView().requestFocus()
                    //voltando a tela de listas
                    findNavController().popBackStack()
                }
            }
        }
        //snackbar mensagem de sucesso ou erro ao adicionar produto
        viewModel.messageEventData.observe(viewLifecycleOwner) { stringResId ->
            Snackbar.make(requireView(), stringResId, Snackbar.LENGTH_LONG).show()
        }
    }
   //função limpar campos
    private fun clearFields() {
        input_name.text?.clear()
        input_price.text?.clear()
        input_quantity.text?.clear()
    }
    //função esconder teclado
    private fun hideKeyboard() {
        val parentActivity = requireActivity()
        if (parentActivity is AppCompatActivity) {
            parentActivity.hideKeyBoard()
        }
    }
    // escuta as funções
    private fun setListeners() {
        button_product.setOnClickListener {
            val name = input_name.text.toString()
            val price = input_price.text.toString().toFloat()
            val quantity = input_quantity.text.toString().toInt()
            // se for inserção o id será 0 e fará o cadastro, senão vai ser atualizado
            viewModel.addOrUpdateProduct(name, price, quantity, args.product?.id ?: 0)
        }
        button_delete_product.setOnClickListener{
            viewModel.deleteProduct(args.product?.id ?: 0)
        }
    }
}

private fun TextInputEditText.setText(price: Float) {

}
private fun TextInputEditText.setText2(quantity: Int) {

}