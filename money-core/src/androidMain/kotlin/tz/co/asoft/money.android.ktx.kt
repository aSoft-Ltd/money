package tz.co.asoft

actual fun Number.toString(places: Byte) = String.format("%.${places}f",this)