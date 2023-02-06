package indigo

import java.io.File

/**
 * Created with IntelliJ IDEA.
$ Project: Indigo Card Game
 * User: rodrigotroy
 * Date: 02-02-23
 * Time: 19:09
 */
class Game(private val players: List<Player>,
           val file: File) {
    private var status = Status.STARTED
    private val deck: Deck = Deck()
    private val table: Table = Table()
    private var currentPlayer: Player = Player(false)
    private var lastWinner: Player = Player(false)

    val currentPlayerReadOnly: Player
        get() = currentPlayer

    init {
        file.appendText("${deck.getCardsAsString()}\n")
        file.appendText("${deck.getNumberOfCards()} cards left in the deck\n\n")
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
        file.appendText("${deck.getNumberOfCards()} cards left in the deck\n")
        file.appendText("${if (currentPlayer.isHuman) "Player" else "Computer"} ${currentPlayer.getCardsInHandAsString()}\n")
        val topCard = table.getTopCard()

        if (card.face == topCard.face || card.suit == topCard.suit) {
            lastWinner = currentPlayer
            file.appendText("${if (currentPlayer.isHuman) "Player" else "Computer"} wins cards\n")
            println("${if (currentPlayer.isHuman) "Player" else "Computer"} wins cards")

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
        file.appendText("Score: Player ${human.getScore()} - Computer ${computer.getScore()}\n")
        print("Score: Player ${human.getScore()} - Computer ${computer.getScore()}\n")
        file.appendText("Cards: Player ${human.getNumberOfCardsEarned()} - Computer ${computer.getNumberOfCardsEarned()}\n\n")
        print("Cards: Player ${human.getNumberOfCardsEarned()} - Computer ${computer.getNumberOfCardsEarned()}\n")
    }

    fun action(value: String) {
        when (value) {
            "robotTurn" -> {
                status = Status.STARTED
                val card = currentPlayer.throwCard(0)
                file.appendText("Computer plays $card\n")
                println("Computer plays $card")
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
                file.appendText("Player plays $card\n")
                processCard(card)
            }
        }

        if (players.sumOf { it.getNumberOfCardsInHand() } == 0 && deck.getNumberOfCards() == 0) {
            println(getTableStatus(this))
            lastWinner.addScore(table.getCardsPoints())
            lastWinner.addEarnedCards(table.throwAllCards())
            val winner = players.maxByOrNull { it.getNumberOfCardsEarned() } ?: players.first { it.isFirst }
            winner.addScore(3)
            file.appendText("${if (winner.isHuman) "Player" else "Computer"} get the cards and 3 points\n")
            status = Status.FINISHED
            printPlayersScore()
            println("Game Over")
            return
        }

        if (currentPlayer.getNumberOfCardsInHand() == 0 && deck.getNumberOfCards() >= 6) {
            dealCards(currentPlayer,
                      6)
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
