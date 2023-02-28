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
import br.com.alura.orgs.databinding.ActivityDetalhesProdutoBinding
import br.com.alura.orgs.model.Itens
import br.com.alura.orgs.ui.activity.FormularioItensActivity

private const val TAG = "DetalhesProduto"
class DetalhesProdutoActivity : AppCompatActivity() {

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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_detalhes_itens_remover -> {
                Log.i(TAG, "onOptionsItemSelected: remover")
            }
            R.id.menu_detalhes_itens_editar -> {
                Log.i(TAG, "onOptionsItemSelected: editar")
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun tentaCarregarProduto() {
        // tentativa de buscar o produto se ele existir,
        // caso contrário, finalizar a Activity
        preencheCampos()

    }

    private fun preencheCampos() {
        with(binding) {
            val path = intent.getStringExtra("imagem")
            val bitmap = BitmapFactory.decodeFile(path)
            val itemPerdido = intent.getStringExtra("itemPerdido")
            val situacao = intent.getStringExtra("situacao")
            val descricao = intent.getStringExtra("descricao")

            if (bitmap != null) {
                activityDetalhesProdutoImagem.setImageBitmap(bitmap)
            }
            activityDetalhesProdutoNome.text = itemPerdido
            activityDetalhesProdutoDescricao.text = descricao
            activityDetalhesProdutoValor.text = situacao
        }
    }

}