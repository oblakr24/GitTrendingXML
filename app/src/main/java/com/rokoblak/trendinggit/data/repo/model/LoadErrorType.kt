package com.rokoblak.trendinggit.data.repo.model

sealed interface LoadErrorType {
    object NoNetwork: LoadErrorType
    data class ApiError(val message: String): LoadErrorType
}
