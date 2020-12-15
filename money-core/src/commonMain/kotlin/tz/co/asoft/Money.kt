package tz.co.asoft

import kotlinx.serialization.Serializable
import kotlin.math.floor

/**
 * @param value should be in cents
 */
@Serializable(with = MoneySerializer::class)
data class Money internal constructor(val value: Long, val cur: Currency) : Comparable<Money> {
    constructor(value: Number, cur: Currency) : this((value.toDouble() * cur.smallestUnitMultiplier).toLong(), cur)

    init {
        require(cur.smallestUnitMultiplier > 0) {
            "small unit multiplier for ${cur.name} must be a non zero value"
        }
    }

    operator fun times(n: Number) = Money((n.toDouble() * value).toLong(), cur)

    operator fun div(n: Number) = this * (1 / n.toDouble())

    operator fun plus(m: Money): Money {
        if (m.cur != cur) throw Exception("Can't add between two currencies ${m.cur.name}/${cur.name}")
        return Money(m.value + value, cur)
    }

    operator fun minus(m: Money): Money {
        if (m.cur != cur) throw Exception("Can't add between two currencies ${m.cur.name}/${cur.name}")
        return Money(value - m.value, cur)
    }

    override operator fun compareTo(other: Money): Int {
        if (other.cur != cur) {
            throw RuntimeException("Can't compare different currencies")
        }
        return value.compareTo(other.value)
    }

    fun toFullString(): String = "${cur.name} $this"

    override fun toString(): String {
        var out = ""
        val amount = floor(value.toDouble() * 100) / (100 * cur.smallestUnitMultiplier)
        val splits = amount.toString(places = 2).split(".")
        val characteristic = splits[0]
        var mantisa = splits.elementAtOrElse(1) { "0" }
        while (mantisa.length > 1 && mantisa.endsWith("0")) {
            mantisa = mantisa.substringBeforeLast("0")
        }
        var counts = 0
        for (i in characteristic.length - 1 downTo 0) {
            counts++
            out = characteristic[i] + out
            if (counts == 3 && i != 0) {
                out = ",$out"
                counts = 0
            }
        }

        val mantisaValue = if (mantisa.length > 2) mantisa.take(2) else mantisa
        if (mantisaValue != "0") {
            out += ".$mantisaValue"
        }
        return out
    }
}