package com.jetpack.easynotesapp.presentation.addediteasynote

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jetpack.easynotesapp.domain.action.EasyNoteUseCases
import com.jetpack.easynotesapp.domain.model.EasyNotes
import com.jetpack.easynotesapp.domain.model.InvalidNoteException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditEasyNoteViewModel @Inject constructor(
    private val noteUseCases: EasyNoteUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _noteTitle = mutableStateOf(EasyNoteTextFieldState(
        hint = "Enter title..."
    ))
    val easyNoteTitle: State<EasyNoteTextFieldState> = _noteTitle

    private val _noteContent = mutableStateOf(EasyNoteTextFieldState(
        hint = "Enter content..."
    ))
    val easyNoteContent: State<EasyNoteTextFieldState> = _noteContent

    private val _noteColor = mutableStateOf(EasyNotes.easyNotesColors.random().toArgb())
    val noteColor: State<Int> = _noteColor

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var currentNoteId: Int? = null

    init {
        savedStateHandle.get<Int>("noteId")?.let { noteId ->
            if(noteId != -1) {
                viewModelScope.launch {
                    noteUseCases.getEasyNote(noteId)?.also { note ->
                        currentNoteId = note.id
                        _noteTitle.value = easyNoteTitle.value.copy(
                            text = note.title,
                            isHintVisible = false
                        )
                        _noteContent.value = _noteContent.value.copy(
                            text = note.content,
                            isHintVisible = false
                        )
                        _noteColor.value = note.color
                    }
                }
            }
        }
    }

    fun onEvent(event: AddEditEasyNoteEvent) {
        when(event) {
            is AddEditEasyNoteEvent.EnteredTitle -> {
                _noteTitle.value = easyNoteTitle.value.copy(
                    text = event.value
                )
            }
            is AddEditEasyNoteEvent.ChangeTitleFocus -> {
                _noteTitle.value = easyNoteTitle.value.copy(
                    isHintVisible = !event.focusState.isFocused &&
                            easyNoteTitle.value.text.isBlank()
                )
            }
            is AddEditEasyNoteEvent.EnteredContent -> {
                _noteContent.value = _noteContent.value.copy(
                    text = event.value
                )
            }
            is AddEditEasyNoteEvent.ChangeContentFocus -> {
                _noteContent.value = _noteContent.value.copy(
                    isHintVisible = !event.focusState.isFocused &&
                            _noteContent.value.text.isBlank()
                )
            }
            is AddEditEasyNoteEvent.ChangeColor -> {
                _noteColor.value = event.color
            }
            is AddEditEasyNoteEvent.SaveNote -> {
                viewModelScope.launch {
                    try {
                        noteUseCases.addEasyNotes(
                            EasyNotes(
                                title = easyNoteTitle.value.text,
                                content = easyNoteContent.value.text,
                                timestamp = System.currentTimeMillis(),
                                color = noteColor.value,
                                id = currentNoteId
                            )
                        )
                        _eventFlow.emit(UiEvent.SaveNote)
                    } catch(e: InvalidNoteException) {
                        _eventFlow.emit(
                            UiEvent.ShowSnackbar(
                                message = e.message ?: "Couldn't save Easy Note"
                            )
                        )
                    }
                }
            }
        }
    }

    sealed class UiEvent {
        data class ShowSnackbar(val message: String): UiEvent()
        object SaveNote: UiEvent()
    }
}