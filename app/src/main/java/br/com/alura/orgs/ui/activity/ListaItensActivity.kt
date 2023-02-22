package br.com.alura.orgs.ui.activity

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import br.com.alura.orgs.R
import br.com.alura.orgs.dao.ItensDao
import br.com.alura.orgs.databinding.ActivityListaItensBinding
import br.com.alura.orgs.ui.recyclerview.adapter.ListaProdutosAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.io.File
import java.io.FileOutputStream

class ListaItensActivity : AppCompatActivity() {

    private val dao = ItensDao()
    private val adapter = ListaProdutosAdapter(context = this, itens = dao.buscaTodos())
    private val binding by lazy {
        ActivityListaItensBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        configuraRecyclerView()
        configuraFab()
    }

    override fun onResume() {
        super.onResume()
        adapter.atualiza(dao.buscaTodos())

    }

    private fun configuraFab() {
        val fab = binding.floatingActionButton
        fab.setOnClickListener {
            vaiParaFormularioItens()
        }
    }

    private fun vaiParaFormularioItens() {
        val intent = Intent(this, FormularioItensActivity::class.java)
        startActivity(intent)
    }

    private fun configuraRecyclerView() {
        val recyclerView = binding.listagemDeItens
        recyclerView.adapter = adapter

        // implementação do listener para abrir a Activity de detalhes do produto
        // com o produto clicado
        adapter.quandoClicaNoItem = {

            val tempFile = File.createTempFile("tempFile", null, externalCacheDir)
            val fos = FileOutputStream(tempFile)
            it.img?.compress(Bitmap.CompressFormat.PNG, 100, fos)
            fos.flush()
            fos.close()
            val intent = Intent(this, DetalhesProdutoActivity::class.java).apply {
                putExtra("itemPerdido", it.itemPerdido)
                putExtra("situacao", it.situacao)
                putExtra("descricao", it.descricao)
                putExtra("imagem",tempFile.absolutePath)
            }
            startActivity(intent)
        }
    }
}


