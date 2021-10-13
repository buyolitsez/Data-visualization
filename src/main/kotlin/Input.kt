import java.util.*

data class Data(val value: Float, var paramName: String)

enum class DiagramName {
    CIRCLE,
    BAR,
    NONE
}

var inputData = mutableListOf<Data>()
var diagramName = DiagramName.NONE

fun readData() {
    println("Diagram types:")
    DiagramName.values().forEach { name ->
        if (name != DiagramName.NONE) {
            println(name.toString().lowercase(Locale.getDefault()))
        }
    }
    outputStringWithColor("Diagram name: ")
    when (readLine()) {
        null -> throwError("Input is null")
        "circle" -> diagramName = DiagramName.CIRCLE
        "bar" -> diagramName = DiagramName.BAR
        else -> throwError("Unknown type of diagram")
    }
    readDataArray()
}

fun readDataArray() {
    println("Write data")
    outputStringWithColor("data: ")
    var inputString = readLine()
    val maxLenOfName = MAX_NAME_LEN[diagramName]!!
    while (!inputString.isNullOrEmpty()) {
        inputData.add(
            Data(
                inputString.substringBefore(' ').toFloat(),
                inputString.substringAfter(' ')
            )
        )
        if (inputData.last().paramName.length > maxLenOfName) {
            assert(maxLenOfName >= 3)
            inputData.last().paramName = inputData.last().paramName.take(maxLenOfName - 3) + "..."
        }
        outputStringWithColor("data: ")
        inputString = readLine()
    }
    logger.info { "input data = $inputData" }
    if (inputData.isEmpty()) {
        throwError("input data is empty")
    }
    inputData.sortBy { -it.value }
    reduceInputData()
}

fun reduceInputData() {
    var othersSum = 0f
    while (inputData.size >= COUNT_MAX_VALUES || (inputData.size > 1 && inputData.first().value / inputData.last().value > MAX_RATIO)) {
        othersSum += inputData.last().value
        inputData = inputData.dropLast(1).toMutableList()
    }
    if (!isEqualsFloat(othersSum, 0f)) {
        inputData.add(Data(othersSum, "others"))
    }
}