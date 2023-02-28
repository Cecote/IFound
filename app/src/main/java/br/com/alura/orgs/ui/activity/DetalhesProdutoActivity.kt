package br.com.alura.orgs.ui.activity

import android.content.ClipData.Item
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import br.com.alura.orgs.R
import br.com.alura.orgs.database.AppDatabase
import br.com.alura.orgs.databinding.ActivityDetalhesProdutoBinding
import br.com.alura.orgs.model.Itens
import br.com.alura.orgs.ui.activity.FormularioItensActivity

private const val TAG = "DetalhesProduto"
class DetalhesProdutoActivity : AppCompatActivity() {

    private lateinit var item2 : Itens
    private val binding by lazy {
        ActivityDetalhesProdutoBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        tentaCarregarProduto()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detalhes_itens, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(itemAux: MenuItem): Boolean {
        if(::item2.isInitialized){
            val db = AppDatabase.instancia(this)
            val itemDao = db.itemDao()
            when (itemAux.itemId) {
                R.id.menu_detalhes_itens_remover -> {
                    itemDao.remove(item2)
                    finish()

                }
                R.id.menu_detalhes_itens_editar -> {
                    Log.i(TAG, "onOptionsItemSelected: editar")
                }
            }
        }
        return super.onOptionsItemSelected(itemAux)
    }

    private fun tentaCarregarProduto() {
        // tentativa de buscar o produto se ele existir,
        // caso contrário, finalizar a Activity
        val path = intent.getStringExtra("imagem")
        val bitmap = BitmapFactory.decodeFile(path)
        val itemPerdido = intent.getStringExtra("itemPerdido")
        val situacao = intent.getStringExtra("situacao")
        val descricao = intent.getStringExtra("descricao")
        val contato = intent.getStringExtra("contato")
        val id = intent.getLongExtra("id", -1L)
        val local = intent.getStringExtra("local")
        val item = Itens(
            id = id,
            itemPerdido = itemPerdido.toString(),
            situacao = situacao.toString(),
            descricao = descricao.toString(),
            contato = contato.toString(),
            local = local.toString(),
            img = bitmap
        )
        item2 = item
        preencheCampos(item)
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