import kotlin.test.*

internal class TestReduceInputData {
    private var countMaxValuesOld = 0
    private var maxRatioOld = 0f

    @BeforeTest
    fun getConstants() {
        inputData.clear()
        countMaxValuesOld = COUNT_MAX_VALUES
        maxRatioOld = MAX_RATIO
    }

    @AfterTest
    fun setConstants() {
        COUNT_MAX_VALUES = countMaxValuesOld
        MAX_RATIO = maxRatioOld
    }

    @Test
    fun testEmpty() {
        inputData = mutableListOf()
        reduceInputData()
        assertContentEquals(mutableListOf(), inputData)
    }

    @Test
    fun testReduceByCount() {
        for (i in 0..10) {
            inputData.add(Data(i.toFloat(), "$i"))
        }
        inputData.sortBy { -it.value }
        COUNT_MAX_VALUES = 3
        MAX_RATIO = 100f
        reduceInputData()
        assertContentEquals(listOf(Data(10f, "10"), Data(9f, "9"), Data(36f, "others")), inputData)
    }

    @Test
    fun testReduceByRatio() {
        for (i in 0..10) {
            inputData.add(Data(i.toFloat(), "$i"))
        }
        inputData.sortBy { -it.value }
        COUNT_MAX_VALUES = 15
        MAX_RATIO = 1.4f
        reduceInputData()
        println(inputData)
        assertContentEquals(listOf(Data(10f, "10"), Data(9f, "9"), Data(8f, "8"), Data(28f, "others")), inputData)
    }
}
