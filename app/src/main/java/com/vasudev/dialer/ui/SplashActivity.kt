package com.vasudev.dialer.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vasudev.dialer.MainActivity
import com.vasudev.dialer.R
import com.vasudev.dialer.ui.theme.VasudevDialerTheme
import kotlinx.coroutines.delay

class SplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        setContent {
            VasudevDialerTheme {
                SplashScreen {
                    startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                    finish()
                }
            }
        }
    }
}

@Composable
fun SplashScreen(onSplashComplete: () -> Unit) {
    var currentStep by remember { mutableIntStateOf(0) }
    val alpha = remember { Animatable(0f) }
    
    val splashMessages = listOf(
        stringResource(R.string.initializing),
        stringResource(R.string.loading_ui),
        stringResource(R.string.connecting),
        stringResource(R.string.ready)
    )
    
    LaunchedEffect(Unit) {
        // Animate each message
        for (i in splashMessages.indices) {
            alpha.animateTo(1f, animationSpec = tween(300))
            currentStep = i
            delay(800)
            if (i < splashMessages.size - 1) {
                alpha.animateTo(0f, animationSpec = tween(200))
                delay(200)
            }
        }
        
        // Wait a bit on the final message then proceed
        delay(1000)
        onSplashComplete()
    }
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // App title
            Text(
                text = "VASUDEV DIALER",
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 2.sp
                ),
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(bottom = 48.dp)
            )
            
            // Loading messages
            Text(
                text = if (currentStep < splashMessages.size) splashMessages[currentStep] else "",
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontSize = 16.sp
                ),
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.alpha(alpha.value)
            )
            
            // Progress indicator
            if (currentStep < splashMessages.size - 1) {
                Text(
                    text = "█".repeat(currentStep + 1) + "░".repeat(splashMessages.size - currentStep - 1),
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontSize = 14.sp,
                        letterSpacing = 2.sp
                    ),
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(top = 24.dp)
                )
            }
        }
    }
}
