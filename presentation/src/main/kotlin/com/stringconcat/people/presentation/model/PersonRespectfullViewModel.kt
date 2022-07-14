package com.stringconcat.people.presentation.model

import com.stringconcat.people.businessPeople.Person

const val AGE_LIMIT = 40

class PersonRespectfullViewModel(
    private val person: Person
) {

    fun title() =
        "${prefixIfNeeded()} ${person.firstName} ${person.secondName}"

    private fun prefixIfNeeded() =
        if (person.age() > AGE_LIMIT)
            when (person.sex) {
                Person.Sex.MAN -> "Mr"
                Person.Sex.WOMAN -> "Mrs"
            }
        else ""


    fun avatarUrl() = person.avatartUrl

    fun birthDate() = "${person.birthDate.dayOfMonth} ${person.birthDate.month} ${person.birthDate.year}"

    fun favoriteQuote() = person.favoriteQuote
}
