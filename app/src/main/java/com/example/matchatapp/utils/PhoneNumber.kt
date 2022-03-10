package com.example.matchatapp.utils

fun String.removeExtraZeroPhone(): String {
    var editedString: String = ""
    for (i in this.indices) {
        if (i == 3) {
            continue
        } else {
            editedString += this[i]
        }
    }
    return editedString
}