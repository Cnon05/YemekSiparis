package com.yemeksiparisi.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.yemeksiparisi.app.data.entity.Person
import com.yemeksiparisi.app.viewmodel.AppViewModel

@Composable
fun PeopleScreen(navController: NavHostController, viewModel: AppViewModel) {
    var showDialog by remember { mutableStateOf(false) }
    var personName by remember { mutableStateOf("") }
    val persons by viewModel.allPersons.observeAsState(emptyList())

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { showDialog = true }) {
                Icon(Icons.Filled.Add, contentDescription = "Kişi Ekle")
            }
        },
        topBar = {
            TopAppBar(
                title = { Text("Kişiler") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Geri")
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(modifier = Modifier.padding(padding)) {
            items(persons) { person ->
                PersonItem(person = person, onDelete = {
                    viewModel.deletePerson(person)
                })
            }
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Yeni Kişi Ekle") },
            text = {
                TextField(
                    value = personName,
                    onValueChange = { personName = it },
                    label = { Text("Kişi Adı") }
                )
            },
            confirmButton = {
                Button(onClick = {
                    if (personName.isNotEmpty()) {
                        viewModel.insertPerson(Person(name = personName))
                        personName = ""
                        showDialog = false
                    }
                }) {
                    Text("Ekle")
                }
            },
            dismissButton = {
                Button(onClick = { showDialog = false }) {
                    Text("İptal")
                }
            }
        )
    }
}

@Composable
fun PersonItem(person: Person, onDelete: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(person.name, fontSize = 16.sp)
            IconButton(onClick = onDelete) {
                Icon(Icons.Filled.Delete, contentDescription = "Sil")
            }
        }
    }
}
