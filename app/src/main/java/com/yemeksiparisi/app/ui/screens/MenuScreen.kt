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
import com.yemeksiparisi.app.data.entity.MenuItem
import com.yemeksiparisi.app.viewmodel.AppViewModel

@Composable
fun MenuScreen(navController: NavHostController, viewModel: AppViewModel) {
    var showDialog by remember { mutableStateOf(false) }
    var itemName by remember { mutableStateOf("") }
    var itemPrice by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("Çorbalar") }
    
    val menuItems by viewModel.allMenuItems.observeAsState(emptyList())
    val categories by viewModel.allCategories.observeAsState(emptyList())
    var filteredCategory by remember { mutableStateOf<String?>(null) }

    val displayItems = if (filteredCategory != null) {
        menuItems.filter { it.category == filteredCategory }
    } else {
        menuItems
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { showDialog = true }) {
                Icon(Icons.Filled.Add, contentDescription = "Ürün Ekle")
            }
        },
        topBar = {
            TopAppBar(
                title = { Text("Menü") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Geri")
                    }
                }
            )
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            // Kategori filtreleme
            LazyColumn(modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .padding(horizontal = 8.dp)
            ) {
                items(categories) { category ->
                    FilterChip(
                        selected = filteredCategory == category,
                        onClick = {
                            filteredCategory = if (filteredCategory == category) null else category
                        },
                        label = { Text(category) },
                        modifier = Modifier.padding(horizontal = 4.dp)
                    )
                }
            }

            Divider()

            // Ürün listesi
            LazyColumn(modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
            ) {
                items(displayItems) { item ->
                    MenuItemRow(item = item, onDelete = {
                        viewModel.deleteMenuItem(item)
                    })
                }
            }
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Yeni Ürün Ekle") },
            text = {
                Column {
                    TextField(
                        value = itemName,
                        onValueChange = { itemName = it },
                        label = { Text("Ürün Adı") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    TextField(
                        value = itemPrice,
                        onValueChange = { itemPrice = it },
                        label = { Text("Fiyat") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    TextField(
                        value = selectedCategory,
                        onValueChange = { selectedCategory = it },
                        label = { Text("Kategori") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                Button(onClick = {
                    if (itemName.isNotEmpty() && itemPrice.isNotEmpty()) {
                        val price = itemPrice.toDoubleOrNull() ?: 0.0
                        viewModel.insertMenuItem(
                            MenuItem(
                                name = itemName,
                                price = price,
                                category = selectedCategory
                            )
                        )
                        itemName = ""
                        itemPrice = ""
                        selectedCategory = "Çorbalar"
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
fun MenuItemRow(item: MenuItem, onDelete: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(item.name, fontSize = 16.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                Text(item.category, fontSize = 12.sp)
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("${item.price}₺", fontSize = 16.sp)
                IconButton(onClick = onDelete) {
                    Icon(Icons.Filled.Delete, contentDescription = "Sil")
                }
            }
        }
    }
}
