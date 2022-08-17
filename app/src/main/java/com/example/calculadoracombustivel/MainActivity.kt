package com.example.calculadoracombustivel

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.math.abs
import kotlin.math.sqrt

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // executa o cálcula no clique do botão
        btnCalcular.setOnClickListener{
            calcularCoordenada()

            // fechar o teclado
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            imm?.hideSoftInputFromWindow(it.windowToken, 0)
        }
    }

    @SuppressLint("SetTextI18n")
    fun calcularCoordenada() {
        // pega texto dos inputs
        val valorNome = txtNome.text.toString()
        val valorX1 = txtX1.text.toString()
        val valorY1 = txtY1.text.toString()
        val valorDist1 = txtDistancia1.text.toString()

        val valorX2 = txtX2.text.toString()
        val valorY2 = txtY2.text.toString()
        val valorDist2 = txtDistancia2.text.toString()

        val valorX3 = txtX3.text.toString()
        val valorY3 = txtY3.text.toString()
        val valorDist3 = txtDistancia3.text.toString()

        val validaCampos = validarCampos(valorNome, valorX1, valorY1, valorDist1, valorX2, valorY2, valorDist2, valorX3, valorY3, valorDist3)

        // se campos validados
        if(validaCampos){
            // aponta coordenada do item lido
           val ponto = calculaTrilateracao(valorX1, valorY1, valorDist1, valorX2, valorY2, valorDist2, valorX3, valorY3, valorDist3)
            val x = ponto[0]
            val y = ponto[1]

            txtResultado.text = "$valorNome a coordenada do item lido: x $x y $y"
        } else {
            // se houve erro de preenchimento ou campos vazios
            txtResultado.text = "Preencha os campos corretamente primeiro!"
        }
    }

    private fun calculaTrilateracao( x1: String, y1: String, dist1: String, x2: String, y2: String, dist2: String, x3: String, y3: String, dist3: String): DoubleArray{
        // converte string para double
        val valorX1 = x1.toDouble()
        val valorY1 = y1.toDouble()
        val valorDist1 = dist1.toDouble()

        val valorX2 = x2.toDouble()
        val valorY2 = y2.toDouble()
        val valorDist2 = dist2.toDouble()

        val valorX3 = x3.toDouble()
        val valorY3 = y3.toDouble()
        val valorDist3 = dist3.toDouble()

        val retorno = DoubleArray(2)

        val p: DoubleArray
        var p1: DoubleArray? = DoubleArray(4)
        var p2: DoubleArray? = DoubleArray(4)
        var p3: DoubleArray? = DoubleArray(4)

        //calcula os seis pontos da intersecção de 3 circunferências
        p1 = calcularIntersescoes(valorX1, valorY1, valorX2,valorY2,valorDist1, valorDist2)
        p2 = calcularIntersescoes(valorX1, valorY1, valorX3, valorY3, valorDist1, valorDist3)
        p3 = calcularIntersescoes(valorX2, valorY2, valorX3, valorY3, valorDist2, valorDist3)

        //verifica se todos os pontos foram calculados
        if (!(p1 == null || p2 == null || p3 == null)) {
            p = p1 + p2 + p3

            //armazenando todas distancias dentro de um array d
            val d: MutableList<String> = ArrayList()

            var j = 0

            val pCount = p.size

            // percorrendo pelos pontos das coordenadas armazenadas no array p
            while (j < pCount) {
                var i = j
                while (i < pCount) {
                    if (j != i) {
                        var dist = distancia(
                            p[j],
                            p[i],
                            p[j + 1],
                            p[i + 1]
                        )
                        d.add("$dist|$j,$i")
                    }
                    i += 2
                }
                j += 2
            }

            //ordenando o vetor
            d.sort()

            //abaixo eu montei uma lógica para selecionar o maior triangulo
            //que pode ser formado por todas as retas possíveis que podem ser formadas com os 6 pontos
            //identificados acima, quando esse triângulo é encontrado eu elimino os pontos que o forma
            //para conseguir as coordenadas do triangulo que me interessa, para assim calcular seu baricentro
            //e finalmente identificar a coordenada lida.
            var maiorArea = 0.0
            var par1 = 0
            var par2 = 0
            var par3 = 0
            var par4 = 0
            var par5 = 0
            var par6 = 0
            var l = 0
            while (l < pCount) {
                var m = l
                while (m < pCount) {
                    var n = m
                    while (n < pCount) {
                        if (!(l == m || m == n || n == l)) {
                            val a: Double = distancia(p[l], p[m], p[l + 1], p[m + 1])
                            val b: Double = distancia(p[l], p[n], p[l + 1], p[n + 1])
                            val c: Double = distancia(p[m], p[n], p[m + 1], p[n + 1])
                            var x: Double
                            var area: Double = 0.0
                            if (!(a + b < c || a + c < b || b + c < a)) {
                                x = (a + b + c) / 2
                                area = sqrt(x * (x - a) * (x - b) * (x - c))
                                if (area > maiorArea) {
                                    maiorArea = area
                                    par1 = l
                                    par2 = m
                                    par3 = n
                                    par4 = l + 1
                                    par5 = m + 1
                                    par6 = n + 1
                                }
                            }
                        }
                        n += 2
                    }
                    m += 2
                }
                l += 2
            }

            //zerando os 3 pares ordenados do maior triangulo
            p[par1] = 0.0
            p[par2] = 0.0
            p[par3] = 0.0
            p[par4] = 0.0
            p[par5] = 0.0
            p[par6] = 0.0
            var x = 0.0
            var y = 0.0
            var f = 0
            while (f < pCount) {
                x += p[f]
                y += p[f + 1]
                f += 2
            }
            x /= 3
            y /= 3
            retorno[0] = x
            retorno[1] = y
        }
        return retorno
    }

    fun calcularIntersescoes(
        x0: Double,
        y0: Double,
        x1: Double,
        y1: Double,
        r0: Double,
        r1: Double
    ): DoubleArray? {
        val p = DoubleArray(4)
        val a: Double
        val d: Double
        val h: Double
        val rx: Double
        val ry: Double
        val x2: Double
        val y2: Double
        val dx: Double = x1 - x0
        val dy: Double = y1 - y0

        d = sqrt(dx * dx + dy * dy)

        //verifica se há intersecção entre as circunferências
        return if (d > (r0 + r1)) {
            null
        } else if (d < abs((r0 - r1))) {
            null
        } else {
            a = (r0 * r0 - r1 * r1 + d * d) / (2 * d)
            x2 = x0 + dx * a / d
            y2 = y0 + dy * a / d
            h = sqrt(r0 * r0 - a * a)
            rx = -dy * (h / d)
            ry = dx * (h / d)
            p[0] = x2 + rx
            p[1] = y2 + ry
            p[2] = x2 - rx
            p[3] = y2 - ry
            p
        }
    }

    //calcula a distância entre dois pontos
    fun distancia(x1: Double, x2: Double, y1: Double, y2: Double): Double {
        val calc = (x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1)
        return sqrt(calc)
    }

    private fun validarCampos(nome: String, x1: String, y1: String, dist1: String, x2: String, y2: String, dist2: String, x3: String, y3: String, dist3: String): Boolean{
        var camposValidados: Boolean = true

        // se converte corretamente devolve um double se não retorna null
        val valorX1 = x1.toDoubleOrNull()
        val valorY1 = y1.toDoubleOrNull()
        val valorDist1 = dist1.toDoubleOrNull()

        val valorX2 = x2.toDoubleOrNull()
        val valorY2 = y2.toDoubleOrNull()
        val valorDist2 = dist2.toDoubleOrNull()

        val valorX3 = x3.toDoubleOrNull()
        val valorY3 = y3.toDoubleOrNull()
        val valorDist3 = dist3.toDoubleOrNull()

        if(nome == "" || valorX1 == null || valorX2 == null || valorX3 == null || valorY1 == null || valorY2 == null || valorY3 == null || valorDist1 == null || valorDist2 == null || valorDist3 == null) {
            camposValidados = false
        }

        // se tudo for convertido corretamente retorna true se não false
        return camposValidados
    }
}