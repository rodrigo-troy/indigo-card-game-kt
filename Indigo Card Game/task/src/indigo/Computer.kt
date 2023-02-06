package indigo

/**
 * Created with IntelliJ IDEA.
$ Project: Indigo Card Game
 * User: rodrigotroy
 * Date: 06-02-23
 * Time: 16:52
 */
class Computer(override val isFirst: Boolean) : Player("Computer",
                                                       false,
                                                       isFirst) {
    fun throwCard(tableCards: List<Card>): Card {
        val topCard = tableCards.last()

        if (this.cardsInHand.size == 1) {
            return this.cardsInHand.removeAt(0)
        }

        if (tableCards.isEmpty()) {
            val cardsGroupedBySuit = cardsInHand.groupBy { it.suit }

            if (cardsGroupedBySuit.size == 1) {
                return this.cardsInHand.removeAt(0)
            }

            return cardsGroupedBySuit.maxOf { it.value.size }
                .let { max ->
                    cardsGroupedBySuit.filter { it.value.size == max }
                        .maxOf { it.value.first().face }
                        .let { maxFace ->
                            cardsGroupedBySuit.filter { it.value.size == max }
                                .filter { it.value.first().face == maxFace }.values.first()
                                .first()
                        }
                }
        }

        return this.cardsInHand.removeAt(0)
    }

    private fun candidateCardsCount(tableCards: List<Card>): Int {
        var matchCardsCount = 0
        tableCards.forEach { tableCard ->
            cardsInHand.forEach { cardInHand ->
                if (cardInHand.face == tableCard.face || cardInHand.suit == tableCard.suit) {
                    matchCardsCount++
                }
            }
        }

        return matchCardsCount
    }


    private fun getCandidateCards(tableCards: List<Card>): List<Card> {
        val candidateCards = mutableListOf<Card>()
        tableCards.forEach { tableCard ->
            cardsInHand.forEach { cardInHand ->
                if (cardInHand.face == tableCard.face || cardInHand.suit == tableCard.suit) {
                    candidateCards.add(cardInHand)
                }
            }
        }

        return candidateCards
    }
}
