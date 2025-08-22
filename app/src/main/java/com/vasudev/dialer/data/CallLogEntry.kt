package com.vasudev.dialer.data

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

enum class CallType {
    INCOMING, OUTGOING, MISSED
}

data class CallLogEntry(
    val number: String,
    val name: String?,
    val type: CallType,
    val date: Long,
    val duration: Long
) {
    val typeString: String
        get() = when (type) {
            CallType.INCOMING -> "Incoming"
            CallType.OUTGOING -> "Outgoing"
            CallType.MISSED -> "Missed"
        }
    
    val formattedDate: String
        get() {
            val dateFormat = SimpleDateFormat("MMM dd, HH:mm", Locale.getDefault())
            return dateFormat.format(Date(date))
        }
    
    val formattedDuration: String
        get() {
            val minutes = duration / 60
            val seconds = duration % 60
            return if (minutes > 0) {
                "${minutes}m ${seconds}s"
            } else {
                "${seconds}s"
            }
        }
}
