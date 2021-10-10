import kotlin.test.*

internal class TestDistanceSQ {

    @Test
    fun testEquals() {
        assertEquals(distanceSq(3.743f, 313f, 3.743f, 313f), 0f)
    }

    @Test
    fun testNearPoints() {
        assertEquals(distanceSq(0f, 0f, 1f, 1f), 2f)
    }

    @Test
    fun testFarPoints() {
        assertEquals(distanceSq(-1000f, -1000f, 1000f, 1000f), 8000000f)
    }
}
