package com.example.notesapp
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.saveable.rememberSaveable
import com.example.notesapp.Note
import com.example.notesapp.NotesViewModel


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(color = MaterialTheme.colorScheme.background)
                {
                    NotesScreen()
                }
            }
        }
    }
}

@Composable
fun NotesScreen(vm: NotesViewModel = viewModel()) {
    val notes by vm.notes.collectAsState()
    var input by rememberSaveable { mutableStateOf("") }
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(
            top = 100.dp,
            start = 16.dp,
            end = 16.dp,
            bottom = 16.dp
        )) {
        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = input,
                    onValueChange = { input = it },
                    modifier = Modifier.weight(1f),
                    label = { Text("Catatan baru") }
                )
                Button(onClick = { vm.addNote(input); input = "" }) {
                    Text("Tambah")
                }
            }
        Spacer(Modifier.height(12.dp))
        LazyColumn {
            items(notes, key = { it.id }) { note ->
                NoteRowReadOnly(note = note)
                Divider()
            }
        }
    }
}


@Composable
fun NoteRowReadOnly(note: Note) {
    ListItem(
        headlineContent = { Text(note.title) }
    )
}