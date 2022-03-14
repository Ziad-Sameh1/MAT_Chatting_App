package com.example.matchatapp.feature_chatting.data.repository

import android.annotation.SuppressLint
import android.content.Context
import android.provider.ContactsContract
import android.util.Log
import com.example.matchatapp.feature_chatting.domain.model.Contact
import com.example.matchatapp.feature_chatting.domain.repository.ContactsRepo
import com.example.matchatapp.utils.Constants.TAG
import javax.inject.Inject

class ContactsRepoImpl @Inject constructor(
    private val context: Context
) : ContactsRepo {
    @SuppressLint("Range")
    override fun getContacts(): List<Contact> {
        val list = mutableListOf<Contact>()
        val cursor = context.contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            null,
            null,
            null,
            null
        )
        if (cursor?.count ?: 0 > 0) {
            while (cursor?.moveToNext() == true) {
                val name =
                    cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
                val phone =
                    cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                val contact = Contact(name = name, phoneNumber = phone)
                list.add(contact)
            }
        }
        cursor?.close()
        return list
    }
}