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
        return "${cards.size} cards on the table, and the top card is ${cards.last()}"
    }

    fun getCardsCount(): Int {
        return cards.size
    }

    fun getCardsAsString(): String {
        return cards.joinToString(" ")
    }
}
