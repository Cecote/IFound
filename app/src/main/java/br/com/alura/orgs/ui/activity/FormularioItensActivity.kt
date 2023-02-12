package br.com.alura.orgs.ui.activity

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import 	android.widget.ImageView
import kotlin.Any
import android.widget.EditText
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import br.com.alura.orgs.R
import br.com.alura.orgs.dao.ItensDao
import br.com.alura.orgs.databinding.ActivityFormularioItensBinding
import br.com.alura.orgs.model.Itens
import br.com.alura.orgs.ui.recyclerview.adapter.ListaProdutosAdapter
import java.util.jar.Manifest

class FormularioItensActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityFormularioItensBinding.inflate(layoutInflater)
    }
    private var urlzinha:Bitmap? = null
    private lateinit var dialog: AlertDialog
    companion object{
        private val PERMISSAO_GALERIA = android.Manifest.permission.READ_EXTERNAL_STORAGE
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
        val dao = ItensDao()
        botaoSalvar.setOnClickListener {
            val novoItem = criaItem()
            dao.add(novoItem)
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

        return Itens(
            itemPerdido = itemPerdido,
            situacao = situacao,
            local = local,
            contato = contato,
            img = urlzinha
        )
    }
}