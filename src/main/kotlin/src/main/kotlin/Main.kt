import kotlin.math.*


fun imprimeTitulo(planeta:String,haAtrito:Boolean) {
    println()
    print("""
    >>>>>>>>>>>>>>>>>>>>>>>>>>
      🚀 Projeto de Física 🚀
    <<<<<<<<<<<<<<<<<<<<<<<<<<
""")
    println("              ${planeta.replaceFirstChar { it.uppercase() }}")
    println("     (${if (haAtrito) "Com" else "Sem"} resistência do ar)")
    println()
    println("1 - Lançar projétil")
    println("2 - Escolher Planeta")
    println("3 - ${if (haAtrito) "Desativar" else "Ativar"} resistência do ar")
    println("4 - Informações sobre o Projeto")
    println("0 - Sair")
}

fun arredondar(num:Double): Double {
    var numero = (round(num*100))/100
    if (numero == -0.0) {
        numero = 0.0
    }
    return numero
}

fun validaOpcao(): Int {
    do {
        val opcao = readln().toIntOrNull()
        if (opcao != null && opcao in 0..4) {
            return opcao
        }
        else {
            println("Opcao invalida. Por favor, tente novamente.")
        }
    } while (true)
}

fun pRadianos(a:Double) = (a * PI/180)

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

fun densidadeATM(planeta:String): Double {
    when (planeta) {
        "mercurio", "mercúrio" -> return 0.0
        "venus", "vénus" -> return 65.0
        "terra" -> return 1.2
        "marte" -> return 0.02
        "jupiter","júpiter" -> return 5.0
        "saturno" -> return 1.5
        "urano" -> return 0.42
        "neptuno" -> return 0.45
        "plutao","plutão" -> return 0.0
        "lua" -> return 0.0
        else ->return 0.0
    }

}

fun pedePlaneta(): String {
    do {
        println("Introduza um planeta do sistema solar (ou Enter para escolher a Terra)")
        when (val planeta = readln().lowercase()) {
            "" -> {
                print("Escolhido o planeta Terra!")
                return "terra"
            }

            "Lua", "lua" -> {
                println("Escolhida a Lua!")
                return "lua"
            }

            else -> {
                if (gravidade(planeta) != -1.0) {
                    print("Escolhido o planeta ${planeta.replaceFirstChar { it.uppercase() }}!")
                    return planeta
                } else {
                    println("Planeta inválido")
                }
            }
        }
    } while (true)
}

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

fun pedeAltura():Double {
    do {
        println("Introduza a altura")
        val altura = readln().toDoubleOrNull() ?: -1.0
        if (altura >= 0) {
            return altura
        }
        else {
            println("Altura inválida")
        }
    } while (true)
}

fun pedeVelocidade():Double {
    do {
        println("Introduza a velocidade inicial")
        val v0 = readln().toDoubleOrNull() ?: -1.0
        if (v0 >= 0) {
            return v0
        }
        else {
            println("Velocidade inválida")
        }
    } while (true)
}

fun pedeAngulo():Double {
    do {
        println("Introduza o ângulo (0º-90º)")
        val angulo = readln().toDoubleOrNull() ?: -1.0
        if (angulo in (0.0..90.0)) {
            return round(angulo)
        }
        else {
            println("Angulo inválido")
        }
    } while (true)
}

fun imprimeDados(planeta:String,aceleracaoG:Double,altura:Double,v0:Double,angulo:Double,tvoo:Double,alcance:Double,alturaMax:Double,tSubida:Double,v0y:Double,v0x:Double,resistenciaAr:Boolean) {
    println("|Dados do Lançamento")
    println("|===================")
    println("|Planeta                       : ${planeta.replaceFirstChar {it.uppercase()}}")
    println("|Aceleração gravítica          : ${aceleracaoG}m/s^2")
    println("|Resistência do ar             : ${if (resistenciaAr) "Sim" else "Não"}")
    println("|Altura inicial                : ${arredondar(altura)}m")
    println("|Velocidade inicial            : ${arredondar(v0)}m/s")
    println("|Angulo de lançamento          : ${arredondar(angulo)}º")
    println("|Velocidade vertical inicial   : ${arredondar(v0y)}m/s")
    println("|Velocidade horizontal         : ${arredondar(v0x)}m/s")
    println("|Tempo de voo                  : ${arredondar(tvoo)}s")
    println("|Tempo de subida               : ${arredondar(tSubida)}s")
    println("|Alcance                       : ${arredondar(alcance)}m")
    println("|Altura máxima                 : ${arredondar(alturaMax)}m")

    println()
}

