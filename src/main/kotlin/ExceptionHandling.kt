import kotlin.system.exitProcess

fun throwError(error: String) {
    logger.info { "exit process cause of $error" }
    System.err.println(error)
    exitProcess(-1)
}