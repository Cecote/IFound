package br.com.alura.orgs.ui.activity


import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.lifecycleScope
import br.com.alura.orgs.R
import br.com.alura.orgs.database.AppDatabase
import br.com.alura.orgs.databinding.ActivityListaItensBinding
import br.com.alura.orgs.extensions.vaiPara
import br.com.alura.orgs.preferences.dataStore
import br.com.alura.orgs.preferences.usuarioLogadoPreferences
import br.com.alura.orgs.ui.recyclerview.adapter.ListaProdutosAdapter
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream

class ListaItensUsuarioComumActivity : AppCompatActivity() {


    private val adapter = ListaProdutosAdapter(context = this)
    private val binding by lazy {
        ActivityListaItensBinding.inflate(layoutInflater)
    }

    private val itemDao by lazy {
        val db = AppDatabase.instancia(this)
        db.itemDao()
    }

    private val usuarioDao by lazy {
        AppDatabase.instancia(this).usuarioDao()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        configuraRecyclerView()
        configuraFab()
        lifecycleScope.launch {
            launch {
                itemDao.buscaTodos().collect { itens ->
                    adapter.atualiza(itens)
                }
            }

            launch {
                dataStore.data.collect {preferences ->
                    preferences[usuarioLogadoPreferences]?.let { usuarioId ->
                        launch {
                            usuarioDao.buscaPorId(usuarioId).collect {
                                Log.i("ListaProdutos", "OnCreate: $it")
                            }
                        }
                    } ?: vaiParaLogin()

                }
            }
        }


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_lista_itens, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_lista_produtos_sair_do_app -> {
                lifecycleScope.launch {
                    dataStore.edit { preferences ->
                        preferences.remove(usuarioLogadoPreferences)
                    }
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }
    private fun vaiParaLogin() {
        vaiPara(LoginActivity::class.java)
        finish()
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
            val intent = Intent(this, DetalhesProdutoUsuariosActivity::class.java).apply {
                putExtra(CHAVE_PRODUTO_ID, it.id)
            }
            startActivity(intent)
        }
    }
}