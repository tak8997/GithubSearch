package com.github.byungtak.githubsearch.ui.common


import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class PaginationScrollListener(private var layoutManager: LinearLayoutManager) : RecyclerView.OnScrollListener() {

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        val visibleItemCount = layoutManager.childCount
        val totalItemCount = layoutManager.itemCount
        val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

        Log.d("MY_LOG", "scrolled $visibleItemCount , $totalItemCount , $firstVisibleItemPosition")
        Log.d("MY_LOG", isLastPage.toString() + ", " + isLoading)
        if (!isLoading && !isLastPage) {
            if (visibleItemCount + firstVisibleItemPosition >= totalItemCount
                && firstVisibleItemPosition >= 0
//                && totalItemCount >= PAGE_SIZE
            ) {
                loadMoreItems()
            }
        }

    }

    abstract val isLastPage: Boolean

    abstract val isLoading: Boolean

    protected abstract fun loadMoreItems()

    companion object {

        /**
         * Set scrolling threshold here (for now i'm assuming 10 item in one page)
         */
        private const val PAGE_SIZE = 5
    }

}