package indigo

//print all the println in the game into a file

import java.io.File

fun main() {
    val file = File("output.txt")
    file.writeText("Indigo Card Game\n")

    println("Indigo Card Game".lowercase())
    println("Play first?")
    var playFirst = readln()

    while (!checkPlayFirst(playFirst)) {
        println("Play first?")
        playFirst = readln()
    }

    val game = Game(listOf(Player(true,
                                  playFirst == "yes"),
                           Player(false,
                                  playFirst == "no")),
                    file)

    println(game.getInitialCardsAsString())
    while (game.getStatus() == Status.STARTED || game.getStatus() == Status.WRONG_INPUT_NUMBER) {
        file.appendText(game.getInitialCardsAsString()
                            .removePrefix("Initial") + "\n")
        if (game.getStatus() != Status.WRONG_INPUT_NUMBER) {
            file.appendText(Game.getTableStatus(game) + "\n")
            println(Game.getTableStatus(game))
        }

        if (game.currentPlayerReadOnly.isHuman) {
            if (game.getStatus() != Status.WRONG_INPUT_NUMBER) {
                file.appendText(game.getCurrentPlayerHandAsString() + "\n")
                println(game.getCurrentPlayerHandAsString())
            }

            println(game.getChoosePrompt())
            game.action(readln())
        } else {
            game.action("robotTurn")
        }
    }

    file.appendText("Game Over")

    if (game.getStatus() == Status.EXITING) {
        println("Game Over")
    }
}

fun checkPlayFirst(playFirst: String): Boolean {
    return playFirst == "yes" || playFirst == "no"
}
