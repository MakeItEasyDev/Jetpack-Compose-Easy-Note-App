package com.jetpack.easynotesapp.domain.util

sealed class EasyNotesOrder(val orderType: OrderType) {
    class Title(orderType: OrderType): EasyNotesOrder(orderType)
    class Date(orderType: OrderType): EasyNotesOrder(orderType)
    class Color(orderType: OrderType): EasyNotesOrder(orderType)

    fun copy(orderType: OrderType): EasyNotesOrder {
        return when(this) {
            is Title -> Title(orderType)
            is Date -> Date(orderType)
            is Color -> Color(orderType)
        }
    }
}
