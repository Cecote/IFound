package br.com.alura.orgs.ui.activity


import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import br.com.alura.orgs.database.AppDatabase
import br.com.alura.orgs.databinding.ActivityListaItensVisitanteBinding
import br.com.alura.orgs.ui.recyclerview.adapter.ListaProdutosAdapter
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream


class ListaItensVisitanteActivity : AppCompatActivity() {

    private val adapter = ListaProdutosAdapter(context = this)
    private val binding by lazy {
        ActivityListaItensVisitanteBinding.inflate(layoutInflater)
    }

    private val itemDao by lazy {
        val db = AppDatabase.instancia(this)
        db.itemDao()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        configuraRecyclerView()
        lifecycleScope.launch {
            launch {
                itemDao.buscaTodos().collect { itens ->
                    adapter.atualiza(itens)
                }
            }
        }
    }

    private fun configuraRecyclerView() {
        val recyclerView = binding.listagemDeItensVisitante
        recyclerView.adapter = adapter

        // implementação do listener para abrir a Activity de detalhes do produto
        // com o produto clicado
        adapter.quandoClicaNoItem = {

            val tempFile = File.createTempFile("tempFile", null, externalCacheDir)
            val fos = FileOutputStream(tempFile)
            it.img?.compress(Bitmap.CompressFormat.PNG, 100, fos)
            fos.flush()
            fos.close()
            val intent = Intent(this, DetalhesProdutoUsuariosActivity::class.java).apply {
                putExtra(CHAVE_PRODUTO_ID, it.id)
            }
            startActivity(intent)
        }
    }

}