package com.example.calculadoracombustivel

import android.content.Context
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // executa o cálcula no clique do botão
        btnCalcular.setOnClickListener{
            calcularPreco()

            // fechar o teclado
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            imm?.hideSoftInputFromWindow(it.windowToken, 0)
        }
    }

    fun calcularPreco() {
        // pega texto dos inputs
        val precoAlcool = txtAlcool.text.toString()
        val precoGasolina = txtGasolina.text.toString()

        val validaCampos = validarCampos(precoAlcool, precoGasolina)

        // se campos validados
        if(validaCampos){
            // calcular melhor preço
            calcularMelhorPreco(precoAlcool, precoGasolina)
        } else {
            // se houve erro de preenchimento ou campos vazios
            txtResultado.text = "Preencha os campos corretamente primeiro!"
        }
    }

    private fun calcularMelhorPreco(precoAlccol: String, precoGasolina: String){
        // converte string para double
        val valorAlcool = precoAlccol.toDouble()
        val valorGasolina = precoGasolina.toDouble()

        val resultadoPreco = valorAlcool/valorGasolina

        if(resultadoPreco >= 0.7){
            // define valor para label
            txtResultado.text = "Melhor utilizar Gasolina"
        }else{
            txtResultado.text = "Melhor utilizar Álcool"
        }
    }

    private fun validarCampos(precoAlcool: String, precoGasolina: String): Boolean{
        var camposValidados: Boolean = true

        // se converte corretamente devolve um double senão retorna null
        val valorAlcool = precoAlcool.toDoubleOrNull()
        val valorGasolina = precoGasolina.toDoubleOrNull()

        if(valorAlcool == null){
            camposValidados = false
        }else if(valorGasolina == null){
            camposValidados = false
        }

        // se tudo for convertido corretamente retorna true senão false
        return camposValidados
    }
}