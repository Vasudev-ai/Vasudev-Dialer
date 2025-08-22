package com.vasudev.dialer.ui.screens

import android.Manifest
import android.content.Context
import android.database.Cursor
import android.provider.CallLog
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CallMade
import androidx.compose.material.icons.filled.CallMissed
import androidx.compose.material.icons.filled.CallReceived
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.vasudev.dialer.R
import com.vasudev.dialer.data.CallLogEntry
import com.vasudev.dialer.data.CallType
import com.vasudev.dialer.ui.components.PermissionDialog
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CallLogsScreen() {
    var callLogs by remember { mutableStateOf<List<CallLogEntry>>(emptyList()) }
    var showPermissionDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current
    
    val callLogPermissionState = rememberPermissionState(Manifest.permission.READ_CALL_LOG)
    
    LaunchedEffect(callLogPermissionState.status.isGranted) {
        if (callLogPermissionState.status.isGranted) {
            callLogs = getCallLogs(context)
        }
    }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        Text(
            text = stringResource(R.string.call_logs),
            style = MaterialTheme.typography.headlineMedium.copy(
                fontWeight = FontWeight.Bold
            ),
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        
        if (callLogPermissionState.status.isGranted) {
            if (callLogs.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No call logs found",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(callLogs) { callLog ->
                        CallLogItem(callLog = callLog)
                    }
                }
            }
        } else {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Call log permission required",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    androidx.compose.material3.Button(
                        onClick = { showPermissionDialog = true }
                    ) {
                        Text("Grant Permission")
                    }
                }
            }
        }
    }
    
    if (showPermissionDialog) {
        PermissionDialog(
            title = stringResource(R.string.permission_required),
            message = stringResource(R.string.call_log_permission_message),
            onGrantPermission = {
                callLogPermissionState.launchPermissionRequest()
                showPermissionDialog = false
            },
            onDismiss = { showPermissionDialog = false }
        )
    }
}

@Composable
fun CallLogItem(callLog: CallLogEntry) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                1.dp,
                MaterialTheme.colorScheme.primary.copy(alpha = 0.3f),
                RoundedCornerShape(12.dp)
            ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Call type icon
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(
                        color = when (callLog.type) {
                            CallType.INCOMING -> MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
                            CallType.OUTGOING -> MaterialTheme.colorScheme.secondary.copy(alpha = 0.2f)
                            CallType.MISSED -> MaterialTheme.colorScheme.tertiary.copy(alpha = 0.2f)
                        },
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = when (callLog.type) {
                        CallType.INCOMING -> Icons.Default.CallReceived
                        CallType.OUTGOING -> Icons.Default.CallMade
                        CallType.MISSED -> Icons.Default.CallMissed
                    },
                    contentDescription = null,
                    tint = when (callLog.type) {
                        CallType.INCOMING -> MaterialTheme.colorScheme.primary
                        CallType.OUTGOING -> MaterialTheme.colorScheme.secondary
                        CallType.MISSED -> MaterialTheme.colorScheme.tertiary
                    },
                    modifier = Modifier.size(20.dp)
                )
            }
            
            Spacer(modifier = Modifier.width(16.dp))
            
            // Call details
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = callLog.name ?: callLog.number,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    color = MaterialTheme.colorScheme.onSurface
                )
                
                if (callLog.name != null) {
                    Text(
                        text = callLog.number,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                
                Text(
                    text = "${callLog.typeString} • ${callLog.formattedDate}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

private fun getCallLogs(context: Context): List<CallLogEntry> {
    val callLogs = mutableListOf<CallLogEntry>()
    
    try {
        val cursor: Cursor? = context.contentResolver.query(
            CallLog.Calls.CONTENT_URI,
            arrayOf(
                CallLog.Calls.NUMBER,
                CallLog.Calls.CACHED_NAME,
                CallLog.Calls.TYPE,
                CallLog.Calls.DATE,
                CallLog.Calls.DURATION
            ),
            null,
            null,
            "${CallLog.Calls.DATE} DESC LIMIT 50"
        )
        
        cursor?.use {
            val numberIndex = it.getColumnIndex(CallLog.Calls.NUMBER)
            val nameIndex = it.getColumnIndex(CallLog.Calls.CACHED_NAME)
            val typeIndex = it.getColumnIndex(CallLog.Calls.TYPE)
            val dateIndex = it.getColumnIndex(CallLog.Calls.DATE)
            val durationIndex = it.getColumnIndex(CallLog.Calls.DURATION)
            
            while (it.moveToNext()) {
                val number = it.getString(numberIndex) ?: "Unknown"
                val name = it.getString(nameIndex)
                val type = when (it.getInt(typeIndex)) {
                    CallLog.Calls.INCOMING_TYPE -> CallType.INCOMING
                    CallLog.Calls.OUTGOING_TYPE -> CallType.OUTGOING
                    CallLog.Calls.MISSED_TYPE -> CallType.MISSED
                    else -> CallType.MISSED
                }
                val date = it.getLong(dateIndex)
                val duration = it.getLong(durationIndex)
                
                callLogs.add(
                    CallLogEntry(
                        number = number,
                        name = name,
                        type = type,
                        date = date,
                        duration = duration
                    )
                )
            }
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
    
    return callLogs
}
