package indigo

//print all the println in the game into a file

fun main() {
    println("Indigo Card Game".lowercase())
    println("Play first?")
    var playFirst = readln()

    while (!checkPlayFirst(playFirst)) {
        println("Play first?")
        playFirst = readln()
    }

    val game = Game(listOf(Human(playFirst == "yes"),
                           Computer(playFirst == "no")))

    println(game.getInitialCardsAsString())
    while (game.getStatus() == Status.STARTED || game.getStatus() == Status.WRONG_INPUT_NUMBER) {
        if (game.getStatus() != Status.WRONG_INPUT_NUMBER) {
            println(Game.getTableStatus(game))
        }

        if (game.currentPlayerReadOnly.isHuman) {
            if (game.getStatus() != Status.WRONG_INPUT_NUMBER) {
                println("Cards in hand: " + game.getCurrentPlayerHandAsString())
            }

            println(game.getChoosePrompt())
            game.action(readln())
        } else {
            println(game.getCurrentPlayerHandAsString())
            game.action("robotTurn")
        }
    }

    if (game.getStatus() == Status.EXITING) {
        println("Game Over")
    }
}

fun checkPlayFirst(playFirst: String): Boolean {
    return playFirst == "yes" || playFirst == "no"
}
