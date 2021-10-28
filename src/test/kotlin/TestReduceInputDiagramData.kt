import kotlin.test.*

internal class TestReduceInputDiagramData {
    private var countMaxValuesOld = 0
    private var maxRatioOld = 0f

    @BeforeTest
    fun getConstants() {
        countMaxValuesOld = COUNT_MAX_VALUES
        maxRatioOld = MAX_RATIO
    }

    @AfterTest
    fun setConstants() {
        COUNT_MAX_VALUES = countMaxValuesOld
        MAX_RATIO = maxRatioOld
    }

    @Test
    fun testReduceByCount() {
        val inputData = mutableListOf<DiagramData>()
        for (i in 0..10) {
            inputData.add(DiagramData(i.toFloat(), "$i"))
        }
        inputData.sortBy { -it.value }
        COUNT_MAX_VALUES = 3
        MAX_RATIO = 100f
        assertContentEquals(
            listOf(DiagramData(10f, "10"), DiagramData(9f, "9"), DiagramData(36f, "others")),
            reduceInputData(inputData)
        )
    }

    @Test
    fun testReduceByRatio() {
        val inputData = mutableListOf<DiagramData>()
        for (i in 0..10) {
            inputData.add(DiagramData(i.toFloat(), "$i"))
        }
        inputData.sortBy { -it.value }
        COUNT_MAX_VALUES = 15
        MAX_RATIO = 1.4f
        println(inputData)
        assertContentEquals(
            listOf(
                DiagramData(10f, "10"),
                DiagramData(9f, "9"),
                DiagramData(8f, "8"),
                DiagramData(28f, "others")
            ), reduceInputData(inputData)
        )
    }
}
