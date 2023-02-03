package indigo

enum class Face(val value: String) {
    ACE("A"),
    TWO("2"),
    THREE("3"),
    FOUR("4"),
    FIVE("5"),
    SIX("6"),
    SEVEN("7"),
    EIGHT("8"),
    NINE("9"),
    TEN("10"),
    JACK("J"),
    QUEEN("Q"),
    KING("K");

    companion object {
        fun fromString(value: String): Face? {
            return values().find { it.value == value }
        }
    }
}
