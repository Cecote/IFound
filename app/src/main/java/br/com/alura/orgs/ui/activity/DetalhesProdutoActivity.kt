package br.com.alura.orgs.ui.activity

import android.content.ClipData.Item
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import br.com.alura.orgs.R
import br.com.alura.orgs.database.AppDatabase
import br.com.alura.orgs.databinding.ActivityDetalhesProdutoBinding
import br.com.alura.orgs.model.Itens
import br.com.alura.orgs.ui.activity.FormularioItensActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream



class DetalhesProdutoActivity : AppCompatActivity() {

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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detalhes_itens, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(itemAux: MenuItem): Boolean {
        when (itemAux.itemId) {
                R.id.menu_detalhes_itens_remover -> {
                        item2?.let {
                            lifecycleScope.launch {
                                itemDao.remove(it)
                                finish()
                            }
                        }


                }
                R.id.menu_detalhes_itens_editar -> {
                    val tempFile = File.createTempFile("tempFile", null, externalCacheDir)
                    val fos = FileOutputStream(tempFile)
                    item2?.img?.compress(Bitmap.CompressFormat.PNG, 100, fos)
                    fos.flush()
                    fos.close()
                    Intent(this, FormularioItensActivity::class.java).apply {
                        putExtra(CHAVE_PRODUTO_ID, itemId)
                        startActivity(this)
                    }
                }
            }

        return super.onOptionsItemSelected(itemAux)
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