package aula1

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
fun main() {
    var num1 = 12;
    var num2: Int = num1;

    num1 = 55;

    println("Hello world");
    println(num1);
    println(num2);

    //Leitura de dados

    println("Digite sua idade");
    var idade = readln().toInt();

    println("No ano que vem sua idade será: " + (idade + 1))
}