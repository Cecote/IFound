package br.com.alura.orgs.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import br.com.alura.orgs.database.AppDatabase
import br.com.alura.orgs.databinding.ActivityFormularioCadastroUsuarioBinding
import br.com.alura.orgs.model.Usuario

class FormularioCadastroUsuarioActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityFormularioCadastroUsuarioBinding.inflate(layoutInflater)
    }

    private val dao by lazy {
        AppDatabase.instancia(this).usuarioDao()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        configuraBotaoCadastrar()
    }

    private fun configuraBotaoCadastrar() {
        val db = AppDatabase.instancia(this)
        val usuarioDao = db.usuarioDao()
        binding.activityFormularioCadastroBotaoCadastrar.setOnClickListener {
            val novoUsuario = criaUsuario()
            Log.i("CadastroUsuario", "onCreate: $novoUsuario")
            try {
                usuarioDao.salva(novoUsuario)
                finish()
            } catch (e: Exception) {
                Log.e("CadastroUsuario", "configuraBotaoCadastrar", e)
                Toast.makeText(this@FormularioCadastroUsuarioActivity, "Falha ao cadastrar usuario!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun criaUsuario(): Usuario {
        val usuario = binding.activityFormularioCadastroUsuario.text.toString()
        val nome = binding.activityFormularioCadastroNome.text.toString()
        val senha = binding.activityFormularioCadastroSenha.text.toString()
        return Usuario(usuario, nome, senha)
    }

}