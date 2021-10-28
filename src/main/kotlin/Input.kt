import java.io.File
import java.util.*

data class DiagramData(val value: Float, var paramName: String)

enum class DiagramName {
    CIRCLE,
    BAR,
    NONE
}

var diagramName = DiagramName.NONE

fun inputCore(): List<DiagramData> {
    logger.info { "Start input core" }
    val commands = listOf("load", "write")
    println("Type of commands:")
    commands.forEach { println(it) }
    outputStringWithColor("command: ")
    val inputData = when (readLine()) {
        "load" -> loadDataFromFile()
        "write" -> readData()
        else -> listOf()
    }
    return reduceInputData(inputData)
}

fun loadDataFromFile() : List<DiagramData> {
    logger.info { "Load data from file" }
    outputStringWithColor("file name: ")
    val fileName = readLine()?.trim()
    check(fileName != null) { throwError("Input is null") }
    check(File(fileName).exists()) { throwError("File doesn't exist") }
    setDiagramName(File(fileName).readLines()[0])
    val inputData = mutableListOf<DiagramData>()
    for (str in File(fileName).readLines().drop(1)) {
        if (str.isBlank()) continue
        val value = str.substringBefore(' ').toFloatOrNull()
        val valueName = str.substringAfter(' ')
        check(value != null) { throwError("Value(${str.substringBefore(' ')}) cant be represented as float") }
        inputData.add(DiagramData(value, valueName))
    }
    return inputData
}

fun readData() : List<DiagramData> {
    logger.info { "load data from IO" }
    println("Diagram types:")
    DiagramName.values().forEach { name ->
        if (name != DiagramName.NONE) {
            println(name.toString().lowercase(Locale.getDefault()))
        }
    }
    outputStringWithColor("Diagram name: ")
    setDiagramName(readLine())
    return readDataArray()
}

fun setDiagramName(name: String?) {
    when (name) {
        null -> throwError("Input is null")
        "circle" -> diagramName = DiagramName.CIRCLE
        "bar" -> diagramName = DiagramName.BAR
        else -> throwError("Unknown type of diagram")
    }
}

fun readDataArray(): List<DiagramData> {
    println("Write data")
    outputStringWithColor("data: ")
    var inputString = readLine()
    val maxLenOfName = MAX_NAME_LEN[diagramName]!!
    val inputData = mutableListOf<DiagramData>()
    while (!inputString.isNullOrEmpty()) {
        inputData.add(
            DiagramData(
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
    return inputData
}

fun reduceInputData(inputData: List<DiagramData>): List<DiagramData> {
    var reducedInputData = inputData.toMutableList()
    logger.info { "start input data = $reducedInputData" }
    if (reducedInputData.isEmpty()) {
        throwError("input data is empty")
    }
    reducedInputData.sortBy { -it.value }
    var othersSum = 0f
    while (reducedInputData.size >= COUNT_MAX_VALUES || (reducedInputData.size > 1 && reducedInputData.first().value / reducedInputData.last().value > MAX_RATIO)) {
        othersSum += reducedInputData.last().value
        reducedInputData = reducedInputData.dropLast(1).toMutableList()
    }
    if (!isEqualsFloat(othersSum, 0f)) {
        reducedInputData.add(DiagramData(othersSum, "others"))
    }
    logger.info { "data after reduce = $reducedInputData" }
    return reducedInputData
}