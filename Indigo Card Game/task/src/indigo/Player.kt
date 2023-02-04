package indigo

/**
 * Created with IntelliJ IDEA.
$ Project: Indigo Card Game
 * User: rodrigotroy
 * Date: 03-02-23
 * Time: 14:16
 */
class Player(val isHuman: Boolean,
             val isFirst: Boolean = false) {
    private var cardsInHand: MutableList<Card> = mutableListOf()

    fun addCards(cards: MutableList<Card>) {
        cardsInHand.addAll(cards)
    }

    fun getNumberOfCardsInHand(): Int {
        return cardsInHand.size
    }


    fun getCardsInHandAsString(): String {
        var cardsInHandAsString = "Cards in hand: "
        cardsInHand.forEachIndexed { index, card ->
            cardsInHandAsString += "${index + 1})${card} "
        }

        return cardsInHandAsString
    }

    fun throwCard(cardIndex: Int): Card {
        return cardsInHand.removeAt(cardIndex)
    }

    override fun toString(): String {
        return "Player(isHuman=$isHuman, cardsInHand=$cardsInHand)"
    }
}