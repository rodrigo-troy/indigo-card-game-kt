package indigo

/**
 * Created with IntelliJ IDEA.
$ Project: Indigo Card Game
 * User: rodrigotroy
 * Date: 03-02-23
 * Time: 14:32
 */
data class Card(val face: Face,
                val suit: Suit) {
    override fun toString(): String {
        return "${face.value}${suit.value}"
    }
}
