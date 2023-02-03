package indigo

enum class Suit(val value: String) {
    HEARTS("♥"),
    DIAMONDS("♦"),
    CLUBS("♣"),
    SPADES("♠"),
    UNDEFINED("U");

    companion object {
        fun fromString(value: String): Suit {
            return values().find { it.value == value } ?: UNDEFINED
        }
    }
}
