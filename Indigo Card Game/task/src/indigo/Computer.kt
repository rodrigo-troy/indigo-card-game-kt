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
        if (this.cardsInHand.size == 1) {
            return this.cardsInHand.removeAt(0)
        }

        val cardsGroupedBySuit: Map<Suit, List<Card>> = cardsInHand.groupBy { it.suit }
        val cardsGroupedByFace: Map<Face, List<Card>> = cardsInHand.groupBy { it.face }

        if (tableCards.isEmpty() || this.candidateCardsCount(tableCards) == 0) {
            if (cardsGroupedBySuit.size == 1) {
                return this.cardsInHand.removeAt(0)
            }

            if (!this.allGroupHaveTheSameSize(cardsGroupedBySuit)) {
                val biggestGroupBySuit = cardsGroupedBySuit.maxOf { it.value.size }

                val card = biggestGroupBySuit.let { max ->
                    val filter = cardsGroupedBySuit.filter { it.value.size == max }

                    filter.maxOf { it.value.first().face }.let { maxFace ->
                        filter.filter { it.value.first().face == maxFace }.values.first().first()
                    }
                }

                return this.cardsInHand.removeAt(this.cardsInHand.indexOf(card))
            }

            if (!this.allGroupHaveTheSameSize(cardsGroupedByFace)) {
                val biggestGroupByFace = cardsGroupedByFace.maxOf { it.value.size }

                val card = biggestGroupByFace.let { max ->
                    val filter = cardsGroupedByFace.filter { it.value.size == max }

                    filter.maxOf { it.value.first().face }.let { maxFace ->
                        filter.filter { it.value.first().face == maxFace }.values.first().first()
                    }
                }

                return this.cardsInHand.removeAt(this.cardsInHand.indexOf(card))
            }

            return this.cardsInHand.removeAt(0)
        }

        if (this.candidateCardsCount(tableCards) == 1) {
            val card = getCandidateCards(tableCards).first()
            return this.cardsInHand.removeAt(this.cardsInHand.indexOf(card))
        }

        val candidateCardsGroupedBySuit: Map<Suit, List<Card>> = getCandidateCards(tableCards).groupBy { it.suit }
        val candidateCardsGroupedByFace: Map<Face, List<Card>> = getCandidateCards(tableCards).groupBy { it.face }

        if (!this.allGroupHaveTheSameSize(candidateCardsGroupedBySuit)) {
            val biggestGroupBySuit = candidateCardsGroupedBySuit.maxOf { it.value.size }

            val card = biggestGroupBySuit.let { max ->
                val filter = candidateCardsGroupedBySuit.filter { it.value.size == max }

                filter.maxOf { it.value.first().face }.let { maxFace ->
                    filter.filter { it.value.first().face == maxFace }.values.first().first()
                }
            }

            return this.cardsInHand.removeAt(this.cardsInHand.indexOf(card))
        }

        if (!this.allGroupHaveTheSameSize(candidateCardsGroupedByFace)) {
            val biggestGroupByFace = candidateCardsGroupedByFace.maxOf { it.value.size }

            val card = biggestGroupByFace.let { max ->
                val filter = candidateCardsGroupedByFace.filter { it.value.size == max }

                filter.maxOf { it.value.first().face }.let { maxFace ->
                    filter.filter { it.value.first().face == maxFace }.values.first().first()
                }
            }

            return this.cardsInHand.removeAt(this.cardsInHand.indexOf(card))
        }

        val card = this.getCandidateCards(tableCards).first()
        return this.cardsInHand.removeAt(this.cardsInHand.indexOf(card))
    }

    private fun <T> allGroupHaveTheSameSize(map: Map<T, List<Card>>): Boolean {
        val size = map.values.first().size

        map.forEach { (_, cards) ->
            if (cards.size != size) {
                return false
            }
        }

        return true
    }

    private fun candidateCardsCount(tableCards: List<Card>): Int {
        return getCandidateCards(tableCards).size
    }

    private fun getCandidateCards(tableCards: List<Card>): List<Card> {
        if (tableCards.isEmpty()) {
            return emptyList()
        }

        val candidateCards = mutableListOf<Card>()
        val topCard = tableCards.last()

        cardsInHand.forEach { cardInHand ->
            if (cardInHand.isCandidateCard(topCard)) {
                candidateCards.add(cardInHand)
            }
        }

        return candidateCards
    }

    override fun getCardsInHandAsString(): String {
        var cardsInHandAsString = ""
        cardsInHand.forEachIndexed { index, card ->
            cardsInHandAsString += "${card} "
        }

        return cardsInHandAsString
    }
}
