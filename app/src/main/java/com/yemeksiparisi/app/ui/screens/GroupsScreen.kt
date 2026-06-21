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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.yemeksiparisi.app.data.entity.Group
import com.yemeksiparisi.app.viewmodel.AppViewModel

@Composable
fun GroupsScreen(navController: NavHostController, viewModel: AppViewModel) {
    var showDialog by remember { mutableStateOf(false) }
    var groupName by remember { mutableStateOf("") }
    val groups by viewModel.allGroups.observeAsState(emptyList())

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { showDialog = true }) {
                Icon(Icons.Filled.Add, contentDescription = "Grup Ekle")
            }
        },
        topBar = {
            TopAppBar(
                title = { Text("Gruplar") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Geri")
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            items(groups) { group ->
                GroupCard(group = group, onClick = {
                    navController.navigate("group_detail/${group.id}")
                }, onDelete = {
                    viewModel.deleteGroup(group)
                    viewModel.deleteGroupOrderItems(group.id)
                })
            }
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Yeni Grup Ekle") },
            text = {
                TextField(
                    value = groupName,
                    onValueChange = { groupName = it },
                    label = { Text("Grup Adı") },
                    modifier = Modifier.fillMaxWidth()
                )
            },
            confirmButton = {
                Button(onClick = {
                    if (groupName.isNotEmpty()) {
                        viewModel.insertGroup(Group(name = groupName))
                        groupName = ""
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
fun GroupCard(group: Group, onClick: () -> Unit, onDelete: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(group.name, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }
            Row {
                IconButton(onClick = onDelete) {
                    Icon(Icons.Filled.Delete, contentDescription = "Sil")
                }
            }
        }
    }
}
