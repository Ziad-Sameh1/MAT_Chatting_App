package com.example.matchatapp.feature_chatting.domain.repository

import com.example.matchatapp.feature_chatting.domain.model.Contact

interface ContactsRepo {
    fun getContacts() : List<Contact>
}