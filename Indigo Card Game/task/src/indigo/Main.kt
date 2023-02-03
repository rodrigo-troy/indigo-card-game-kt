package indigo

fun main() {
    val game = Game()

    println("Play first?")
    val playFirst = readln()
    game.addPlayer(Player(),
                   playFirst == "yes")
    game.addPlayer(Player(),
                   playFirst != "yes")
    game.dealCards(6)

    println(game.getInitialCardsAsString())
    while (game.getStatus() != Status.FINISHED) {
        println(game.getTableStatus())
        println(game.getCurrentPlayerHandAsString())
        println(game.getChoosePrompt())
        game.action(readln())
    }
}
