import mu.KotlinLogging

val logger = KotlinLogging.logger { }

fun main() {
    logger.info { "Start main" }
    inputCore()
    createWindow("pf-2021-viz")
    logger.info { "End main" }
}