fun pedeRepetirOuTerminar(): Boolean {
    do {
        println("Realizar novo lançamento? (S/N)?")
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
        val larguraGrafico = 60
        val alturaGrafico = 20
        val grafico = Chart(larguraGrafico, alturaGrafico)
        val planetas = listOf("mercurio","venus","terra","marte","jupiter","saturno","urano","neptuno","plutao","lua")
        var planeta = planetas.random()
        var g = gravidade(planeta)
        var haAtrito = false

        //Menu
        do {
            imprimeTitulo(planeta,haAtrito)
            val opcao = validaOpcao()
            if (opcao == 1) {
                do {
                    //Pede dados de lançamento
                    val altura = pedeAltura()
                    val v0 = pedeVelocidade()
                    var angulo = 0.0
                    if (altura != 0.0 && v0 != 0.0) {
                       angulo = pedeAngulo()
                   }
                    else if (altura == 0.0 && v0 != 0.0) {
                        angulo = pedeAngulo()
                    }
                    //Calcula dados lançamento
                    var anguloRad = arredondar(pRadianos(angulo))
                    var v0y = (v0 * (sin(anguloRad)))
                    var v0x = (v0 * (cos(anguloRad)))
                    var tempoVoo = fResolvente(-(g / 2), v0y, altura)
                    var alcance = (v0 * arredondar((cos(anguloRad)))) * tempoVoo
                    var tempoSubida = v0y / g
                    var alturaMaxima = altura + v0y * tempoSubida - (g / 2) * (tempoSubida).pow(2)

                    if (!haAtrito || planeta == "lua" || planeta == "mercurio" || planeta == "plutao") {

                        if ((v0 == 0.0 && altura == 0.0) || (altura == 0.0 && angulo == 0.0)) {
                            println("\nO projétil não se moveu :(\n")
                        } else if (v0 == 0.0) {
                            println("\nO projétil foi largado a ${arredondar(altura)}m de altura! Chegou ao solo em ${arredondar(tempoVoo)}s!\n")
                        } else if (angulo == 90.0) {
                            println(
                                "\nO projétil foi lançado para cima. De ${arredondar(altura)}m para ${arredondar(alturaMaxima)}m de altura!\n")
                        } else {
                            var x = 0.0
                            println("\nAltura em função da posição:")
                            do {
                                val yEmX = altura + v0y * (x / v0x) - (g / 2) * (x / v0x).pow(2)
                                grafico.ponto(x, yEmX)
                                x += alcance / 24
                            } while (x <= alcance)
                            grafico.draw()
                            println("O projétil foi lançado a ${arredondar(alcance)}m de distância!\n")
                        }
                        }
                    else if (haAtrito) {
                        println("Lançamento com resistência do ar por implementar. Desative a resistência no menu principal")
                        break
                    }
                    imprimeDados(planeta, g, altura, v0, angulo, tempoVoo, alcance, alturaMaxima, tempoSubida, v0y, v0x, haAtrito)
                    val gerarOutroGrafico = pedeRepetirOuTerminar()
                } while (gerarOutroGrafico)
                planeta = planetas.random()
            }
            if (opcao == 2) {
                planeta = pedePlaneta()
                g = gravidade(planeta)
            }
            if (opcao == 3) {
                if (haAtrito) {
                    haAtrito = false
                    println("Desativada a resistência do ar!")
                }
                else {
                    haAtrito = true
                    println("Ativada a resistência do ar!")
                }
            }
            if (opcao == 4) {
                println("""
        |===========================================================
        |${"\u001B[36m"}   Este projeto foi realizado no âmbito da disciplina de    ${"\u001B[0m"}
        |${"\u001B[36m"}              Fundamentos de Física pelos alunos:          ${"\u001B[0m"}
        |-----------------------------------------------------------
        |${"\u001B[33m"}   Ricardo Santos                                       ${"\u001B[0m"}
        |${"\u001B[33m"}   Nuno Tainha                                         ${"\u001B[0m"}
        |${"\u001B[33m"}   Duarte Martins                                      ${"\u001B[0m"}
        |${"\u001B[33m"}   Duarte Veríssimo                                    ${"\u001B[0m"}
        |-----------------------------------------------------------
        |${"\u001B[35m"}   Ano letivo: 2024/2025                                 ${"\u001B[0m"}
        |${"\u001B[35m"}   (1º semestre)                                        ${"\u001B[0m"}
        |===========================================================
    """.trimMargin())
            }
        } while (opcao != 0)
        print("""
                                         _.oo.
                 _.u[[/;:,.         .odMMMMMM'
              .o888UU[[[/;:-.  .o@P^    MMM^
             oN88888UU[[[/;::-.        dP^
            dNMMNN888UU[[[/;:--.   .o@P^
           ,MMMMMMN888UU[[/;::-. o@^
           NNMMMNN888UU[[[/~.o@P^
           888888888UU[[[/o@^-..
          oI8888UU[[[/o@P^:--..
       .@^  YUU[[[/o@^;::---..
     oMP     ^/o@P^;:::---..
  .dMMM    .o@^ ^;::---...
 dMMMMMMM@^`       `^^^^
YMMMUP^
 ^^                                       (https://ascii.co.uk/art/saturn)

""")

        println("Até à próxima!")
    }







