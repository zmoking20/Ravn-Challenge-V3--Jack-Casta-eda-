package com.jack.ravn_challenge.domain.usecase

import com.jack.ravn_challenge.data.PeopleRepository
import com.jack.ravn_challenge.data.model.PersonModel
import javax.inject.Inject

class GetPersonUseCase @Inject constructor(
    private val repository: PeopleRepository
) {

    suspend operator fun invoke(id:String):PersonModel{
        return repository.getPersonById(id)
    }

}