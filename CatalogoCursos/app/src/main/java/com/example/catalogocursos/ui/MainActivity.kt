package com.example.catalogocursos.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.catalogocursos.R
import com.example.catalogocursos.data.AppDatabase
import com.example.catalogocursos.databinding.ActivityMainBinding
import com.example.catalogocursos.model.Curso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    companion object {
        const val TAG = "MainActivity"
    }

    private lateinit var binding: ActivityMainBinding
    private lateinit var db: AppDatabase
    private lateinit var launcherCadastro: ActivityResultLauncher<Intent>
    private var cursos: MutableList<Curso> = mutableListOf()
    private var cursoSelecionado: Curso? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = AppDatabase.getInstance(this)
        setupLauncher()
        setupListeners()
        loadData()
    }

    private fun setupLauncher() {
        launcherCadastro = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                limparSelecao()
                loadData()
            }
        }
    }

    private fun loadData() {
        lifecycleScope.launch(Dispatchers.IO) {
            var listaBanco = db.cursoDao().listarTodos()

            // Se o banco estiver vazio na primeira execução, insere os cursos do strings.xml
            if (listaBanco.isEmpty()) {
                inserirCursosIniciais()
                listaBanco = db.cursoDao().listarTodos()
            }

            cursos = listaBanco.toMutableList()
            val nomesCursos = cursos.map { it.nome }

            withContext(Dispatchers.Main) {
                val adapter = ArrayAdapter(
                    this@MainActivity,
                    android.R.layout.simple_list_item_1,
                    nomesCursos
                )
                binding.listaCursos.adapter = adapter
            }
        }
    }

    private suspend fun inserirCursosIniciais() {
        val iniciais = listOf(
            Curso(nome = getString(R.string.nome_tsi), descricao = getString(R.string.desc_tsi)),
            Curso(nome = getString(R.string.nome_eng), descricao = getString(R.string.desc_eng)),
            Curso(nome = getString(R.string.nome_ads), descricao = getString(R.string.desc_ads)),
            Curso(nome = getString(R.string.nome_mat), descricao = getString(R.string.desc_mat))
        )
        for (curso in iniciais) {
            db.cursoDao().inserir(curso)
        }
    }

    private fun setupListeners() {
        // Clique em um curso da ListView mostra a descrição e habilita Editar/Excluir
        binding.listaCursos.setOnItemClickListener { _, _, position, _ ->
            val curso = cursos[position]
            cursoSelecionado = curso
            binding.tvDescricao.text = curso.descricao
            binding.btnEditar.isEnabled = true
            binding.btnExcluir.isEnabled = true
        }

        // Botão para inserir novo curso
        binding.btnNovoCurso.setOnClickListener {
            val intent = Intent(this, CadastroCursoActivity::class.java)
            launcherCadastro.launch(intent)
        }

        // Botão opcional para editar curso selecionado
        binding.btnEditar.setOnClickListener {
            val curso = cursoSelecionado
            if (curso != null) {
                val intent = Intent(this, CadastroCursoActivity::class.java)
                intent.putExtra("curso", curso)
                launcherCadastro.launch(intent)
            }
        }

        // Botão opcional para excluir curso selecionado
        binding.btnExcluir.setOnClickListener {
            val curso = cursoSelecionado
            if (curso != null) {
                deletarCurso(curso)
            }
        }
    }

    private fun deletarCurso(curso: Curso) {
        lifecycleScope.launch(Dispatchers.IO) {
            db.cursoDao().deletar(curso)
            withContext(Dispatchers.Main) {
                Toast.makeText(this@MainActivity, "Curso excluído!", Toast.LENGTH_SHORT).show()
                limparSelecao()
                loadData()
            }
        }
    }

    private fun limparSelecao() {
        cursoSelecionado = null
        binding.tvDescricao.text = ""
        binding.btnEditar.isEnabled = false
        binding.btnExcluir.isEnabled = false
    }
}