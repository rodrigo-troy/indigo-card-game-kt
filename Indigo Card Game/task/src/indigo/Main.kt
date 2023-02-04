package indigo

fun main() {
    println("Indigo Card Game")
    println("Play first?")
    val playFirst = readln()
    val game = Game(listOf(Player(true,
                                  playFirst == "yes"),
                           Player(false,
                                  playFirst == "no")))

    println(game.getInitialCardsAsString())
    while (game.getStatus() != Status.FINISHED) {
        //println("Player ${game.currentPlayerReadOnly} turn")
        println(Game.getTableStatus(game))

        if (game.currentPlayerReadOnly.isHuman) {
            println(game.getCurrentPlayerHandAsString())
            println(game.getChoosePrompt())
            game.action(readln())
        } else {
            game.action("robotTurn")
        }
    }

    println("Game Over")
}
