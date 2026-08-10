package aula2

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
fun main() {
    val idade = 42

    //when: semelhante ao switch
    val resultado = when {
        idade > 18 -> "Maior de idade"
        idade > 65 -> "Idoso"
        else -> println("Menor de idade");
    }

    println("Resultado: $resultado")

    //===estruturas de repetição===

    for (i in 1..3) {
        println(i)
    }
    print("\n")

    //pulando
    for(i in 2..10 step 2) {
        println(i)
    }
    print("\n")

    //contagem regressiva
    for(i in 5 downTo 1) {
        println(i)
    }
    print("\n")

}