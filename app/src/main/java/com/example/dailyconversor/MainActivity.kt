package com.example.dailyconversor

import android.R
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import android.widget.Toast.*
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.dailyconversor.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Recuperando spinner de Massa
        val spinnerMassaEntrada: Spinner = binding.spMassaEntrada
        val spinnerMassaSaida: Spinner = binding.spMassaSaida

        //// Recuperando spinner de Comprimento
        val spinnerComprimentoEntrada: Spinner = binding.spComprimentoEntrada
        val spinnerComprimentoSaida: Spinner = binding.spComprimentoSaida

        //// Recuperando spinner de Capacidade
        val spinnerCapacidadeEntrada: Spinner = binding.spCapacidadeEntrada
        val spinnerCapacidadeSaida: Spinner = binding.spCapacidadeSaida

        // Recuperando botões e resultado
        val btnConverter: Button = binding.btnConverter
        val btnLimpar: Button = binding.btnLimpar
        val tvResultado: TextView = binding.tvResult


        // Lista de opções para o Spinner
        val optionsMassa = arrayOf(
            "Quilograma",
            "Tonelada",
            "Hectograma",
            "Decagrama",
            "Grama",
            "Decigrama",
            "Centigrama",
            "Miligrama"
        )
        val optionComprimento = arrayOf(
            "Quilômetro",
            "Hectômetro",
            "Decâmetro",
            "Metro",
            "Decímetro",
            "Centímetro",
            "Milímetro"
        )
        val optionCapacidade = arrayOf(
            "Quilolitro",
            "Hectolitro",
            "Decalitro",
            "Decilitro",
            "Centilitro",
            "Mililitro",
            "Litro"
        )

        // Criação do ArrayAdapter para o Spinner
        val adapterMassa = ArrayAdapter(this, android.R.layout.simple_spinner_item, optionsMassa)
        val adapterComprimento = ArrayAdapter(this, R.layout.simple_spinner_item, optionComprimento)
        val adapterCapacidade = ArrayAdapter(this, R.layout.simple_spinner_item, optionCapacidade)

        // Definir o layout para as opções no Spinner
        adapterMassa.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
        adapterComprimento.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
        adapterCapacidade.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)

        // Aplicar o adaptador ao Spinner
        spinnerMassaEntrada.adapter = adapterMassa
        spinnerMassaSaida.adapter = adapterMassa
        spinnerComprimentoEntrada.adapter = adapterComprimento
        spinnerComprimentoSaida.adapter = adapterComprimento
        spinnerCapacidadeEntrada.adapter = adapterCapacidade
        spinnerCapacidadeSaida.adapter = adapterCapacidade

        // Mapas de conversão
        val fatorConversaoMassa = mapOf(
            "Quilograma" to 1.0,
            "Tonelada" to 1000.0,
            "Hectograma" to 0.1,
            "Decagrama" to 0.01,
            "Grama" to 0.001,
            "Decigrama" to 0.0001,
            "Centigrama" to 0.00001,
            "Miligrama" to 0.000001
        )

        val fatorConversaoComprimento = mapOf(
            "Quilômetro" to 1000.0,
            "Hectômetro" to 100.0,
            "Decâmetro" to 10.0,
            "Metro" to 1.0,
            "Decímetro" to 0.1,
            "Centímetro" to 0.01,
            "Milímetro" to 0.001
        )

        val fatorConversaoCapacidade = mapOf(
            "Litro" to 1.0,
            "Quilolitro" to 0.001,
            "Hectolitro" to 0.01,
            "Decalitro" to 0.1,
            "Decilitro" to 10.0,
            "Centilitro" to 100.0,
            "Mililitro" to 1000.0
        )


        btnConverter.setOnClickListener {
            val valorMassa = binding.edtMassa.text.toString()
            val valorComprimeto = binding.edtComprimento.text.toString()
            val valorCapacidade = binding.edtCapacidade.text.toString()

            // Verificar se o valor de massa ou comprimento está vazio
            if (valorMassa.isEmpty() && valorComprimeto.isEmpty() && valorCapacidade.isEmpty()) {
                Toast.makeText(this, "Preencha o valor a ser convertido.", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }

            var resultado = 0.0
            var unidadeSaida = ""

            if (valorMassa.isNotEmpty()) {
                val valorEntradaMassa = valorMassa.toDouble()
                val unidadeEntradaMassa = spinnerMassaEntrada.selectedItem.toString()
                val unidadeSaidaMassa = spinnerMassaSaida.selectedItem.toString()

                val fatorEntradaMassa = fatorConversaoMassa[unidadeEntradaMassa] ?: 1.0
                val fatorSaidaMassa = fatorConversaoMassa[unidadeSaidaMassa] ?: 1.0

                // Conversão de massa
                resultado =
                    valorEntradaMassa * (fatorEntradaMassa.toDouble() / fatorSaidaMassa.toDouble())
                unidadeSaida = unidadeSaidaMassa
            }

            if (valorComprimeto.isNotEmpty()) {
                val valorEntradaComprimento = valorComprimeto.toDouble()
                val unidadeEntradaComprimento = spinnerComprimentoEntrada.selectedItem.toString()
                val unidadeSaidaComprimento = spinnerComprimentoSaida.selectedItem.toString()

                val fatorEntradaComprimento =
                    fatorConversaoComprimento[unidadeEntradaComprimento] ?: 1.0
                val fatorSaidaComprimento =
                    fatorConversaoComprimento[unidadeSaidaComprimento] ?: 1.0

                // Conversão do comprimento
                resultado =
                    valorEntradaComprimento * (fatorEntradaComprimento / fatorSaidaComprimento)
                unidadeSaida = unidadeSaidaComprimento
            }

            if (valorCapacidade.isNotEmpty()) {
                val valorEntradaCapacidade = valorCapacidade.toDouble()
                val unidadeEntradaCapacidade = spinnerCapacidadeEntrada.selectedItem.toString()
                val unidadeSaidaCapacidade = spinnerCapacidadeSaida.selectedItem.toString()

                val fatorEntradaCapacidade =
                    fatorConversaoCapacidade[unidadeEntradaCapacidade] ?: 1.0
                val fatorSaidaCapacidade = fatorConversaoCapacidade[unidadeSaidaCapacidade] ?: 1.0

                // Conversão da capacidade
                resultado =
                    valorEntradaCapacidade * (fatorEntradaCapacidade.toDouble() / fatorSaidaCapacidade.toDouble())
                unidadeSaida = unidadeSaidaCapacidade
            }

            //Exibir o resultado
            tvResultado.text = String.format("%.4f %s", resultado, unidadeSaida)
        }

        btnLimpar.setOnClickListener {
            //Limpar os campos de texto
            binding.edtMassa.text?.clear()
            binding.edtCapacidade.text?.clear()
            binding.edtComprimento.text?.clear()

            //Limpar o valor dos resultados
            tvResultado.text = "0.0"

            //Redefinir os spinner
            spinnerMassaEntrada.setSelection(0)
            spinnerMassaSaida.setSelection(0)
            spinnerCapacidadeEntrada.setSelection(0)
            spinnerCapacidadeSaida.setSelection(0)
            spinnerComprimentoEntrada.setSelection(0)
            spinnerComprimentoSaida.setSelection(0)
        }
    }
}