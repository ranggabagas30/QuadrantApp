package com.quadrantapp.core.base

import com.quadrantapp.core.extension.StatefulResult
import com.quadrantapp.core.extension.either
import com.quadrantapp.core.extension.toResult
import com.quadrantapp.core.model.Error
import com.quadrantapp.core.model.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector

data class FlowFailedException(val caused: Error?) : Exception()

/**
 * Use [safelyProceedFlow] when You are going to flatmap flow of StatefulResult into another flow.
 * The function is to safely flatmap our Flow of StateFulResult by handle Failed and throw exception immediately
 * Flatmap usually used for sequential process/call
 *
 * Use [safelyProceed] when You are going to map or run a process from StatefulResult into another object/entity.
 * The function is to safely process our StatefulResult by handle Failed and throw exception immediately
 *
 * Use [safelyEmit] when You are going to emit from StatefulResult with mapper (if needed).
 * The function is to safely emit an object/entity from our StatefulResult by handle Failed
 * and throw exception immediately
 *
 * Use [safelyCall] when You are going to safely call repository, then convert Result<T> into StatefulResult<T>
 */
abstract class FlowableUseCase<Param, Rezult> {

    abstract fun executeFlow(param: Param): Flow<Rezult>

    fun <I, O> StatefulResult<I>.safelyProceedFlow(process: (I) -> Flow<O>): Flow<O> {
        if (this is StatefulResult.Failed) throw FlowFailedException(error)

        return process((this as StatefulResult.Success).data)
    }

    suspend fun <I, O> StatefulResult<I>.safelyProceed(process: suspend (I) -> O): O {
        if (this is StatefulResult.Failed) throw FlowFailedException(error)

        return process((this as StatefulResult.Success).data)
    }

    suspend fun <I, O> FlowCollector<O>.safelyEmit(result: StatefulResult<I>, mapper: (I) -> O) {
        if (result.succeeded) {
            emit(mapper((result as StatefulResult.Success).data))
        } else {
            throw FlowFailedException((result as StatefulResult.Failed).error)
        }
    }

    suspend fun <I> safelyCall(block: suspend () -> Result<I>): StatefulResult<I> = either {
        block()
    }.toResult()
}
