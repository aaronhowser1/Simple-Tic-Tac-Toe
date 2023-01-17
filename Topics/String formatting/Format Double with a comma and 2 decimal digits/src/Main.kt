fun main() {
    val value = readln().toDouble()
//    val value = 1264.86453

    // Change this line of code
    val doubleString = String.format("%,.2f",value)

    println(doubleString )
}