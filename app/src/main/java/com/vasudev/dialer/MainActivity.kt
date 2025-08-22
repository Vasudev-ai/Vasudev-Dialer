package com.vasudev.dialer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import com.vasudev.dialer.ui.navigation.MainNavigation
import com.vasudev.dialer.ui.theme.VasudevDialerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        
        // Make status bar and navigation bar transparent
        WindowCompat.setDecorFitsSystemWindows(window, false)
        
        setContent {
            var isNeonTheme by remember { mutableStateOf(true) }
            
            VasudevDialerTheme(isNeonTheme = isNeonTheme) {
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    MainNavigation(
                        modifier = Modifier.padding(innerPadding),
                        onThemeToggle = { isNeonTheme = !isNeonTheme },
                        isNeonTheme = isNeonTheme
                    )
                }
            }
        }
    }
}
