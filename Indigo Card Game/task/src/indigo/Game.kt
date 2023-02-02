package indigo

/**
 * Created with IntelliJ IDEA.
$ Project: Indigo Card Game
 * User: rodrigotroy
 * Date: 02-02-23
 * Time: 19:09
 */
class Game {
    private var status = Status.START
    private val cards = listOf("A",
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

    private val suits = listOf("♦",
                               "♥",
                               "♠",
                               "♣")

    private var deck = cards.flatMap { card -> suits.map { suit -> card + suit } }

    fun getStatus() = status

    fun printCards() {
        println(cards.joinToString(" "))
        println()
        println(suits.joinToString(" "))
        println()
        println(deck.joinToString(" "))
    }

    fun action(value: String) {
        when (value) {
            "reset" -> {
                deck = cards.flatMap { card -> suits.map { suit -> card + suit } }
                println("Card deck is reset.")
            }

            "shuffle" -> {
                deck = deck.shuffled()
                println("Card deck is shuffled.")
            }

            "get" -> {
                println("Number of cards:")
                var number = readln()

                if (!isValidNumber(number)) {
                    return
                }

                println(deck.take(number.toInt())
                            .joinToString(" "))

                deck = deck.drop(number.toInt())
            }

            "exit" -> {
                println("Bye!")
                status = Status.FINISHED
            }

            else -> {
                println("Wrong action.")
            }
        }
    }

    private fun isValidNumber(number: String): Boolean {
        if (Regex("^([1-9]|[1-4][0-9]|5[0-2])\$").matches(number)) {
            val numberInt = number.toInt()

            if (numberInt > deck.size) {
                println("The remaining cards are insufficient to meet the request.")
                return false
            }

            return true
        } else {
            println("Invalid number of cards.")
            return false
        }
    }
}
