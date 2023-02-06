package indigo

class Table {
    private var cards: MutableList<Card> = mutableListOf()

    fun addCards(cards: MutableList<Card>) {
        this.cards.addAll(cards)
    }

    fun addCard(card: Card) {
        this.cards.add(card)
    }

    fun status(): String {
        if (cards.isEmpty())
            return "No cards on the table"

        return "\n${cards.size} cards on the table, and the top card is ${cards.last()}"
    }

    fun getTopCard(): Card = cards.lastOrNull() ?: Card(Face.UNDEFINED,
                                                        Suit.UNDEFINED)

    fun getCards(): List<Card> = cards.toList()

    fun getCardsPoints(): Int = cards.sumOf { it.face.points }

    fun getCardsAsString(): String = cards.joinToString(" ")

    fun throwAllCards(): MutableList<Card> =
        cards.take(cards.size)
            .toMutableList()
            .also { cards.removeAll(it) }

}
