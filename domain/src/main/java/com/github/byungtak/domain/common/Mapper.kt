package com.github.byungtak.domain.common

import com.github.byungtak.domain.entities.Optional
import io.reactivex.Observable



abstract class Mapper<in E, T> {
    abstract fun mapFrom(from: E): T

    fun mapOptional(from: Optional<E>): Optional<T> {
        from.value?.let {
            return Optional.of(mapFrom(it))
        } ?: return Optional.empty()
    }

    fun observable(from: E): Observable<T> = Observable.fromCallable { mapFrom(from) }

    fun observable(from: List<E>): Observable<List<T>> = Observable.fromCallable { from.map { mapFrom(it) } }

}