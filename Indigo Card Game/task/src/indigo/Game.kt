package indigo

/**
 * Created with IntelliJ IDEA.
$ Project: Indigo Card Game
 * User: rodrigotroy
 * Date: 02-02-23
 * Time: 19:09
 */
class Game(private val players: List<Player>) {
    private var status = Status.STARTED
    private val deck: Deck = Deck()
    private val table: Table = Table()
    private var currentPlayer: Player = Player(false)

    val currentPlayerReadOnly: Player
        get() = currentPlayer

    init {
        table.addCards(deck.takeCards(4))
        players.forEach {
            dealCards(it,
                      6)
            if (it.isFirst) {
                currentPlayer = it
            }
        }
    }

    private fun dealCards(player: Player,
                          numberOfCards: Int) {
        player.addCards(deck.takeCards(numberOfCards))
    }

    fun getInitialCardsAsString(): String {
        return "Initial cards on the table: ${table.getCardsAsString()}\n"
    }

    fun getChoosePrompt(): String = "Choose a card to play (1-${currentPlayer.getNumberOfCardsInHand()}):"

    fun getCurrentPlayerHandAsString(): String {
        return currentPlayer.getCardsInHandAsString()
    }

    fun getStatus(): Status {
        return status
    }

    fun action(value: String) {
        when (value) {
            "robotTurn" -> {
                status = Status.STARTED
                val card = currentPlayer.throwCard(0)
                table.addCard(card)
                println("Computer plays $card\n")
            }

            "exit" -> {
                status = Status.EXITING
            }

            else -> {
                status = Status.STARTED
                if (!checkNumber(value)) {
                    status = Status.WRONG_INPUT_NUMBER
                    return
                }

                val cardIndex = value.toInt() - 1
                val card = currentPlayer.throwCard(cardIndex)
                table.addCard(card)
            }
        }

        if (table.getCardsCount() == 52) {
            status = Status.FINISHED
            return
        }

        if (currentPlayer.getNumberOfCardsInHand() == 0 && deck.getNumberOfCards() >= 6) {
            dealCards(currentPlayer,
                      6)
        }

        currentPlayer = players[(players.indexOf(currentPlayer) + 1) % players.size]
    }

    fun checkNumber(value: String): Boolean {
        val regex = Regex("^[1-${currentPlayer.getNumberOfCardsInHand()}]")
        return regex.matches(value)
    }

    companion object {
        fun getTableStatus(game: Game): String {
            return game.table.status()
        }
    }
}
