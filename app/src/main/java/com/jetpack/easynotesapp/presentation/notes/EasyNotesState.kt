package com.jetpack.easynotesapp.presentation.notes

import com.jetpack.easynotesapp.domain.model.EasyNotes
import com.jetpack.easynotesapp.domain.util.EasyNotesOrder
import com.jetpack.easynotesapp.domain.util.OrderType

data class EasyNotesState(
    val notes: List<EasyNotes> = emptyList(),
    val noteOrder: EasyNotesOrder = EasyNotesOrder.Date(OrderType.Descending),
    val isOrderSectionVisible: Boolean = false
)
