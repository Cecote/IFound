package br.com.alura.orgs.ui.activity

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import br.com.alura.orgs.database.AppDatabase
import br.com.alura.orgs.databinding.ActivityFormularioItensBinding
import br.com.alura.orgs.model.Itens

class FormularioItensActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityFormularioItensBinding.inflate(layoutInflater)
    }
    private var idItem = 0L
    private var urlzinha:Bitmap? = null
    private lateinit var dialog: AlertDialog
    companion object{
        private val PERMISSAO_GALERIA = android.Manifest.permission.READ_MEDIA_IMAGES
    }

    private val requestGaleria =
        registerForActivityResult(ActivityResultContracts.RequestPermission()){permissao ->
            if(permissao){
                resultGaleria.launch(
                    Intent(
                        Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                ))
            }else{
                showDialogPermission()
            }
        }

    private val resultGaleria =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {result ->
        val bitmap:Bitmap = if(Build.VERSION.SDK_INT < 28){
            MediaStore.Images.Media.getBitmap(
                baseContext.contentResolver,
                result.data?.data
            )
        }else{
            val source = ImageDecoder.createSource(
                this.contentResolver,
                result.data?.data!!
            )
            ImageDecoder.decodeBitmap(source)
        }
//            val value = Intent(this, ListaProdutosAdapter::class.java)
//            value.putExtra("bitMapImg", bitmap)
//            startActivity(value)
            urlzinha = bitmap
//            val extras = Bundle()
//            extras.putParcelable("bitmapIMG", bitmap)
//            val intentForms = Intent(this, DetalhesProdutoActivity::class.java )
//            intentForms.putExtras(extras)
//            startActivity(intentForms)
            binding.activityFormularioItemImagem.setImageBitmap(bitmap)

        }
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        title = "Cadastrar item"
        binding.activityFormularioEscolherImg.setOnClickListener{
            verificaPermissaoGaleria()
        }
        configuraBotaoSalvar()


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

        if(situacao!=null) {
            title = "Editar Item"
            idItem = id
            binding.activityFormularioItemImagem.setImageBitmap(item.img)
            binding.activityFormularioItemItemperdido.setText(item.itemPerdido)
            binding.activityFormularioItemDescricao.setText(item.descricao)
            binding.activityFormularioItemSituacao.setText(item.situacao)
            binding.activityFormularioItemLocal.setText(item.local)
            binding.activityFormularioContato.setText(item.contato)
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun verificaPermissaoGaleria(){
        val permissaoGaleria = verificaPermissao(PERMISSAO_GALERIA)

        when{
            permissaoGaleria -> {
                resultGaleria.launch(
                    Intent(
                        Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                    ))
            }

            shouldShowRequestPermissionRationale(PERMISSAO_GALERIA) -> showDialogPermission()

            else -> requestGaleria.launch(PERMISSAO_GALERIA)
        }
    }

    private fun showDialogPermission(){
        val builder = AlertDialog.Builder(this)
            .setTitle("Ateção")
            .setMessage("Precisamos do acesso à galeria do dispositivo, deseja permitir agora?")
            .setNegativeButton("Não"){_, _ ->
                dialog.dismiss()
            }
            .setPositiveButton("Sim"){_, _ ->
                val intent = Intent(
                    Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                    Uri.fromParts("package", packageName, null)
                )
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                dialog.dismiss()
            }
        dialog = builder.create()
        dialog.show()

    }
    private fun verificaPermissao(permissao: String) =
        ContextCompat.checkSelfPermission(this, permissao) == PackageManager.PERMISSION_GRANTED


    private fun configuraBotaoSalvar() {
        val botaoSalvar = binding.activityFormulariosalvar
        val db = AppDatabase.instancia(this)
        val itemDao = db.itemDao()

        botaoSalvar.setOnClickListener {
            val novoItem = criaItem()
            if(idItem > 0){
                itemDao.edita(novoItem)
            }else {
                itemDao.salva(novoItem)
            }
            finish()
        }
    }

    private fun criaItem(): Itens {
        val campoItemPerdido = binding.activityFormularioItemItemperdido
        val itemPerdido = campoItemPerdido.text.toString()

        val campoSituacao = binding.activityFormularioItemSituacao
        val situacao = campoSituacao.text.toString()

        val campoLocal = binding.activityFormularioItemLocal
        val local = campoLocal.text.toString()

        val campoContato = binding.activityFormularioContato
        val contato = campoContato.text.toString()

        val campoDescricao = binding.activityFormularioItemDescricao
        val descricao = campoDescricao.text.toString()

        return Itens(
            id = idItem,
            itemPerdido = itemPerdido,
            situacao = situacao,
            local = local,
            contato = contato,
            img = urlzinha,
            descricao = descricao
        )
    }
}