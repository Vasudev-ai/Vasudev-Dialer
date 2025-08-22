package com.vasudev.dialer.ui.screens

import android.Manifest
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Backspace
import androidx.compose.material.icons.filled.Call
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.vasudev.dialer.R
import com.vasudev.dialer.ui.components.GlowingButton
import com.vasudev.dialer.ui.components.PermissionDialog

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun DialerScreen() {
    var phoneNumber by remember { mutableStateOf("") }
    var showPermissionDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current
    
    val callPermissionState = rememberPermissionState(Manifest.permission.CALL_PHONE)
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Phone number display
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .background(
                    MaterialTheme.colorScheme.surface,
                    RoundedCornerShape(12.dp)
                )
                .border(
                    2.dp,
                    MaterialTheme.colorScheme.primary,
                    RoundedCornerShape(12.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = if (phoneNumber.isEmpty()) stringResource(R.string.enter_number) else phoneNumber,
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                ),
                color = if (phoneNumber.isEmpty()) 
                    MaterialTheme.colorScheme.onSurfaceVariant 
                else 
                    MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Center,
                maxLines = 1
            )
        }
        
        Spacer(modifier = Modifier.height(32.dp))
        
        // Dialpad grid
        DialpadGrid(
            onNumberClick = { digit ->
                if (phoneNumber.length < 15) {
                    phoneNumber += digit
                }
            },
            onLongPressZero = {
                phoneNumber += "+91"
            }
        )
        
        Spacer(modifier = Modifier.height(32.dp))
        
        // Action buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Delete button
            GlowingButton(
                onClick = {
                    if (phoneNumber.isNotEmpty()) {
                        phoneNumber = phoneNumber.dropLast(1)
                    }
                },
                modifier = Modifier.size(64.dp),
                shape = RoundedCornerShape(12.dp),
                glowColor = MaterialTheme.colorScheme.tertiary
            ) {
                Icon(
                    imageVector = Icons.Default.Backspace,
                    contentDescription = stringResource(R.string.delete),
                    tint = MaterialTheme.colorScheme.tertiary,
                    modifier = Modifier.size(24.dp)
                )
            }
            
            // Call button
            GlowingButton(
                onClick = {
                    if (phoneNumber.isNotEmpty()) {
                        if (callPermissionState.status.isGranted) {
                            makeCall(context, phoneNumber)
                        } else {
                            showPermissionDialog = true
                        }
                    }
                },
                modifier = Modifier.size(80.dp),
                shape = CircleShape,
                glowColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(
                    imageVector = Icons.Default.Call,
                    contentDescription = stringResource(R.string.call),
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.size(32.dp)
                )
            }
        }
    }
    
    if (showPermissionDialog) {
        PermissionDialog(
            title = stringResource(R.string.permission_required),
            message = stringResource(R.string.call_permission_message),
            onGrantPermission = {
                callPermissionState.launchPermissionRequest()
                showPermissionDialog = false
            },
            onDismiss = { showPermissionDialog = false }
        )
    }
}

@Composable
fun DialpadGrid(
    onNumberClick: (String) -> Unit,
    onLongPressZero: () -> Unit
) {
    val dialpadButtons = listOf(
        listOf("1", "2", "3"),
        listOf("4", "5", "6"),
        listOf("7", "8", "9"),
        listOf("*", "0", "#")
    )
    
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        dialpadButtons.forEach { row ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                row.forEach { digit ->
                    DialpadButton(
                        text = digit,
                        onClick = { onNumberClick(digit) },
                        onLongPress = if (digit == "0") onLongPressZero else null
                    )
                }
            }
        }
    }
}

@Composable
fun DialpadButton(
    text: String,
    onClick: () -> Unit,
    onLongPress: (() -> Unit)? = null
) {
    var isPressed by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.95f else 1f,
        animationSpec = tween(100),
        label = "button_scale"
    )
    
    Box(
        modifier = Modifier
            .size(72.dp)
            .scale(scale)
            .clip(CircleShape)
            .background(
                brush = Brush.radialGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.primary.copy(alpha = 0.3f),
                        MaterialTheme.colorScheme.surface
                    )
                )
            )
            .border(
                2.dp,
                MaterialTheme.colorScheme.primary,
                CircleShape
            )
            .clickable {
                isPressed = true
                onClick()
                isPressed = false
            },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.headlineMedium.copy(
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            ),
            color = MaterialTheme.colorScheme.primary
        )
    }
}

private fun makeCall(context: Context, phoneNumber: String) {
    val intent = Intent(Intent.ACTION_CALL).apply {
        data = Uri.parse("tel:$phoneNumber")
    }
    context.startActivity(intent)
}
