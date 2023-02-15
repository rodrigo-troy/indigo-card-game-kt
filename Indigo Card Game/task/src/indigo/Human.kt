package indigo

/**
 * Created with IntelliJ IDEA.
$ Project: Indigo Card Game
 * User: rodrigotroy
 * Date: 06-02-23
 * Time: 17:00
 */
class Human(override val isFirst: Boolean) : Player("Player",
                                                    true,
                                                    isFirst)
