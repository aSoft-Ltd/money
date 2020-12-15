package tz.co.asoft

operator fun Number.times(m: Money) = m * this

inline val Number.TZS get() = Money(toDouble(), Currency.TZS)
inline val Number.USD get() = Money(toDouble(), Currency.USD)

expect fun Number.toString(places: Byte): String