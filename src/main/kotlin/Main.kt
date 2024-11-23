import kotlin.math.*

fun imprimeTitulo(planeta:String,distanciaRede: Double, alturaRede: Double) {
    println()
    println("""
        ==========================================
                 🚀 PROJETO DE FÍSICA 🚀
                    🌟 Acerta na Rede! 🌟
        ==========================================
    """.trimIndent())
    println("🌍 Planeta atual: ${planeta.replaceFirstChar { it.uppercase() }}")
    println("📏 Distância da Rede: ${arredondar(distanciaRede)}m")
    println("📐 Altura da Rede:    ${arredondar(alturaRede)}m")
    println("""
        ==========================================
                🔽 MENU PRINCIPAL 🔽
        ==========================================
        1️⃣ - Iniciar Lançamento
        2️⃣ - Escolher Planeta
        3️⃣ - Alterar Rede
        4️⃣ - Instruções
        5️⃣ - Informações sobre o projeto
        0️⃣ - Sair
        ==========================================
    """.trimIndent())
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
        if (opcao != null && opcao in 0..5) {
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

fun pedePlaneta(): String {
    do {
        println("Introduz um planeta do sistema solar (ou Enter para escolher a Terra)")
        when (val planeta = readln().lowercase()) {
            "" -> {
                print("\nEscolheste o planeta Terra!\n")
                return "terra"
            }

            "Lua", "lua" -> {
                println("\nEscolheste a Lua!\n")
                return "lua"
            }

            else -> {
                if (gravidade(planeta) != -1.0) {
                    print("\nEscolheste o planeta ${planeta.replaceFirstChar { it.uppercase() }}!\n")
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
        println("Introduz a altura")
        val altura = readln().toDoubleOrNull() ?: -1.0
        if (altura >= 0) {
            return altura
        }
        else {
            println("Altura inválida. A altura deve ser um valor positivo")
        }
    } while (true)
}

fun pedeVelocidade():Double {
    do {
        println("Introduz a velocidade inicial")
        val v0 = readln().toDoubleOrNull() ?: -1.0
        if (v0 >= 0) {
            return v0
        }
        else {
            println("Velocidade inválida. A velocidade deve ser um valor positivo")
        }
    } while (true)
}

fun pedeAngulo():Double {
    do {
        println("Introduz o ângulo (0º-90º)")
        val angulo = readln().toDoubleOrNull() ?: -1.0
        if (angulo in (0.0..90.0)) {
            return round(angulo)
        }
        else {
            println("Angulo inválido")
        }
    } while (true)
}

fun pedeRepetirOuTerminar(): Boolean {
    do {
        println("Tentar outra vez? (S/N)?")
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
        val larguraGrafico = 80
        val alturaGrafico = 20
        val grafico = Chart(larguraGrafico, alturaGrafico)
        val planetas = listOf("mercurio","venus","terra","marte","jupiter","saturno","urano","neptuno","plutao","lua")
        var planeta = planetas.random()
        var g = gravidade(planeta)
        var distanciaRede = 40.00
        var alturaRede = 5.00
        //Menu
        do {
            imprimeTitulo(planeta,distanciaRede,alturaRede)
            val opcao = validaOpcao()
            if (opcao == 1) {
                do {
                    //Pede dados de lançamento
                    val altura = pedeAltura()
                    val v0 = pedeVelocidade()
                    var angulo = 0.0
                    if (altura != 0.0 && v0 != 0.0) {
                        angulo = pedeAngulo()
                    } else if (altura == 0.0 && v0 != 0.0) {
                        angulo = pedeAngulo()
                    }
                    //Calcula dados lançamento
                    val anguloRad = arredondar(pRadianos(angulo))
                    val v0y = (v0 * (sin(anguloRad)))    //Isto calcula a velocida em X
                    val v0x = (v0 * (cos(anguloRad)))
                    val tempoVoo = fResolvente(-(g / 2), v0y, altura)
                    val alcance = (v0 * arredondar((cos(anguloRad)))) * tempoVoo
                    val tempoSubida = v0y / g
                    val alturaMaxima = altura + v0y * tempoSubida - (g / 2) * (tempoSubida).pow(2)

                    if ((v0 == 0.0 && altura == 0.0) || (altura == 0.0 && angulo == 0.0)) {
                        println("\nO projétil não se moveu, não acertou na rede :(\n")
                    } else if (v0 == 0.0) {
                        println(
                            "\nO projétil foi largado a ${arredondar(altura)}m de altura! Chegou ao solo em ${
                                arredondar(
                                    tempoVoo
                                )
                            }s mas não acertou na rede :(\n"
                        )
                    } else if (angulo == 90.0) {
                        println(
                            "\nO projétil foi lançado para cima. De ${arredondar(altura)}m para ${
                                arredondar(
                                    alturaMaxima
                                )
                            }m de altura mas não acertou na rede  :(\n"
                        )
                    } else {
                        var x = 0.0
                        println("\nAltura em função da posição:")

                        //Calcula se acertou na Rede
                        var caiuAntesOuDepois = ""
                        var acertouNaRede = false
                        if (altura + v0y * (distanciaRede / v0x) - (g / 2) * (distanciaRede / v0x).pow(2) in 0.0..alturaRede) {
                            acertouNaRede = true
                        }

                        //Cria os pontos da trajetoria no grafico
                        do {
                            val yEmX = altura + v0y * (x / v0x) - (g / 2) * (x / v0x).pow(2)
                            grafico.ponto(x, yEmX)
                            x += distanciaRede / 10
                        } while (yEmX > 0 && x < distanciaRede * 2)

                        //Cria os pontos da Rede no grafico
                        var count: Double = alturaRede
                        while (count >= 0) {
                            grafico.ponto(distanciaRede, count)
                            count -= alturaRede / 10
                        }
                        //Imprime o grafico
                        grafico.draw()
                        if (acertouNaRede) {
                            println()
                            println(
                                """
        ==========================================
                       🎉 PARABÉNS! 🎉
                  🏆 Acertaste na Rede! 🏆
        ==========================================
                 📊 DADOS DO LANÇAMENTO 📊
        ==========================================
        🌍 Planeta:                  ${planeta.replaceFirstChar { it.uppercase() }}
        🌌 Aceleração Gravítica:     ${arredondar(g)} m/s²
        🏔️ Altura Inicial:           ${arredondar(altura)} m
        🏃‍♂️ Velocidade Inicial:       ${arredondar(v0)} m/s
        🎯 Ângulo de Lançamento:     ${arredondar(angulo)} º
        ⬆️ Velocidade Vertical:      ${arredondar(v0y)} m/s
        ➡️ Velocidade Horizontal:    ${arredondar(v0x)} m/s
        ⏱️ Tempo de Voo:             ${arredondar(tempoVoo)} s
        ⬆️ Tempo de Subida:          ${arredondar(tempoSubida)} s
        📏 Alcance:                  ${arredondar(alcance)} m
        ⛰️ Altura Máxima:            ${arredondar(alturaMaxima)} m
        ==========================================
    """.trimIndent()
                            )
                        } else if (!acertouNaRede) {
                            if (alcance > distanciaRede) {
                                caiuAntesOuDepois = "     O jogador passou por cima da rede!"
                            }
                            if (alcance < distanciaRede) {
                                caiuAntesOuDepois = "   O jogador caiu antes de chegar à rede!"
                            }
                            println(
                                """
        ==========================================
                ❌ NÃO ACERTASTE NA REDE ❌
        $caiuAntesOuDepois
        ==========================================
                 📊 DADOS DO LANÇAMENTO 📊
        ==========================================
        🌍 Planeta:                  ${planeta.replaceFirstChar { it.uppercase() }}
        🌌 Aceleração Gravítica:     ${arredondar(g)} m/s²
        🏔️ Altura Inicial:           ${arredondar(altura)} m
        🚀 Velocidade Inicial:       ${arredondar(v0)} m/s
        🎯 Ângulo de Lançamento:     ${arredondar(angulo)} º
        ⬆️ Velocidade Vertical:      ${arredondar(v0y)} m/s
        ➡️ Velocidade Horizontal:    ${arredondar(v0x)} m/s
        ⏱️ Tempo de Voo:             ${arredondar(tempoVoo)} s
        ⬆️ Tempo de Subida:          ${arredondar(tempoSubida)} s
        📏 Alcance:                  ${arredondar(alcance)} m
        ⛰️ Altura Máxima:            ${arredondar(alturaMaxima)} m
        ==========================================
    """.trimIndent()
                            )
                        }
                    }
                    val gerarOutroGrafico = pedeRepetirOuTerminar()
                } while (gerarOutroGrafico)
            }
            if (opcao == 2) {
                planeta = pedePlaneta()
                g = gravidade(planeta)
            }
            if (opcao == 3) {
                do {
                    println("Introduza a distancia da rede:")
                    distanciaRede = readln().toDoubleOrNull() ?: -1.0
                    if (distanciaRede <= 0) {
                        println("Distancia invalida, o valor deve ser maior que 0.")
                    }
                } while (distanciaRede <= 0)

                do {
                    println("Introduza a altura da rede:")
                    alturaRede = readln().toDoubleOrNull() ?: -1.0
                    if (alturaRede <= 0) {
                        println("Altura invalida, o valor deve ser maior que 0.")
                    }
                } while (alturaRede <= 0)
            }

            if (opcao == 4) {
                println()
                println("""
        ==========================================
                 🏅 INSTRUÇÕES DO JOGO 🏅
        ==========================================
        
        🎢 Bem-vindo ao Recinto de Jogos Radicais! 🎢
        
        🌟 Desafio: Lança um jogador com o canhão e 
           tenta acertar na rede! 🌟
        
        🛡️ Para garantir a segurança do jogador, 
           uma rede foi posicionada:
        - Distância: $distanciaRede metros
        - Altura: $alturaRede metros
        
        🌟 Para jogar, introduz os seguintes dados:
        1️⃣ Altura de onde o jogador será lançado.
        2️⃣ Velocidade inicial do lançamento.
        3️⃣ Ângulo de lançamento.
        
        🚀 O jogador está a contar contigo! 
            Não podes falhar! 🚀
        
        ==========================================
             🔍 INFORMAÇÕES ADICIONAIS 🔍
        ==========================================
        🌍 O jogo inicia-se num planeta aleatório.
        🔄 Podes alterar o planeta na opção 2 do 
           Menu Principal.
        🎯 Também no Menu Principal, na opção 3, 
           podes modificar a posição e a altura 
           da rede para ajustar a dificuldade.
        
        Boa sorte!!! 🎉
        ==========================================
    """.trimIndent())
                println("Pressiona uma tecla para continuar")
                readln()
            }

            if (opcao == 5) {
                println("""
        ==========================================
         📚 PROJETO DE FUNDAMENTOS DE FÍSICA 📚
        ==========================================
        Este projeto foi realizado no âmbito da 
        disciplina de Fundamentos de Física pelos:
        
        👨‍🎓 Alunos:
        ------------------------------------------
        🔹 Ricardo Santos
        🔹 Nuno Tainha
        🔹 Duarte Martins
        🔹 Duarte Veríssimo
        ------------------------------------------
        
        🗓️ Ano Letivo: 2024/2025 
        📆 (1º Semestre)
        ==========================================
    """.trimIndent())
                println("Pressiona uma tecla para continuar")
                readln()
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