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
    private var players: MutableList<Player> = mutableListOf()
    private var deck: Deck = Deck()
    private var table: Table = Table()
    private var currentPlayer: Player = Player()

    init {
        println("Indigo Card Game")
        table.addCards(deck.takeCards(4))
    }

    fun dealCards(numberOfCards: Int) {
        players.forEach { player ->
            player.addCards(deck.takeCards(numberOfCards))
        }
    }

    fun getInitialCardsAsString(): String {
        return "Initial cards on the table: ${table.getCardsAsString()}"
    }

    fun getChoosePrompt(): String {
        return "Choose a card to play (1-${currentPlayer.getNumberOfCardsInHand()})"
    }

    fun getCurrentPlayerHandAsString(): String {
        return currentPlayer.getCardsInHandAsString()
    }

    fun getTableStatus(): String {
        return table.status()
    }

    fun setPlayFirst(player: Player) {
        currentPlayer = player
    }

    fun addPlayer(player: Player,
                  playFirst: Boolean = false) {
        players.add(player)

        if (playFirst) {
            setPlayFirst(player)
        }
    }

    fun getStatus(): Status {
        return status
    }

    fun action(value: String) {
        when (value) {
            "exit" -> {
                status = Status.FINISHED
                println("Bye!")
            }

            else -> {
                val cardIndex = value.toInt() - 1
                val card = currentPlayer.getCard(cardIndex)

            }
        }
    }
}
