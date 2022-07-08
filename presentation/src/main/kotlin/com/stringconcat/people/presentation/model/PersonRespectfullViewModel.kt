package com.stringconcat.people.presentation.model

import com.stringconcat.people.businessPeople.Person

class PersonRespectfullViewModel(
    private val person: Person
) {
    private val ageLimit = 40

    fun title() =
        "${prefixIfNeeded()} ${person.firstName} ${person.secondName}"

    private fun prefixIfNeeded() =
        if (person.age() > ageLimit)
            when (person.sex) {
                Person.Sex.MAN -> "Mr"
                Person.Sex.WOMAN -> "Mrs"
            }
        else ""


    fun avatarUrl() = person.avatartUrl

    fun birthDate() = "${person.birthDate.dayOfMonth} ${person.birthDate.month} ${person.birthDate.year}"

    fun favoriteQuote() = person.favoriteQuote
}
