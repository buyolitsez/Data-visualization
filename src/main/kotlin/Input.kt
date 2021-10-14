import java.io.File
import java.util.*

data class Data(val value: Float, var paramName: String)

enum class DiagramName {
    CIRCLE,
    BAR,
    NONE
}

var inputData = mutableListOf<Data>()
var diagramName = DiagramName.NONE

fun inputCore() {
    logger.info { "Start input core" }
    val commands = listOf("load", "write")
    println("Type of commands:")
    commands.forEach { println(it) }
    outputStringWithColor("command: ")
    when (readLine()) {
        null -> throwError("Input is null")
        "load" -> loadDataFromFile()
        "write" -> readData()
    }
    reduceInputData()
}

fun loadDataFromFile() {
    logger.info { "Load data from file" }
    outputStringWithColor("file name: ")
    val fileName = readLine()?.trim()
    check(fileName != null) { throwError("Input is null") }
    setDiagramName(File(fileName).readLines()[0])
    for (str in File(fileName).readLines().drop(1)) {
        if (str.isBlank()) continue
        val value = str.substringBefore(' ').toFloatOrNull()
        val valueName = str.substringAfter(' ')
        check(value != null) { throwError("Value(${str.substringBefore(' ')}) cant be represented as float") }
        inputData.add(Data(value, valueName))
    }
}

fun readData() {
    logger.info { "load data from IO" }
    println("Diagram types:")
    DiagramName.values().forEach { name ->
        if (name != DiagramName.NONE) {
            println(name.toString().lowercase(Locale.getDefault()))
        }
    }
    outputStringWithColor("Diagram name: ")
    setDiagramName(readLine())
    readDataArray()
}

fun setDiagramName(name: String?) {
    when (name) {
        null -> throwError("Input is null")
        "circle" -> diagramName = DiagramName.CIRCLE
        "bar" -> diagramName = DiagramName.BAR
        else -> throwError("Unknown type of diagram")
    }
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
    reduceInputData()
}

fun reduceInputData() {
    logger.info { "start input data = $inputData" }
    if (inputData.isEmpty()) {
        throwError("input data is empty")
    }
    inputData.sortBy { -it.value }
    var othersSum = 0f
    while (inputData.size >= COUNT_MAX_VALUES || (inputData.size > 1 && inputData.first().value / inputData.last().value > MAX_RATIO)) {
        othersSum += inputData.last().value
        inputData = inputData.dropLast(1).toMutableList()
    }
    if (!isEqualsFloat(othersSum, 0f)) {
        inputData.add(Data(othersSum, "others"))
    }
    logger.info { "data after reduce = $inputData" }
}