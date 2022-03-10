package com.example.matchatapp.feature_chatting.domain.use_case

import com.example.matchatapp.feature_chatting.domain.model.Contact
import com.example.matchatapp.feature_chatting.domain.repository.ContactsRepo
import javax.inject.Inject

class GetContactsUseCase @Inject constructor(
    private val repo: ContactsRepo
) {
    operator fun invoke() : List<Contact> {
        return repo.getContacts()
    }
}