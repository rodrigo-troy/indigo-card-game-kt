package indigo

class Deck {
    private var cards: MutableList<Card> = mutableListOf()

    init {
        reset()
    }

    fun reset() {
        cards.clear()
        Suit.values()
            .filter { it != Suit.UNDEFINED }
            .forEach { suit ->
                Face.values()
                    .forEach { face ->
                        cards.add(Card(face,
                                       suit))
                    }
            }
        cards.shuffle()
    }

    fun deal(): Card {
        return cards.removeAt(0)
    }

    fun takeCards(numberOfCards: Int): MutableList<Card> {
        val cardsToTake = mutableListOf<Card>()
        for (i in 1..numberOfCards) {
            cardsToTake.add(deal())
        }
        return cardsToTake
    }
}
