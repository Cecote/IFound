package br.com.alura.orgs.ui.activity

import android.content.ClipData.Item
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.com.alura.orgs.databinding.ActivityDetalhesProdutoBinding
import br.com.alura.orgs.model.Itens
import br.com.alura.orgs.ui.activity.FormularioItensActivity

class DetalhesProdutoActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityDetalhesProdutoBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        tentaCarregarProduto()
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

            if (bitmap != null){
                activityDetalhesProdutoImagem.setImageBitmap(bitmap)
            }
            activityDetalhesProdutoNome.text = itemPerdido
            activityDetalhesProdutoDescricao.text = descricao
            activityDetalhesProdutoValor.text = situacao
        }
    }

}