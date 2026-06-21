package com.yemeksiparisi.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.yemeksiparisi.app.viewmodel.AppViewModel

@Composable
fun HomeScreen(navController: NavHostController, viewModel: AppViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "Yemek Siparişi",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        HomeMenuItem(
            icon = Icons.Filled.People,
            title = "Kişiler",
            description = "Sipariş veren kişileri yönet"
        ) {
            navController.navigate("people")
        }

        Spacer(modifier = Modifier.height(12.dp))

        HomeMenuItem(
            icon = Icons.Filled.Restaurant,
            title = "Menü",
            description = "Ürün ve kategorileri yönet"
        ) {
            navController.navigate("menu")
        }

        Spacer(modifier = Modifier.height(12.dp))

        HomeMenuItem(
            icon = Icons.Filled.Groups,
            title = "Gruplar",
            description = "Sipariş gruplarını oluştur ve yönet"
        ) {
            navController.navigate("groups")
        }
    }
}

@Composable
fun HomeMenuItem(
    icon: androidx.compose.material.icons.materialIcon,
    title: String,
    description: String,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(vertical = 8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                icon,
                contentDescription = title,
                modifier = Modifier
                    .size(48.dp)
                    .padding(end = 16.dp)
            )
            Column {
                Text(title, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Text(description, fontSize = 12.sp)
            }
        }
    }
}

// Temporary fix for icon type issue
typealias materialIcon = androidx.compose.ui.graphics.vector.ImageVector
