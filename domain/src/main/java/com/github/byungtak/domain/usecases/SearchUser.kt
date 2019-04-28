package com.github.byungtak.domain.usecases

import com.github.byungtak.domain.UserRepository
import com.github.byungtak.domain.common.Transformer
import com.github.byungtak.domain.entities.UserEntity
import io.reactivex.Observable


class SearchUser(
    transformer: Transformer<List<UserEntity>>,
    private val userRepository: UserRepository
): UseCase<List<UserEntity>>(transformer) {

    companion object {
        private const val PARAM_SEARCH_QUERY = "param:search_query"
    }

    fun searchUser(query: String, currentPage: Int): Observable<List<UserEntity>> {
        val data = HashMap<String, Pair<String, Int>>()
        data[PARAM_SEARCH_QUERY] = Pair(query, currentPage)
        return observable(data)
    }

    override fun createObservable(data: Map<String, Any>?): Observable<List<UserEntity>> {
        val searchData = data?.get(PARAM_SEARCH_QUERY)
        searchData?.let {
            val data = it as Pair<*, *>
            val query = data.first
            val page = data.second

            return userRepository.searchUser(query as String, page as Int)
        } ?: return Observable.just(emptyList())
    }

}