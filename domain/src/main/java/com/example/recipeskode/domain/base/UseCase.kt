package com.example.recipeskode.domain.base


interface UseCase<T, in Params> {
    operator fun invoke(params: Params): T
}
