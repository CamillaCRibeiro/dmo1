package aula3

fun main() {
    mensagem("Camilla")
    println(calculo(2, 3))
    println(calculo2(2, 6))
    println("Média: ${ media(5.0, 7.0, 6.0)}")
    val notas = doubleArrayOf(7.0, 6.0)
    println("Segunda Média: ${ media(*notas) }")
    val nome = "José"
    println("Tamanho: ${nome.tamanho()}")
    println("Dobro de 44: ${ 44.dobro() }")
}

fun mensagem(nome:String) {
    println("Aprendendo sobre funções em Kotlin")
    println("$nome está adorando Kotlin")
}

fun calculo(num1:Int, num2:Int): Int {

    return (num1 + num2) * 2
}

fun calculo2(num1:Int, num2: Int) = (num1 + num2) *2

fun media(vararg nota: Double): Double {
    var contador:Double = 0.0
    var somatoria:Double = 0.0

    for (n in nota) {
        somatoria += n
        contador++
    }
    return somatoria / contador
}

fun String.tamanho(): Int {
    return this.length
}

fun Int.dobro(): Int {
    return this * 2
}

val lista = listOf(40, 50, 60)