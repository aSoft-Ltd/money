package tz.co.asoft

import kotlinx.serialization.Serializable

@Serializable
data class Currency(
    val name: String,
    val smallestUnitMultiplier: Short,
    val smallestUnit: String
) {
    companion object {
        val TZS = Currency("TZS", 100, "cents")
        val USD = Currency("USD", 100, "cents")
    }

    override fun toString() = "$name @ $smallestUnitMultiplier $smallestUnit"
}