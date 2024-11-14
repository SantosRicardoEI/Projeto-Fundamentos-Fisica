import kotlin.math.*

fun pedePlaneta(): String {

    do {
        println("Introduza um planeta do sistema solar (ou Enter para escolher a Terra)")
        val planeta = readln().lowercase()
        if (planeta == "") {
            return "terra"
        }
        if (gravidade(planeta) != -1.0) {
            return planeta
        } else {
            println("Planeta inválido")
        }
    } while (gravidade(planeta) == -1.0)
    return "ERRO"
}

fun gravidade(planeta: String): Double {
    when (planeta) {
        "mercurio", "mercúrio" -> return 3.7
        "venus", "vénus" -> return 8.7
        "terra" -> return 9.8
        "marte" -> return 3.7
        "jupiter","júpiter" -> return 24.9
        "saturno" -> return 10.5
        "urano" -> return 8.6
        "neptuno" -> return 11.8
        "plutao","plutão" -> return 0.6
        "lua" -> return 1.6
    }
    return -1.0
}


fun imprimeTitulo() {
    println("""
    >>>>>>>>>>>>>>>>>>>>>>>>>>
        Projeto de Física
    <<<<<<<<<<<<<<<<<<<<<<<<<<
""")
}

//Conversor graus para radianos
fun pRadianos(a:Double) = a * PI/180

//Formula resolvente que retorna a maior solução
fun fResolvente(a:Double,b:Double,c:Double):Double {
        val d = b * b - 4 * a * c
        if (d < 0) {
            return -1.0
        }
        val sol1 = (-b + sqrt(d)) / (2 * a)
        val sol2 = (-b - sqrt(d)) / (2 * a)
        if (sol1 > sol2) {
            return sol1
        }
        else return sol2
    }


//Pede ao utilizador para introduzir uma altura e retorna a resposta validada
fun pedeAltura():Double {
    do {
        println("Introduza a altura")
        val altura = readln().toDoubleOrNull() ?: -1.0
        if (altura < 0) {
            println("Altura inválida")
        }
        else return altura
    } while (true)
}

//pede ao utilizador para introduzir uma velocidade inicial e retorna a resposta validada
fun pedeVelocidade():Double {
    do {
        println("Introduza a velocidade inicial")
        val v0 = readln().toDoubleOrNull() ?: -1.0
        if (v0 < 0) {
            println("Velocidade inválida")
        }
        else return v0
    } while (true)
}

//pede ao utilizador para introduzir o angulo de lançamento e retorna a resposta validada
fun pedeAngulo():Double {
    do {
        println("Introduza o ângulo (0º-90º)")
        var angulo = readln().toDoubleOrNull() ?: -1.0
        if (angulo < 0.0 || angulo > 90.0) {
            println("Angulo inválido")
            angulo = -1.0
        }
    } while (angulo < 0 )
    return -1.0
}

//Retorna o valor introduzido reduzido a uma casa decimal
fun cortarCasasDecimais(a:Double):Double {
    val resultado = ((a * 10).toInt()).toDouble() / 10
    return resultado
}


//Imprime os dados relativos ao movimento do projetil
fun imprimeDados(a:String,b:Double,c:Double,d:Double,e:Double,f:Double,g:Double,h:Double,i:Double,j:Double,k:Double) {
    println("Planeta: ${a.replaceFirstChar {it.uppercase()}}")
    println("Aceleração gravítica: ${b}m/s^2")
    println("Altura inicial: ${c}m")
    println("Velocidade inicial: ${d}m/s")
    println("Velocidade vertical inicial: ${j}m/s")
    println("Velocidade horizontal: ${k}m/s")
    println("Angulo de lançamento: ${e}º")
    println("Tempo de voo: ${f}s")
    println("Alcance: ${g}m")
    println("Altura máxima: ${h}m")
    println("Tempo de subida: ${i}s")
    println()

}


//Pergunta ao ultilizador se quer repetir o programa e retorna "true" ou "false"
fun pedeRepetirOuTerminar(): Boolean {

    do {
        println("Pretende outro gráfico? (S/N)?")
        val resposta = readln()
        when (resposta) {
            "S", "s" -> return true
            "N", "n" -> return false
            else -> {
                println("Resposta inválida")
            }
        }
    } while (true)
}

    fun main() {
        imprimeTitulo()
        do {

            //Pede dados de lançamento
            val planeta = pedePlaneta()
            val g = gravidade(planeta)
            val altura = pedeAltura()
            val v0 = pedeVelocidade()
            val angulo = pedeAngulo()

            //Calcula + infs e reduz a 1 casa decimal
            val anguloRad = cortarCasasDecimais(pRadianos(angulo))
            val v0y = cortarCasasDecimais(v0 * sin(anguloRad))
            val v0x = cortarCasasDecimais(v0 * cos(anguloRad))
            val tempoVoo = cortarCasasDecimais(fResolvente(-(g / 2), v0y, altura))
            val alcance = cortarCasasDecimais((v0 * cos(anguloRad)) * tempoVoo)
            val tempoSubida = cortarCasasDecimais(v0y/g)
            val alturaMaxima = cortarCasasDecimais(altura + v0y*tempoSubida - (g/2) * (tempoSubida).pow(2))

            //Define largura e altura do grafico e desenha grafico
            val larguraGrafico = 60
            val alturaGrafico = 20
            val grafico = Chart(larguraGrafico, alturaGrafico)

            //Desenha o grafico adequado ao cenario
            var x = 0.0
            if (v0 == 0.0 && angulo == 90.0) {
                println("Gráfico da altura em função do tempo: (A SER IMPLEMENTADO)")
                //grafico y em t AQUI
                println("O projétil foi deixado cair de ${altura}m de altura! O alcance do projétil é ${alcance}m!\n")
            }
            else if (v0 == 0.0) {
                println("Gráfico da altura em função do tempo: (A SER IMPLEMENTADO)")
                //grafico y em t AQUI
                println("O projétil foi deixado cair de ${altura}m de altura! O alcance do projétil é ${alcance}m!\n")
            }
            else if (angulo == 90.0 ) {
                println("Gráfico da altura em função do tempo: (A SER IMPLEMENTADO)")
                //grafico y em t AQUI
                println("O projétil foi lançado de ${altura}m de altura para ${alturaMaxima}m de altura! O alcance do projétil é ${alcance}m!\n")
            }
            else {
                println("Gráfico da altura em função da posição:")
                do {
                    val yEmX = altura + v0y * (x / v0x) - (g / 2) * (x / v0x).pow(2)
                    grafico.ponto(x, yEmX)
                    x += alcance / 21
                } while (x <= alcance)
                grafico.draw()
                println("O projétil foi lançado a ${alcance}m de distância!\n")
            }

            //Mostra mais dados sobre o movimento do projetil
            imprimeDados(planeta, g, altura, v0, angulo, tempoVoo, alcance, alturaMaxima, tempoSubida, v0y, v0x )

            //Pergunta ao utilizador se quer gerar outro grafico
            val gerarOutroGrafico = pedeRepetirOuTerminar()
        } while (gerarOutroGrafico)
        println("Até à próxima!")
    }







