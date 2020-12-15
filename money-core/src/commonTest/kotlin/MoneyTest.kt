import kotlinx.serialization.json.Json
import tz.co.asoft.Money
import tz.co.asoft.TZS
import tz.co.asoft.USD
import kotlin.test.Test
import kotlin.test.assertEquals

class MoneyTest {
    @Test
    fun should_store_cents() {
        assertEquals(200000, 2000.TZS.value)
    }

    @Test
    fun dollars_are_stored_the_same_way_as_tzs() {
        assertEquals(2.USD.value, 200)
        assertEquals(0.76.USD.value, 76)
        assertEquals(2.989.USD.value, 298)
    }

    @Test
    fun should_display_a_nice_string() {
        assertEquals("2,000", 2_000.TZS.toString())
        assertEquals("TZS 3,500", 3500.TZS.toFullString())
        assertEquals("USD 0.98", 0.98.USD.toFullString())
        assertEquals("TZS 1,000", (2000.TZS / 2).toFullString())
        assertEquals("USD 2", (1.USD * 2).toFullString())
        assertEquals("TZS 1", (1.TZS * 1).toFullString())
        assertEquals("TZS 1", (1.TZS / 1).toFullString())
        assertEquals("TZS 0.5", 0.5.TZS.toFullString())
        assertEquals("TZS 0.5", (1.TZS / 2).toFullString())
    }

    @Test
    fun trancation_should_work() {
        assertEquals("USD 2.98", 2.989.USD.toFullString())
    }

    @Test
    fun can_store_up_to_one_billion_tanzania_shillings() {
        val tenMil = 10_000_000.TZS
        val oneBil = 1_000_000_000.TZS
        assertEquals(tenMil.value, 1_000_000_000)
        assertEquals(oneBil.value, 100_000_000_000)
    }

    @Test
    fun can_print_up_to_one_billion_tanzania_shillings() {
        val tenMil = 10_000_000.TZS
        val oneBil = 1_000_000_000.TZS
        val hundredBill = 900_000_000_000.TZS
        assertEquals("TZS 10,000,000", tenMil.toFullString())
        assertEquals("TZS 1,000,000,000", oneBil.toFullString())
        assertEquals("TZS 900,000,000,000", hundredBill.toFullString())
    }

    @Test
    fun serializes_well() {
        val money1 = 2000.TZS
        val money2 = 0.986.USD
        val json = Json { isLenient = true }
        println(json.encodeToString(Money.serializer(), money1))
        println(json.encodeToString(Money.serializer(), money2))
        val moneyFromString = json.decodeFromString(Money.serializer(), "200000 cents @ 100 cents per TZS")
        assertEquals(money1, moneyFromString)
        assertEquals(money1.cur, moneyFromString.cur)
    }
}