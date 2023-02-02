package indigo

fun main() {
    val game = Game()

    while (game.getStatus() != Status.FINISHED) {
        println("Choose an action (reset, shuffle, get, exit):")
        game.action(readln())
    }
}
