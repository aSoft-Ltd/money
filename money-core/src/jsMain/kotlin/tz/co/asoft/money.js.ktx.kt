package tz.co.asoft

actual fun Number.toString(places: Byte) = "${js("String")(this)}"