package com.example.conversormoedaactivity

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    companion object {
        const val TAG = "MainActivity"
        const val TAXA_DOLAR = 5.60
        const val TAXA_EURO = 6.10
    }

    private lateinit var ivBandeiraOrigem: ImageView
    private lateinit var ivBandeiraDestino: ImageView
    private lateinit var btnInverter: Button
    private lateinit var rgMoedaDestino: RadioGroup
    private lateinit var rbDolar: RadioButton
    private lateinit var rbEuro: RadioButton
    private lateinit var etValor: EditText
    private lateinit var btnCalcular: Button
    private lateinit var btnLimpar: Button
    private lateinit var tvResultado: TextView

    // Variável para controlar se a conversão é de Real -> Estrangeira ou vice-versa
    private var isRealParaEstrangeira = true

    // Funções do ciclo de vida da Activity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupViews()
        setupListeners()
    }

    // Funções Auxiliares de Configuração
    private fun setupViews() {
        ivBandeiraOrigem = findViewById(R.id.ivBandeiraOrigem)
        ivBandeiraDestino = findViewById(R.id.ivBandeiraDestino)
        btnInverter = findViewById(R.id.btnInverter)
        rgMoedaDestino = findViewById(R.id.rgMoedaDestino)
        rbDolar = findViewById(R.id.rbDolar)
        rbEuro = findViewById(R.id.rbEuro)
        etValor = findViewById(R.id.etValor)
        btnCalcular = findViewById(R.id.btnCalcular)
        btnLimpar = findViewById(R.id.btnLimpar) // Adicionado ID do botão limpar
        tvResultado = findViewById(R.id.tvResultado)
    }

    private fun setupListeners() {
        btnInverter.setOnClickListener { inverterMoedas() }
        btnCalcular.setOnClickListener { converterMoeda() }
        btnLimpar.setOnClickListener { limparCampos() }

        // Atualiza a bandeira automaticamente se o usuário trocar Dólar por Euro no menu
        rgMoedaDestino.setOnCheckedChangeListener { _, _ ->
            atualizarBandeiras()
        }
    }

    // Funções Utilitárias
    private fun inverterMoedas() {
        isRealParaEstrangeira = !isRealParaEstrangeira
        atualizarBandeiras()
    }

    private fun atualizarBandeiras() {
        val drawableEstrangeira = if (rbDolar.isChecked) R.drawable.bandeira_eua else R.drawable.bandeira_euro

        if (isRealParaEstrangeira) {
            ivBandeiraOrigem.setImageResource(R.drawable.bandeira_brasil)
            ivBandeiraDestino.setImageResource(drawableEstrangeira)
        } else {
            ivBandeiraOrigem.setImageResource(drawableEstrangeira)
            ivBandeiraDestino.setImageResource(R.drawable.bandeira_brasil)
        }
    }

    private fun converterMoeda() {
        val valorTexto = etValor.text.toString()


        if (valorTexto.isBlank()) {
            Toast.makeText(this, "Por favor, digite um valor", Toast.LENGTH_SHORT).show()
            return
        }

        val valor = valorTexto.toDoubleOrNull()
        if (valor == null) {
            Toast.makeText(this, "Valor inválido", Toast.LENGTH_SHORT).show()
            return
        }

        // Pega a taxa certa baseada no que o usuário selecionou
        val taxa = if (rbDolar.isChecked) TAXA_DOLAR else TAXA_EURO
        val resultado: Double
        val simboloDestino: String

        // Regra de negócio da conversão
        if (isRealParaEstrangeira) {
            resultado = valor / taxa
            simboloDestino = if (rbDolar.isChecked) "US$" else "€"
            tvResultado.text = String.format("Resultado: %s %.2f", simboloDestino, resultado)
        } else {
            resultado = valor * taxa
            tvResultado.text = String.format("Resultado: R$ %.2f", resultado)
        }
    }

    private fun limparCampos() {
        etValor.text.clear()
        tvResultado.text = "Resultado: R$ 0,00"
    }
}