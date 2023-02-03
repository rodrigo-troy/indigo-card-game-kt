package indigo

class Table {
    private var cards: MutableList<Card> = mutableListOf()

    fun getTopCard(): Card {
        return cards.last()
    }

    fun addCards(cards: MutableList<Card>) {
        this.cards.addAll(cards)
    }

    fun addCard(card: Card) {
        this.cards.add(card)
    }

    fun removeCard(card: Card): Boolean {
        return this.cards.remove(card)
    }

    fun removeCards(cards: MutableList<Card>): Boolean {
        return this.cards.removeAll(cards)
    }

    fun getCards(): MutableList<Card> {
        return this.cards
    }

    fun status(): String {
        return "${cards.size} cards on the table, and the top card is ${getTopCard()}"
    }

    fun getCardsAsString(): String {
        return cards.joinToString(" ")
    }
}
