package com.jetpack.easynotesapp.presentation.notes

import android.content.Context
import android.content.Intent
import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Sort
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.jetpack.easynotesapp.R
import com.jetpack.easynotesapp.presentation.notes.components.EasyNoteItem
import com.jetpack.easynotesapp.presentation.notes.components.OrderSection
import com.jetpack.easynotesapp.presentation.notes.components.StaggeredVerticalGrid
import com.jetpack.easynotesapp.presentation.util.Screen
import com.jetpack.easynotesapp.ui.theme.ActionBarColor
import com.jetpack.easynotesapp.ui.theme.BackgroundColor
import kotlinx.coroutines.launch

@ExperimentalFoundationApi
@ExperimentalMaterialApi
@ExperimentalAnimationApi
@Composable
fun NotesScreenScreen(
    navController: NavController,
    viewModelEasy: EasyNotesViewModel = hiltViewModel()
) {
    val state = viewModelEasy.stateEasy.value
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    Scaffold(
        topBar = {
             TopAppBar(
                 title = {
                     Text(
                         text = "Easy Notes",
                         fontFamily = FontFamily(Font(R.font.poppins_bold))
                     )
                 },
                 actions = {
                     IconButton(
                         onClick = {
                             viewModelEasy.onEvent(EasyNotesEvent.ToggleOrderSection)
                         },
                     ) {
                         Icon(
                             imageVector = Icons.Default.Sort,
                             contentDescription = "Sort"
                         )
                     }
                 },
                 navigationIcon = {
                     IconButton(onClick = { }) {
                         Icon(
                             imageVector = Icons.Default.Menu,
                             contentDescription = "Menu"
                         )
                     }
                 },
                 backgroundColor = ActionBarColor
             )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(Screen.AddEditEasyNoteScreen.route)
                },
                backgroundColor = ActionBarColor
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add note")
            }
        },
        scaffoldState = scaffoldState
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(BackgroundColor)
                .padding(5.dp)
        ) {
            AnimatedVisibility(
                visible = state.isOrderSectionVisible,
                enter = fadeIn() + slideInVertically(),
                exit = fadeOut() + slideOutVertically()
            ) {
                OrderSection(
                    noteOrder = state.noteOrder,
                    onOrderChange = {
                        viewModelEasy.onEvent(EasyNotesEvent.Order(it))
                    }
                )
            }
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
            ) {
                StaggeredVerticalGrid(
                    numColumns = 2,
                    modifier = Modifier.padding(5.dp)
                ) {
                    state.notes.forEachIndexed { _, note ->
                        EasyNoteItem(
                            note = note,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    navController.navigate(
                                        Screen.AddEditEasyNoteScreen.route +
                                                "?noteId=${note.id}&noteColor=${note.color}"
                                    )
                                },
                            onDeleteClick = {
                                viewModelEasy.onEvent(EasyNotesEvent.DeleteNote(note))
                                scope.launch {
                                    val result = scaffoldState.snackbarHostState.showSnackbar(
                                        message = "Easy Note deleted",
                                        actionLabel = "Undo"
                                    )
                                    if (result == SnackbarResult.ActionPerformed) {
                                        viewModelEasy.onEvent(EasyNotesEvent.RestoreNote)
                                    }
                                }
                            },
                            shareClick = {
                                shareContent(note.content, context)
                            }
                        )
                    }
                }
            }
        }
    }
}

private fun shareContent(content: String, context: Context) {
    val shareIntent = Intent()
    shareIntent.action = Intent.ACTION_SEND
    shareIntent.type= "text/plain"
    shareIntent.putExtra(Intent.EXTRA_TEXT, content);
    context.startActivity(Intent.createChooser(shareIntent, "Share with:"))
}