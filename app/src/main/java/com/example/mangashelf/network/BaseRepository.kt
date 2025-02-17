package com.example.mangashelf.network

import com.example.mangashelf.common.DataState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response

fun <T> safeApiCall(execute: suspend () -> Response<T?>): Flow<DataState<T?>> = flow {
    // do the API call on IO thread
        try {
            emit(DataState.Loading)
            val response = execute()
            if (response.isSuccessful) {
                emit(DataState.Success(response.body()))
            } else {
                emit(DataState.Error(response.errorBody()?.string() ?: "Invalid Response"))
            }

        } catch (e: Exception) {
            emit(DataState.Error(e.message ?: "Invalid Response"))
        }
}.flowOn(Dispatchers.IO).catch{
    emit(DataState.Error(it.message ?: "Invalid Response"))
}