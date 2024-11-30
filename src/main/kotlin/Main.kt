import kotlin.math.*




//Imprime o Menu Principal com os dados do planeta atual e altura+posicao da rede
fun imprimeTitulo(altura:Double,planeta:String,distanciaRede: Double, alturaRede: Double) {
    println()
    println("""
        ==========================================
                 🚀 PROJETO DE FÍSICA 🚀
                    🌟 Acerta no Rede! 🌟
        ==========================================
        🌍 Planeta atual:     ${planeta.replaceFirstChar { it.uppercase() }}
        🧨 Altura do Canhão:  ${altura}m
        📏 Distância da Rede: ${arredondar(distanciaRede)}m
        📐 Altura da Rede:    ${arredondar(alturaRede)}m
        ==========================================
                🔽 MENU PRINCIPAL 🔽
        ==========================================
         1 - Lançar Canhão
         2 - Escolher Planeta
         3 - Escolher Rede
         4 - Escolher Altura do Canhão
         5 - Instruções
         6 - Soluções
         7 - Informações Sobre Projeto
         0 - Sair
        ==========================================
    """.trimIndent())
}
//Arredonda Doubles para duas casas decimais
fun arredondar(num:Double): Double {
    var numero = (round(num*100))/100
    if (numero == -0.0) {
        numero = 0.0
    }
    return numero
}
//Pede ao utilizar, verifica, e retorna uma opcao valida
fun validaOpcao(): Int {
    do {
        val opcao = readln().toIntOrNull()
        if (opcao != null && opcao in 0..7) {
            return opcao
        }
        else {
            println("Seleciona uma opção disponível")
        }
    } while (true)
}
//Converte graus para radianos
fun pRadianos(a:Double) = (a * PI/180)
//Retorna a gravidade do planeta introduzido (parametro ja validado por pedePlaneta())
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
//Pede ao utilizar, verifica, e retorna um planeta valido
fun pedePlaneta(): String {
    do {
        println("Introduz um planeta do sistema solar (ou Enter para escolher a Terra)")
        when (val planeta = readln().lowercase()) {
            "" -> {
                print("\nEscolheste o planeta Terra!\n")
                println("Pressione ENTER para continuar")
                readln()
                return "terra"
            }
            else -> {
                if (gravidade(planeta) != -1.0) {
                    print("\nEscolheste o planeta ${planeta.replaceFirstChar { it.uppercase() }}!\n")
                    println("Pressione ENTER para continuar")
                    readln()
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
//Pede ao utilizar, verifica, e retorna uma altura valida
fun pedeAltura():Double {
    do {
        println("Introduz a altura do canhão (ou Enter para altura 0)")
        val altura = readln()
        if (altura == "") {
            return 0.0
        }
        val alturaValidada = altura.toDoubleOrNull() ?: -1.0
        if (alturaValidada >= 0.0) {
            return alturaValidada
        }
        else {
            println("Altura inválida. A altura deve ser um valor positivo")
        }
    } while (true)
}
//Pede ao utilizar, verifica, e retorna uma velocidade valida
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
//Pede ao utilizar, verifica, e retorna um angulo em graus inteiro valido
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
//Calcula a altura do projetil na posicao horizontal da rede
fun alturaNaPosicaoDaRede(g:Double,alturaInicial:Double,distanciaRede:Double,v0:Double,anguloemRad:Double): Double {
    val altura = alturaInicial + v0*sin(anguloemRad) * (distanciaRede / (v0*cos(anguloemRad))) - (g/2) * (distanciaRede / (v0*cos(anguloemRad))).pow(2)
    return altura
}

fun acertouNaRede(altura:Double,v0y:Double,v0x:Double,distanciaRede:Double,g:Double,alturaRede:Double): Boolean {
    val yEmQueTocaNaRede = altura + v0y * (distanciaRede / v0x) - (g / 2) * (distanciaRede / v0x).pow(2)
    return yEmQueTocaNaRede in 0.0..alturaRede
}


//Imprime os dados de lançamento
fun imprimeDadosLancamento(anguloEmRad:Double,tentativas:Int,descricaoDoMovimento:String,distanciaRede:Double,alturaRede:Double,acertouNaRede:Boolean,planeta: String,g:Double,altura:Double,v0:Double,angulo:Double,v0y:Double,v0x:Double,tempoVoo:Double,tempoSubida:Double,alcance:Double,alturaMaxima:Double) {
    if (!acertouNaRede) {
        println(
            """
        ==========================================
                ❌ NÃO ACERTASTE NA REDE ❌
        $descricaoDoMovimento
        ==========================================
                 📊 DADOS DO LANÇAMENTO 📊
        ==========================================
        🌍 Planeta:                  ${planeta.replaceFirstChar { it.uppercase() }}
        🌌 Aceleração Gravítica:     ${arredondar(g)}m/s²
        📏 Distância da Rede:        ${arredondar(distanciaRede)}m
        📐 Altura da Rede:           ${arredondar(alturaRede)}m
        🏔️ Altura Inicial:           ${arredondar(altura)}m
        🚀 Velocidade Inicial:       ${arredondar(v0)}m/s
        🎯 Ângulo de Lançamento:     ${arredondar(angulo)}º
        ⬆️ Velocidade Vertical:      ${arredondar(v0y)}m/s
        ➡️ Velocidade Horizontal:    ${arredondar(v0x)}m/s
        ⏱️ Tempo de Voo:             ${arredondar(tempoVoo)}s
        ⬆️ Tempo de Subida:          ${arredondar(tempoSubida)}s
        📏 Alcance:                  ${arredondar(alcance)}m
        ⛰️ Altura Máxima:            ${arredondar(alturaMaxima)}m
        📐 Altura na posição Rede:   ${if (arredondar(alturaNaPosicaoDaRede(g,altura,distanciaRede,v0,anguloEmRad)) > 0)"${(arredondar(alturaNaPosicaoDaRede(g,altura,distanciaRede,v0,anguloEmRad)))}m" else "ND"}
        ==========================================
        Tentativas: $tentativas
        ==========================================
    """.trimIndent())
    }
    else {
        println(
            """
        ==========================================
                       🎉 PARABÉNS! 🎉
                  🏆 Acertaste na Rede! 🏆
                       Tentativas : $tentativas
        ==========================================
                 📊 DADOS DO LANÇAMENTO 📊
        ==========================================
        🌍 Planeta:                  ${planeta.replaceFirstChar { it.uppercase() }}
        🌌 Aceleração Gravítica:     ${arredondar(g)} m/s²
        📏 Distância da Rede:        ${arredondar(distanciaRede)}m
        📐 Altura da Rede:           ${arredondar(alturaRede)}m
        🏔️ Altura Inicial:           ${arredondar(altura)} m
        🚀 Velocidade Inicial:       ${arredondar(v0)} m/s
        🎯 Ângulo de Lançamento:     ${arredondar(angulo)}º
        ⬆️ Velocidade Vertical:      ${arredondar(v0y)} m/s
        ➡️ Velocidade Horizontal:    ${arredondar(v0x)} m/s
        ⏱️ Tempo de Voo:             ${arredondar(tempoVoo)} s
        ⬆️ Tempo de Subida:          ${arredondar(tempoSubida)} s
        📏 Alcance:                  ${arredondar(distanciaRede)} m
        ⛰️ Altura Máxima:            ${arredondar(alturaMaxima)} m
        📐 Altura na posição Rede:   ${arredondar(alturaNaPosicaoDaRede(g,altura,distanciaRede,v0,anguloEmRad))}m
        ==========================================

    """.trimIndent())
    }
}
//Imprime as instruçoes (com os valores selecionados para a rede)
fun imprimeInstrucoes(distanciaRede:Double,alturaRede:Double) {
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
        - Velocidade inicial do lançamento.
        - Ângulo de lançamento.
        
        🚀 O jogador está a contar contigo! 
            Não podes falhar! 🚀
      
        ==========================================
             🔍 INFORMAÇÕES ADICIONAIS 🔍
        ==========================================
        🌍 O jogo inicia-se no planeta Terra.
        🔄 Podes alterar o planeta na opção 2 do 
           Menu Principal.
        🎯 Também no Menu Principal podes modificar
           a posição e a altura da rede, assim como
           a altura do canhão para ajustar a
           dificuldade.
        
        Boa sorte!!! 🎉
        ==========================================
    """.trimIndent())
}
fun imprimeInfos() {
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
        🏫 Universidade Lusófona - ECATI - DEISI
        💻 Engenharia Informártica
        
        🗓️ Ano Letivo: 2024/2025 
        📆 (1º Semestre)
        ==========================================
    """.trimIndent())
}
//Pergunta ao utilizador se quer tentar outra vez e retorna true(Sim) ou false (Nao)
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
    //Variaveis globais
    var planeta = "terra"
    var g = gravidade(planeta)
    var distanciaRede = 40.0
    var alturaRede = 5.0
    var altura = 0.0
    val separarMenus =
        "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n"
    //Definiçoes do grafico
    val larguraGrafico = 100
    val alturaGrafico = 20
    val grafico = Chart(larguraGrafico, alturaGrafico)


    //Apresentação
    println(separarMenus)
    imprimeInstrucoes(distanciaRede, alturaRede)
    println("Pressiona ENTER para continuar")
    readln()

    //Menu
    do {
        //Imprime Menu
        print(separarMenus)
        imprimeTitulo(altura, planeta, distanciaRede, alturaRede)
        //Espera que o utilizador escolha uma opcao
        val opcao = validaOpcao()
        //Opcao 1 - Iniciar lançamento
        if (opcao == 1) {
            var tentativas = 0
            do {
                tentativas += 1
                //Pede dados de lançamento

                val angulo = pedeAngulo()
                var v0 = 0.0
                if (altura != 0.0) {
                    v0 = pedeVelocidade()
                } else if (altura == 0.0 && angulo != 0.0) {
                    v0 = pedeVelocidade()
                }

                //Calcula dados lançamento
                val anguloRad = (pRadianos(angulo))
                val v0y = (v0 * (sin(anguloRad)))
                val v0x = (v0 * (cos(anguloRad)))
                var tempoVoo = fResolvente(-(g / 2), v0y, altura)
                val alcance = (v0 * ((cos(anguloRad)))) * tempoVoo
                val tempoSubida = v0y / g
                val alturaMaxima = altura + v0y * tempoSubida - (g / 2) * (tempoSubida).pow(2)
                var acertouNaRede = false
                //Se (Vo = 0 e altura = 0) ou (altura = 0 e angulo = 0) --> O projetil nao se moveu.
                if ((v0 == 0.0 && altura == 0.0) || (altura == 0.0 && angulo == 0.0)) {
                    print(separarMenus)
                    println("\nO jogador não se moveu! Não acertaste na rede :(")
                    println("Tentativas: $tentativas\n")
                }
                //Se v0 = 0 -> O projetil foi largado a Xm de altura.
                else if (v0 == 0.0) {
                    print(separarMenus)
                    println(
                        "\nO jogador foi largado a ${arredondar(altura)}m de altura! Caiu no chão em ${
                            arredondar(
                                tempoVoo
                            )
                        }s e não acertou na rede :("
                    )
                    println("Tentativas: $tentativas\n")
                }
                //Se angulo = 0 -> O projetil foi lançado para cima.
                else if (angulo == 90.0) {
                    print(separarMenus)
                    println(
                        "\nO jogador foi lançado para cima. De ${arredondar(altura)}m para ${arredondar(alturaMaxima)}m de altura, caiu no chão e não acertou na rede :("
                    )
                    println("Tentativas: $tentativas\n")
                }
                //Se o projetil se move em X é desenhado o grafico
                else {
                    var x = 0.0
                    print(separarMenus)
                    println("\nAltura em função da posição:")
                    //Calcula se acertou na Rede e em que ponto toca na rede
                    val yEmQueTocaNaRede = altura + v0y * (distanciaRede / v0x) - (g / 2) * (distanciaRede / v0x).pow(2)
                    if (yEmQueTocaNaRede in 0.0..alturaRede) {
                        acertouNaRede = true
                    }
                    //Desenha a rede no grafico
                    var count: Double = alturaRede
                    grafico.ponto(0.0, altura)
                    while (count >= 0) {
                        grafico.ponto(distanciaRede, count)
                        count -= alturaRede / 15
                    }
                    //Se nao acertar na rede cria os pontos da trajetoria no grafico até + 1/2 da distancia da rede
                    //Nao cria pontos onde Y (altura) é negativo
                    if (!acertouNaRede) {
                        grafico.ponto(0.0, altura)
                        while (x < distanciaRede * 2) {
                            val yEmX = altura + v0y * (x / v0x) - (g / 2) * (x / v0x).pow(2)
                            if (yEmX > 0) {
                                grafico.ponto(x, yEmX)
                            }
                            x += distanciaRede/25
                        }
                    }
                    //Se acertar na rede cria os pontos da trajetoria apenas até à rede
                    else {
                        do {
                            val yEmX = altura + v0y * (x / v0x) - (g / 2) * (x / v0x).pow(2)
                            grafico.ponto(x, yEmX)
                            x += distanciaRede/50
                        } while (x < distanciaRede)
                        tempoVoo = fResolvente(-(g / 2), v0y, altura - yEmQueTocaNaRede)
                    }
                    //Imprime o grafico com todos os pontos que foram criados
                    grafico.draw()
                    //Se acertou na rede imprime os dados considerando que o movimento parou na rede
                    if (acertouNaRede) {
                        imprimeDadosLancamento(
                            anguloRad,
                            tentativas,
                            "",
                            distanciaRede,
                            alturaRede,
                            true,
                            planeta,
                            g,
                            altura,
                            v0,
                            angulo,
                            v0y,
                            v0x,
                            tempoVoo,
                            tempoSubida,
                            alcance,
                            alturaMaxima
                        )
                    }
                    //Se nao acertou na rede imprime os dados considerando que o movimento termina quando chega ao solo
                    else if (!acertouNaRede) {
                        var descricaoDoMovimento = ""
                        if (alcance > distanciaRede) {
                            descricaoDoMovimento = "         O jogador passou por cima!"
                        }
                        if (alcance < distanciaRede) {
                            descricaoDoMovimento = "           O jogador caiu antes!"
                        }
                        imprimeDadosLancamento(
                            anguloRad,
                            tentativas,
                            descricaoDoMovimento,
                            distanciaRede,
                            alturaRede,
                            false,
                            planeta,
                            g,
                            altura,
                            v0,
                            angulo,
                            v0y,
                            v0x,
                            tempoVoo,
                            tempoSubida,
                            alcance,
                            alturaMaxima
                        )
                    }
                }
                //Pergunta ao utilzador se quer repetir o lançamento com outros dados
                var gerarOutroGrafico = false
                if (acertouNaRede) {
                    println("Pressiona ENTER para continuar")
                    readln()
                }
                if (!acertouNaRede) {
                    gerarOutroGrafico = pedeRepetirOuTerminar()
                }

                //Repete a opcao 1 Se o utilizador escolher repetir o lançamento
            } while (gerarOutroGrafico)
        }
        //Opcao 2 - Escolher planeta
        if (opcao == 2) {
            print(separarMenus)
            planeta = pedePlaneta()
            g = gravidade(planeta)
            print(separarMenus)
        }
        //opcao 3 - Alterar Rede
        if (opcao == 3) {
            print(separarMenus)
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
            println("Escolhida rede de distância ${distanciaRede}m e altura ${alturaRede}m!")
            println("Pressiona ENTER para continuar")
            readln()
        }
        //opcao 4 - Escolher altura do canhão
        if (opcao == 4) {
            print(separarMenus)
            altura = pedeAltura()
            println("Altura do canhão definida para ${altura}m!")
            println("Pressiona ENTER para continuar")
            readln()
        }
        //Opcao 5 - Instruçoes do jogo
        if (opcao == 5) {
            print(separarMenus)
            imprimeInstrucoes(distanciaRede, alturaRede)
            println("Pressiona ENTER para continuar")
            readln()
        }
        //opcao 7 - Inf sobre o projeto
        if (opcao == 7) {
            print(separarMenus)
            imprimeInfos()
            println("Pressiona ENTER para continuar")
            readln()
        }
        //opcao 6 - Mostra solucoes angulo e v0
        if (opcao == 6) {
            println(separarMenus)
            val limiteVelocidade = 300
            var pontos: MutableList<Pair<Int, Int>> = mutableListOf()
            var anguloGraus = 0
            var velIni = 0.0
            var coordenadasSolucao = mutableListOf<Pair<Int, Double>>()
            grafico.ponto(0.0,limiteVelocidade.toDouble())
            while (anguloGraus in 0..90) {
                var anguloRads = pRadianos(anguloGraus.toDouble())
                velIni = 0.0


                while (velIni <= limiteVelocidade) {

                    velIni += 0.01
                    velIni = arredondar(velIni)
                    if (acertouNaRede(altura,velIni*sin(anguloRads),velIni*cos(anguloRads),distanciaRede,g,alturaRede)) {
                        //grafico.ponto(anguloGraus.toDouble(),velIni)
                        coordenadasSolucao.add(Pair(anguloGraus,velIni))
                    }
                }

                anguloGraus += 1
            }

            println("""
        ==========================================
                         SOLUÇÕES
        ==========================================
        🌍 Planeta:           ${planeta.replaceFirstChar { it.uppercase() }}
        🧨 Altura do Canhão:  ${altura}m
        📏 Distância da Rede: ${arredondar(distanciaRede)}m
        📐 Altura da Rede:    ${arredondar(alturaRede)}m
        ==========================================
    """.trimIndent())

            // Definir tamanhos das colunas
            val col1Width = 12  // Largura da coluna de ângulos
            val col2Width = 28  // Largura da coluna de velocidades
            val tableWidth = col1Width + col2Width + 7

            //Imprimir Cabeçalho
            println("╔" + "═".repeat(col1Width) + "╦" + "═".repeat(col2Width) + "╗")
            println("║ " + "Ângulo (º)".padEnd(col1Width - 1) + "║ " + "Velocidades Iniciais (m/s)".padEnd(col2Width - 1) + "║")
            println("╠" + "═".repeat(col1Width) + "╬" + "═".repeat(col2Width) + "╣")

            val grupos = coordenadasSolucao.groupBy({ it.first }, { it.second })
            // Imprimir os dados por ângulo
            for ((angulo, velocidades) in grupos) {
                val minVel = velocidades.minOrNull() ?: 0.0
                val maxVel = velocidades.maxOrNull() ?: 0.0
                val intervalo = "$minVel a $maxVel"
                println("║ " + "$angulo".padEnd(col1Width - 1) + "║ " + intervalo.padEnd(col2Width - 1) + "║")
            }
            println("╚" + "═".repeat(col1Width) + "╩" + "═".repeat(col2Width) + "╝")

            //grafico.draw()
            println("Pressiona ENTER para continuar")
            readln()
        }


    } while (opcao != 0)
    print(separarMenus)
    print(
        """
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
 ^^

"""
    )
    println("Até à próxima!")
    Thread.sleep(2000)
}


