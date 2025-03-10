package com.example.transport_app.ui.auth

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AuthenticationScreen() {
    var selectedTabIndex by remember { mutableStateOf(0) }
    val tabs = listOf("Entrar", "Registrate")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "transport app",
            modifier = Modifier.padding(top = 32.dp, bottom = 24.dp),
            fontSize = 24.sp,
            color = Color.Black
        )

        TabRow(
            selectedTabIndex = selectedTabIndex,
            modifier = Modifier.fillMaxWidth(),
            indicator = {},
            divider = {}
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = { selectedTabIndex = index },
                    text = {
                        Text(
                            text = title,
                            fontSize = 28.sp,
                            fontWeight = if (selectedTabIndex == index) FontWeight.Bold else FontWeight.Normal,
                            color = if (selectedTabIndex == index) Color.Black else Color.Gray
                        )
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(4.dp))

        // Tab indicator
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Divider(
                modifier = Modifier
                    .weight(1f)
                    .height(3.dp)
                    .padding(end = if (selectedTabIndex == 0) 0.dp else 8.dp),
                color = if (selectedTabIndex == 0) Color(0xFF5138EE) else Color.Transparent
            )
            Divider(
                modifier = Modifier
                    .weight(1f)
                    .height(3.dp)
                    .padding(start = if (selectedTabIndex == 1) 0.dp else 8.dp),
                color = if (selectedTabIndex == 1) Color(0xFF5138EE) else Color.Transparent
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Content based on selected tab
        when (selectedTabIndex) {
            0 -> LoginScreen()
            1 -> RegisterScreen()
        }
    }
}