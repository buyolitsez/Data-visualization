import java.util.Scanner

data class Data(var value: Float)

enum class DiagramName {
    CIRCLE,
    NONE
}

val inputScanner = Scanner(System.`in`)
val inputData = ArrayList<Data>()
var diagramName = DiagramName.NONE

fun readData() {
    when (readLine()) {
        null -> throwError("Input is null")
        "circle" -> readDataCircle()
        else -> throwError("Unknown type of diagram")
    }
}

fun readDataCircle() {
    diagramName = DiagramName.CIRCLE
    while (inputScanner.hasNextFloat()) {
        inputData.add(Data(inputScanner.nextFloat()))
    }
    logger.info { "input data = $inputData" }
    if (inputData.isEmpty()) {
        throwError("input data is empty")
    }
}