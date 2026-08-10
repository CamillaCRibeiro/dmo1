package aula2.exercicios

// Peça ao usuário para digitar um número inteiro e informe se ele é par ou ímpar, utilizando
// o if como expressão.

fun main() {

    // 1 - Peça ao usuário para digitar um número inteiro e informe se ele é par ou ímpar, utilizando if.
    println("1 - Digite um número inteiro\n")
    var num = readln().toInt();

    if(num % 2 == 0) {
        println("$num é par")
    }
    else {
        println("$num é ímpar")
    }
    println("\n")

    // 2 - Escreva um programa que leia três números inteiros e indique qual o menor valor dentre eles.
    println("Digite três números inteiros\n")
    var num1 = readln().toInt()
    var num2 = readln().toInt()
    var num3 = readln().toInt()

    val menor = minOf(num1, num2, num3)
    println("O menor número é $menor");
    println("\n")

    // 3 - Peça dois números e uma operação (+, -, *, /). Mostre o resultado da operação escolhida.
    //Se a operação for inválida, exiba uma mensagem de erro.

    println("Digite dois números\n")
    var numero1 = readln().toInt()
    var numero2 = readln().toInt()

    println("Escolha uma operação:")
    println("1.....Adição\n")
    println("2.....Subtração\n")
    println("3.....MUltiplicação\n")
    println("4.....Divisão\n")
    var operacao = readln().toInt()

    val resultado = when {
        operacao == 1 -> numero1 + numero2
        operacao == 2 -> numero1 - numero2
        operacao == 3 -> numero1 * numero2
        operacao == 4 -> numero1 / numero2
        else -> println("Opção inválida :P")
    }

    println("Resultado: $resultado")
}