package com.vasudev.dialer.ui.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import com.vasudev.dialer.R
import com.vasudev.dialer.ui.screens.CallLogsScreen
import com.vasudev.dialer.ui.screens.DialerScreen
import com.vasudev.dialer.ui.screens.SettingsScreen

data class NavigationItem(
    val title: String,
    val icon: ImageVector,
    val screen: @Composable () -> Unit
)

@Composable
fun MainNavigation(
    modifier: Modifier = Modifier,
    onThemeToggle: () -> Unit,
    isNeonTheme: Boolean
) {
    var selectedIndex by remember { mutableIntStateOf(0) }
    
    val navigationItems = listOf(
        NavigationItem(
            title = stringResource(R.string.dialer),
            icon = Icons.Default.Call,
            screen = { DialerScreen() }
        ),
        NavigationItem(
            title = stringResource(R.string.call_logs),
            icon = Icons.Default.History,
            screen = { CallLogsScreen() }
        ),
        NavigationItem(
            title = stringResource(R.string.settings),
            icon = Icons.Default.Settings,
            screen = { 
                SettingsScreen(
                    onThemeToggle = onThemeToggle,
                    isNeonTheme = isNeonTheme
                ) 
            }
        )
    )
    
    Scaffold(
        modifier = modifier,
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.onSurface
            ) {
                navigationItems.forEachIndexed { index, item ->
                    NavigationBarItem(
                        icon = {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = item.title
                            )
                        },
                        label = {
                            Text(
                                text = item.title,
                                style = MaterialTheme.typography.labelSmall
                            )
                        },
                        selected = selectedIndex == index,
                        onClick = { selectedIndex = index },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = MaterialTheme.colorScheme.primary,
                            selectedTextColor = MaterialTheme.colorScheme.primary,
                            unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                            unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                            indicatorColor = MaterialTheme.colorScheme.primaryContainer
                        )
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            navigationItems[selectedIndex].screen()
        }
    }
}
