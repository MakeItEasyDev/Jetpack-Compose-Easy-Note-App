package com.jetpack.easynotesapp.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jetpack.easynotesapp.ui.theme.*

@Entity
data class EasyNotes(
    val title: String,
    val content: String,
    val timestamp: Long,
    val color: Int,
    @PrimaryKey val id: Int? = null
) {
    companion object {
        val easyNotesColors = listOf(LightPink, LightYellow, LightBlue, LightGreen, DarkYellow, RedYellow)
    }
}

class InvalidNoteException(message: String): Exception(message)