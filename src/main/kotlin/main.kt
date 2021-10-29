import mu.KotlinLogging

val logger = KotlinLogging.logger { }

fun main() {
    logger.info { "Start main" }
    val inputData = inputCore()
    if (diagramName == DiagramName.SCATTER) {
        checkInputParamNamesOnlyNumbers(inputData)
    }
    createWindow("pf-2021-viz", inputData)
    logger.info { "End main" }
}
