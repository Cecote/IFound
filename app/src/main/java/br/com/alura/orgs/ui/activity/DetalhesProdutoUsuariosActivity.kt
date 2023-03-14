package br.com.alura.orgs.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import br.com.alura.orgs.database.AppDatabase
import br.com.alura.orgs.databinding.ActivityDetalhesProdutoBinding
import br.com.alura.orgs.model.Itens
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class DetalhesProdutoUsuariosActivity : AppCompatActivity(){

    private var itemId: Long = 0L
    private var item2: Itens? = null
    private val binding by lazy {
        ActivityDetalhesProdutoBinding.inflate(layoutInflater)
    }
    private val itemDao by lazy {
        AppDatabase.instancia(this).itemDao()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        tentaCarregarProduto()
    }

    override fun onResume() {
        super.onResume()
        buscaItem()
    }

    private fun buscaItem() {
        lifecycleScope.launch {
            itemDao.buscaPorId(itemId).collect { itemEncontrado ->
                item2 = itemEncontrado
                item2?.let {
                    preencheCampos(it)
                } ?: finish()
            }
        }

    }

    private fun tentaCarregarProduto() {
        // tentativa de buscar o produto se ele existir,
        // caso contrário, finalizar a Activity
        itemId = intent.getLongExtra(CHAVE_PRODUTO_ID, 0L)

    }

    private fun preencheCampos(item: Itens) {
        with(binding) {

            if (item.img != null) {
                activityDetalhesProdutoImagem.setImageBitmap(item.img)
            }
            activityDetalhesProdutoNome.text = item.itemPerdido
            activityDetalhesProdutoDescricao.text = item.descricao
            activityDetalhesProdutoValor.text = item.situacao
        }
    }

}