package com.example.catalogocursos.ui

import android.app.Activity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.catalogocursos.data.AppDatabase
import com.example.catalogocursos.databinding.ActivityCadastroCursoBinding
import com.example.catalogocursos.model.Curso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CadastroCursoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCadastroCursoBinding
    private lateinit var db: AppDatabase
    private var cursoEdicao: Curso? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCadastroCursoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = AppDatabase.getInstance(this)
        verificarEdicao()
        setupListeners()
    }

    private fun verificarEdicao() {
        cursoEdicao = intent.getSerializableExtra("curso") as? Curso
        if (cursoEdicao != null) {
            binding.tvTituloForm.text = "Editar Curso"
            binding.edtNome.setText(cursoEdicao?.nome)
            binding.edtDescricao.setText(cursoEdicao?.descricao)
        }
    }

    private fun setupListeners() {
        binding.btnSalvar.setOnClickListener {
            val nome = binding.edtNome.text.toString()
            val descricao = binding.edtDescricao.text.toString()

            if (nome.isNotBlank() && descricao.isNotBlank()) {
                val cursoParaSalvar = Curso(
                    id = cursoEdicao?.id ?: 0,
                    nome = nome,
                    descricao = descricao
                )
                salvarNoBanco(cursoParaSalvar)
            } else {
                Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnVoltar.setOnClickListener {
            setResult(Activity.RESULT_CANCELED)
            finish()
        }
    }

    private fun salvarNoBanco(curso: Curso) {
        lifecycleScope.launch(Dispatchers.IO) {
            if (cursoEdicao == null) {
                db.cursoDao().inserir(curso)
            } else {
                db.cursoDao().atualizar(curso)
            }
            setResult(Activity.RESULT_OK)
            finish()
        }
    }
}