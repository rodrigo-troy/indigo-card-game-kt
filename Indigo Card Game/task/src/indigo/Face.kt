package indigo

enum class Face(val value: String,
                val points: Int = 0) {
    ACE("A",
        1),
    TWO("2",
        0),
    THREE("3",
          0),
    FOUR("4",
         0),
    FIVE("5",
         0),
    SIX("6",
        0),
    SEVEN("7",
          0),
    EIGHT("8",
          0),
    NINE("9",
         0),
    TEN("10",
        1),
    JACK("J",
         1),
    QUEEN("Q",
          1),
    KING("K",
         1),
    UNDEFINED("U",
              0);

    companion object {
        fun fromString(value: String): Face {
            return values().find { it.value == value } ?: UNDEFINED
        }
    }
}
