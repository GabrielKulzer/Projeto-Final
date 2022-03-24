package br.com.gabrielkulzer.mercadoqi.ui.productlist


import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import br.com.gabrielkulzer.mercadoqi.R
import br.com.gabrielkulzer.mercadoqi.data.db.AppDatabase
import br.com.gabrielkulzer.mercadoqi.data.db.dao.ProductDAO
import br.com.gabrielkulzer.mercadoqi.extension.navigateWithAnimations
import br.com.gabrielkulzer.mercadoqi.repository.DatabaseDataSource
import br.com.gabrielkulzer.mercadoqi.repository.ProductRepository
import kotlinx.android.synthetic.main.product_list_fragment.*

class ProductListFragment : Fragment(R.layout.product_list_fragment) {

    private val viewModel: ProductListViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val productDAO: ProductDAO =
                    AppDatabase.getInstance(requireContext()).productDAO

                val repository: ProductRepository = DatabaseDataSource(productDAO)
                return ProductListViewModel(repository) as T
            }
        }
    }
    //manipula os elementos que estão o layout
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModelEvents()
        configureViewListeners()

    }
    //observa se o item foi clicado e faz a navegação
    private fun observeViewModelEvents() {
        viewModel.allProductsEvent.observe(viewLifecycleOwner) { allProducts ->
            val productListAdapter = ProductListAdapter(allProducts).apply {
                onItemClick = { product ->
                    //navagação utilizando fragmento product
                        val directions = ProductListFragmentDirections
                            .actionProductListFragmentToProductFragment(product)
                        findNavController().navigateWithAnimations(directions)
                }
            }

            with(recycler_products) {
                setHasFixedSize(true)
                adapter = productListAdapter
            }
        }
    }

    //função para atualizar a lista ao cadastrar um novo produto
    override fun onResume() {
        super.onResume()
        viewModel.getProducts()
    }

    //navegação ao clicar no add button da tela de lista para tela de cadastro
    private fun configureViewListeners(){
        fabAddProduct.setOnClickListener{
            findNavController().navigateWithAnimations(R.id.action_productListFragment_to_productFragment)
        }
    }
}