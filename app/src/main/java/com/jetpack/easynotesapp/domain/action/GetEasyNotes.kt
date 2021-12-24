package com.jetpack.easynotesapp.domain.action

import com.jetpack.easynotesapp.domain.model.EasyNotes
import com.jetpack.easynotesapp.domain.repository.EasyNotesRepository
import com.jetpack.easynotesapp.domain.util.EasyNotesOrder
import com.jetpack.easynotesapp.domain.util.OrderType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetEasyNotes(
    private val repository: EasyNotesRepository
) {

    operator fun invoke(
        noteOrder: EasyNotesOrder = EasyNotesOrder.Date(OrderType.Descending)
    ): Flow<List<EasyNotes>> {
        return repository.getEasyNotes().map { notes ->
            when(noteOrder.orderType) {
                is OrderType.Ascending -> {
                    when(noteOrder) {
                        is EasyNotesOrder.Title -> notes.sortedBy { it.title.lowercase() }
                        is EasyNotesOrder.Date -> notes.sortedBy { it.timestamp }
                        is EasyNotesOrder.Color -> notes.sortedBy { it.color }
                    }
                }
                is OrderType.Descending -> {
                    when(noteOrder) {
                        is EasyNotesOrder.Title -> notes.sortedByDescending { it.title.lowercase() }
                        is EasyNotesOrder.Date -> notes.sortedByDescending { it.timestamp }
                        is EasyNotesOrder.Color -> notes.sortedByDescending { it.color }
                    }
                }
            }
        }
    }
}