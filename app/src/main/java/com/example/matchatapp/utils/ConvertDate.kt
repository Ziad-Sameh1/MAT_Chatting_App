package com.example.matchatapp.utils

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.matchatapp.utils.Constants.TAG
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
fun String.convertDate(): String {
    val currentDate = LocalDate.now()
    val messageDate: String = this.substring(0..9)
    var messageClock: String = this.substring(11..15)
    val messageHour: String = this.substring(11..12)
    var isSame = true
    for (i in 9 downTo 0) {
        isSame = true
        if (currentDate.toString()[i] != messageDate[i]) {
            isSame = false
            break
        }
    }
    return if (isSame) {
        // same date then show clock
        if (messageHour.toInt() <= 12) {
            messageClock += " AM"
            messageClock
        } else {
            messageClock =
                (messageHour.toInt() - 12).toString() + messageDate.substring(12..14) + " PM"
            messageClock
        }
    } else {
        messageDate
    }
}