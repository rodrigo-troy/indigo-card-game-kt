package indigo

fun main() {
    val cards = listOf("A",
                           "2",
                           "3",
                           "4",
                           "5",
                           "6",
                           "7",
                           "8",
                           "9",
                           "10",
                           "J",
                           "Q",
                           "K")

    val suits = listOf("♦",
                           "♥",
                           "♠",
                           "♣")

    val deck = cards.flatMap { card -> suits.map { suit -> card + suit } }

    println(cards.joinToString(" "))
    println()
    println(suits.joinToString(" "))
    println()
    println(deck.joinToString(" "))
}
