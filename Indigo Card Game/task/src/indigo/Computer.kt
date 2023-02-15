package indigo

import java.io.File

/**
 * Created with IntelliJ IDEA.
$ Project: Indigo Card Game
 * User: rodrigotroy
 * Date: 06-02-23
 * Time: 16:52
 */

class Computer(override val isFirst: Boolean,
               private val file: File) : Player("Computer",
                                                false,
                                                isFirst) {

    fun throwCard(tableCards: List<Card>): Card {
        file.appendText("Computer's turn: Computer's cards: ${this.getCardsInHandAsString()}\n")
        file.appendText("Computer's turn: Candidate cards: ${
            this.getCandidateCards(tableCards).joinToString { it.toString() }
        }\n")


        if (this.cardsInHand.size == 1) {
            file.appendText("Only one card left in hand, playing ${this.cardsInHand.first()}\n")
            return this.cardsInHand.removeAt(0)
        }

        val cardsGroupedBySuit: Map<Suit, List<Card>> = cardsInHand.groupBy { it.suit }
        val cardsGroupedByFace: Map<Face, List<Card>> = cardsInHand.groupBy { it.face }

        if (tableCards.isEmpty() || this.candidateCardsCount(tableCards) == 0) {
            file.appendText("No cards on the table\n")

            if (cardsGroupedBySuit.size == 1) {
                file.appendText("Only one suit in hand, playing ${this.cardsInHand.first()}\n")
                return this.cardsInHand.removeAt(0)
            }

            if (!this.allGroupHaveTheSameSize(cardsGroupedBySuit)) {
                file.appendText("There are cards in hand with the same suit\n")

                val biggestGroupBySuit = cardsGroupedBySuit.maxOf { it.value.size }
                file.appendText("Biggest group by Suit: $biggestGroupBySuit\n")

                val card = biggestGroupBySuit.let { max ->
                    val filter = cardsGroupedBySuit.filter { it.value.size == max }

                    filter.maxOf { it.value.first().face }.let { maxFace ->
                        filter.filter { it.value.first().face == maxFace }.values.first().first()
                    }
                }

                return this.cardsInHand.removeAt(this.cardsInHand.indexOf(card))
            }

            if (this.allGroupHaveTheSameSize(cardsGroupedBySuit) && !this.allGroupHaveTheSameSize(cardsGroupedByFace)) {
                file.appendText("There are no cards in hand with the same suit, but there are cards with the same rank\n")

                val biggestGroupByFace = cardsGroupedByFace.maxOf { it.value.size }
                file.appendText("Biggest group by Face: $biggestGroupByFace\n")

                val card = biggestGroupByFace.let { max ->
                    val filter = cardsGroupedByFace.filter { it.value.size == max }

                    filter.maxOf { it.value.first().face }.let { maxFace ->
                        filter.filter { it.value.first().face == maxFace }.values.first().first()
                    }
                }

                return this.cardsInHand.removeAt(this.cardsInHand.indexOf(card))
            }

            this.cardsInHand.removeAt(0)
        }

        if (this.candidateCardsCount(tableCards) == 1) {
            val card = getCandidateCards(tableCards).first()
            file.appendText("Only one candidate card, playing ${card}\n")
            return this.cardsInHand.removeAt(this.cardsInHand.indexOf(card))
        }

        val candidateCardsGroupedBySuit: Map<Suit, List<Card>> = getCandidateCards(tableCards).groupBy { it.suit }
        val candidateCardsGroupedByFace: Map<Face, List<Card>> = getCandidateCards(tableCards).groupBy { it.face }

        if (!this.allGroupHaveTheSameSize(candidateCardsGroupedBySuit)) {
            file.appendText("There are candidate cards with the same suit\n")

            val biggestGroupBySuit = candidateCardsGroupedBySuit.maxOf { it.value.size }
            file.appendText("Biggest group by Suit: $biggestGroupBySuit\n")

            val card = biggestGroupBySuit.let { max ->
                val filter = candidateCardsGroupedBySuit.filter { it.value.size == max }

                filter.maxOf { it.value.first().face }.let { maxFace ->
                    filter.filter { it.value.first().face == maxFace }.values.first().first()
                }
            }

            return this.cardsInHand.removeAt(this.cardsInHand.indexOf(card))
        }

        if (this.allGroupHaveTheSameSize(candidateCardsGroupedBySuit) && !this.allGroupHaveTheSameSize(candidateCardsGroupedByFace)) {
            file.appendText("There are no candidate cards with the same suit, but there are candidate cards with the same rank\n")

            val biggestGroupByFace = candidateCardsGroupedByFace.maxOf { it.value.size }
            file.appendText("Biggest group by Face: $biggestGroupByFace\n")

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
