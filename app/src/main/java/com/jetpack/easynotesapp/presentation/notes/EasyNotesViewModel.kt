package com.jetpack.easynotesapp.presentation.notes

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jetpack.easynotesapp.domain.action.EasyNoteUseCases
import com.jetpack.easynotesapp.domain.model.EasyNotes
import com.jetpack.easynotesapp.domain.util.EasyNotesOrder
import com.jetpack.easynotesapp.domain.util.OrderType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EasyNotesViewModel @Inject constructor(
    private val noteUseCases: EasyNoteUseCases
) : ViewModel() {

    private val _state = mutableStateOf(EasyNotesState())
    val stateEasy: State<EasyNotesState> = _state

    private var recentlyDeletedNote: EasyNotes? = null

    private var getNotesJob: Job? = null

    init {
        getNotes(EasyNotesOrder.Date(OrderType.Descending))
    }

    fun onEvent(eventEasy: EasyNotesEvent) {
        when (eventEasy) {
            is EasyNotesEvent.Order -> {
                if (stateEasy.value.noteOrder::class == eventEasy.noteOrder::class &&
                    stateEasy.value.noteOrder.orderType == eventEasy.noteOrder.orderType
                ) {
                    return
                }
                getNotes(eventEasy.noteOrder)
            }
            is EasyNotesEvent.DeleteNote -> {
                viewModelScope.launch {
                    noteUseCases.deleteEasyNotes(eventEasy.note)
                    recentlyDeletedNote = eventEasy.note
                }
            }
            is EasyNotesEvent.RestoreNote -> {
                viewModelScope.launch {
                    noteUseCases.addEasyNotes(recentlyDeletedNote ?: return@launch)
                    recentlyDeletedNote = null
                }
            }
            is EasyNotesEvent.ToggleOrderSection -> {
                _state.value = stateEasy.value.copy(
                    isOrderSectionVisible = !stateEasy.value.isOrderSectionVisible
                )
            }
        }
    }

    private fun getNotes(noteOrder: EasyNotesOrder) {
        getNotesJob?.cancel()
        getNotesJob = noteUseCases.getEasyNotes(noteOrder)
            .onEach { notes ->
                _state.value = stateEasy.value.copy(
                    notes = notes,
                    noteOrder = noteOrder
                )
            }
            .launchIn(viewModelScope)
    }
}