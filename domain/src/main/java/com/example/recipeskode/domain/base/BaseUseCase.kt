package com.example.recipeskode.domain.base


interface BaseUseCase<T, in Params> {
    operator fun invoke(params: Params): T
}
