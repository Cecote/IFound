package br.com.alura.orgs.ui.recyclerview.adapter

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.alura.orgs.R
import br.com.alura.orgs.databinding.ProdutoItemBinding
import br.com.alura.orgs.model.Itens
import android.content.Intent
import android.graphics.BitmapFactory
import android.widget.ImageView
import kotlin.coroutines.coroutineContext


class ListaProdutosAdapter(
    private val context: Context,
    itens: List<Itens> = emptyList(),
    // declaração da função para o listener do adapter
    var quandoClicaNoItem: (produto: Itens) -> Unit = {}
) : RecyclerView.Adapter<ListaProdutosAdapter.ViewHolder>() {

    private val itens = itens.toMutableList()
    inner class ViewHolder(private val binding: ProdutoItemBinding, private val context: Context) : RecyclerView.ViewHolder(binding.root){

        // Considerando que o ViewHolder modifica de valor com base na posição
        // é necessário o uso de properties mutáveis, para evitar nullables
        // utilizamos o lateinit, properties que podem ser inicializar depois
        private lateinit var item: Itens

        init {
            // implementação do listener do adapter
            itemView.setOnClickListener {
                // verificação da existência de valores em property lateinit
                if (::item.isInitialized) {
                    quandoClicaNoItem(item)
                }
            }
        }
        fun vincula(item: Itens) {
            this.item = item
            val itemPerdido = binding.produtoItemItemPerdido
            itemPerdido.text = item.itemPerdido

            val situacao = binding.produtoItemSituacao
            situacao.text = item.situacao

            val local = binding.produtoItemLocal
            local.text = item.local

            val contato = binding.produtoItemContato
            contato.text = item.contato

            // uma possível alternativa para esconder o container da img caso o usuário nao coloque nenhuma
            val visibility = if(item.img != null){
                View.VISIBLE
            }else{
                View.GONE
            }
            binding.imageView.visibility = visibility


            val icon:Bitmap = BitmapFactory.decodeResource(context.resources,
                R.drawable.erro)
            if(item.img == null){
                binding.imageView.setImageBitmap(icon)
            }else{
                binding.imageView.setImageBitmap(item.img)
            }


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(context)
        val binding = ProdutoItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding, context)
    }

    override fun getItemCount(): Int {
        return itens.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = itens[position]
        holder.vincula(item)

    }

    fun atualiza(itens: List<Itens>) {
        this.itens.clear()
        this.itens.addAll(itens)
        notifyDataSetChanged()
    }

}
