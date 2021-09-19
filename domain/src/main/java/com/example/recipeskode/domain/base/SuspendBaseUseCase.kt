package com.example.recipeskode.domain.base

interface SuspendBaseUseCase<T, in Params> {

    suspend operator fun invoke(params: Params): T

}