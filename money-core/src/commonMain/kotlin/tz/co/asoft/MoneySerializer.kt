package tz.co.asoft

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

internal object MoneySerializer : KSerializer<Money> {
    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("money")

    @OptIn(ExperimentalStdlibApi::class)
    override fun deserialize(decoder: Decoder): Money {
        val splits = buildList { repeat(7) { add(decoder.decodeString()) } }
        return Money(
            value = splits[0].toLong(),
            cur = Currency(
                name = splits[6].toUpperCase(),
                smallestUnitMultiplier = splits[3].toShort(),
                smallestUnit = splits[1]
            )
        )
    }

    override fun serialize(encoder: Encoder, value: Money) {
        val cur = value.cur
        val unit = cur.smallestUnit
        val curName = cur.name
        encoder.encodeString("${value.value} $unit @ ${cur.smallestUnitMultiplier} $unit per $curName")
    }
}