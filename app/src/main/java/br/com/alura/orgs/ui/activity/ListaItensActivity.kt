package br.com.alura.orgs.ui.activity

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import br.com.alura.orgs.database.AppDatabase
import br.com.alura.orgs.databinding.ActivityListaItensBinding
import br.com.alura.orgs.ui.recyclerview.adapter.ListaProdutosAdapter
import java.io.File
import java.io.FileOutputStream

class ListaItensActivity : AppCompatActivity() {


    private val adapter = ListaProdutosAdapter(context = this)
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
        val db = AppDatabase.instancia(this)
        val itemDao = db.itemDao()
        adapter.atualiza(itemDao.buscaTodos())
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
                putExtra("contato", it.contato)
                putExtra("local", it.local)
                putExtra("id", it.id)
            }
            startActivity(intent)
        }
    }
}


