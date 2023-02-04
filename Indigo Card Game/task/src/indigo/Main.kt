package indigo

fun main() {
    println("Indigo Card Game")
    println("Play first?")
    var playFirst = readln()

    while (!checkPlayFirst(playFirst)) {
        println("Play first?")
        playFirst = readln()
    }

    val game = Game(listOf(Player(true,
                                  playFirst == "yes"),
                           Player(false,
                                  playFirst == "no")))

    println(game.getInitialCardsAsString())
    while (game.getStatus() == Status.STARTED || game.getStatus() == Status.WRONG_INPUT_NUMBER) {
        //println("Player ${game.currentPlayerReadOnly} turn")
        if (game.getStatus() != Status.WRONG_INPUT_NUMBER) {
            println(Game.getTableStatus(game))
        }

        if (game.currentPlayerReadOnly.isHuman) {
            if (game.getStatus() != Status.WRONG_INPUT_NUMBER) {
                println(game.getCurrentPlayerHandAsString())
            }

            println(game.getChoosePrompt())
            game.action(readln())
        } else {
            game.action("robotTurn")
        }
    }

    if (game.getStatus() == Status.FINISHED) {
        println(Game.getTableStatus(game))
    }

    println("Game Over")
}

fun checkPlayFirst(playFirst: String): Boolean {
    return playFirst == "yes" || playFirst == "no"
}
