package indigo

private const val NUMBER_OF_CARDS = 6

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
    private var currentPlayer: Player = Player("Player",
                                               true,
                                               true)
    private var lastWinner: Player = Player("Player",
                                            true,
                                            true)

    val currentPlayerReadOnly: Player
        get() = currentPlayer

    init {
        table.addCards(deck.takeCards(4))
        players.forEach {
            dealCards(it,
                      NUMBER_OF_CARDS)
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
        return "Initial cards on the table: ${table.getCardsAsString()}"
    }

    fun getChoosePrompt(): String = "Choose a card to play (1-${currentPlayer.getNumberOfCardsInHand()}):"

    fun getCurrentPlayerHandAsString(): String {
        return currentPlayer.getCardsInHandAsString()
    }

    fun getStatus(): Status {
        return status
    }

    private fun processCard(card: Card) {
        //file.appendText("${deck.getCardsAsString()}\n")
        val topCard = table.getTopCard()

        if (card.face == topCard.face || card.suit == topCard.suit) {
            lastWinner = currentPlayer
            println("${currentPlayer.name} wins cards")

            currentPlayer.addScore(table.getCardsPoints() + card.face.points)
            currentPlayer.addEarnedCards(table.throwAllCards()
                                             .plus(card)
                                             .toMutableList())
            printPlayersScore()
            println()
        } else {
            table.addCard(card)
        }
    }

    private fun printPlayersScore() {
        val human = players.first { it.isHuman }
        val computer = players.first { !it.isHuman }
        print("Score: Player ${human.getScore()} - Computer ${computer.getScore()}\n")
        print("Cards: Player ${human.getNumberOfCardsEarned()} - Computer ${computer.getNumberOfCardsEarned()}\n")
    }

    fun action(value: String) {
        when (value) {
            "robotTurn" -> {
                status = Status.STARTED
                val card = (currentPlayer as Computer).throwCard(table.getCards())
                println("${currentPlayer.name} plays $card")
                processCard(card)
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
                processCard(card)
            }
        }

        if (players.sumOf { it.getNumberOfCardsInHand() } == 0 && deck.getNumberOfCards() == 0) {
            println(getTableStatus(this))
            lastWinner.addScore(table.getCardsPoints())
            lastWinner.addEarnedCards(table.throwAllCards())
            val winner = players.maxByOrNull { it.getNumberOfCardsEarned() } ?: players.first { it.isFirst }
            winner.addScore(3)
            status = Status.FINISHED
            printPlayersScore()
            println("Game Over")
            return
        }

        if (currentPlayer.getNumberOfCardsInHand() == 0 && deck.getNumberOfCards() >= NUMBER_OF_CARDS) {
            dealCards(currentPlayer,
                      NUMBER_OF_CARDS)
        }

        currentPlayer = players[(players.indexOf(currentPlayer) + 1) % players.size]
    }

    private fun checkNumber(value: String): Boolean {
        val regex = Regex("^[1-${currentPlayer.getNumberOfCardsInHand()}]")
        return regex.matches(value)
    }

    companion object {
        fun getTableStatus(game: Game): String {
            return game.table.status()
        }
    }
}
