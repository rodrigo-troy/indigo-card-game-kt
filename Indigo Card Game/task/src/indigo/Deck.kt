package indigo

class Deck {
    private var cards: MutableList<Card> = mutableListOf()

    init {
        reset()
    }

    fun getCardsAsString(): String = "Deck cards: ${cards.joinToString(separator = " ")}"

    private fun reset() {
        cards.clear()
        Suit.values()
            .filter { it != Suit.UNDEFINED }
            .forEach { suit ->
                Face.values()
                    .filter { it != Face.UNDEFINED }
                    .forEach { face ->
                        cards.add(Card(face,
                                       suit))
                    }
            }
        cards.shuffle()
        cards.shuffle()
    }

    fun takeCards(numberOfCards: Int): MutableList<Card> {
        return cards.take(numberOfCards)
            .toMutableList()
            .also { cards.removeAll(it) }
    }

    fun getNumberOfCards(): Int = cards.size
}
