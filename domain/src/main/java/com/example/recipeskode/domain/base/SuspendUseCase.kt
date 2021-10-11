package com.example.recipeskode.domain.base

interface SuspendUseCase<T, in Params> {
    suspend operator fun invoke(params: Params): T
}