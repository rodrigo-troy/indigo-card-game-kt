package indigo

/**
 * Created with IntelliJ IDEA.
$ Project: Indigo Card Game
 * User: rodrigotroy
 * Date: 03-02-23
 * Time: 14:16
 */
open class Player(val name: String,
                  val isHuman: Boolean,
                  open val isFirst: Boolean) {
    private var cardsEarned: MutableList<Card> = mutableListOf()
    protected var cardsInHand: MutableList<Card> = mutableListOf()
    private var score: Int = 0

    fun getScore(): Int = score

    fun addScore(score: Int) {
        this.score += score
    }

    fun addEarnedCards(cards: MutableList<Card>) {
        cardsEarned.addAll(cards)
    }

    fun addCards(cards: MutableList<Card>) {
        cardsInHand.addAll(cards)
    }

    fun getNumberOfCardsEarned(): Int = cardsEarned.size

    fun getNumberOfCardsInHand(): Int = cardsInHand.size

    open fun getCardsInHandAsString(): String {
        var cardsInHandAsString = ""
        cardsInHand.forEachIndexed { index, card ->
            cardsInHandAsString += "${index + 1})${card} "
        }

        return cardsInHandAsString
    }

    fun throwCard(cardIndex: Int): Card = cardsInHand.removeAt(cardIndex)

    override fun toString(): String =
        "Player(isHuman=$isHuman, isFirst=$isFirst, cardsInHand=$cardsInHand, score=$score)"

}
