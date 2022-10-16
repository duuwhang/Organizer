package com.organizer.endless_scroll

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.organizer.DateController
import com.organizer.MainActivity.Companion.inject
import com.organizer.layouts.MainLayout

abstract class EndlessRecyclerViewScrollListener(layoutManager: LinearLayoutManager, days: Int) :
    RecyclerView.OnScrollListener() {

    private val dateController: DateController by inject()
    private val mainLayout: MainLayout by inject()

    // True if we are still waiting for the last set of data to load.
    private var loading = true
    // The minimum amount of items to have below your current scroll position
    // before loading more.
    private val visibleThreshold: Int
    // The current offset index of data you have loaded
    private var currentPage = 0
    // The current offset index of data you have loaded
    //private int negcurrentPage = 0; // TODO
    // The total number of items in the dataset after the last load
    private var previousTotalItemCount = 0
    // Sets the starting page index
    private val startingPageIndex = 0
    var mLayoutManager: RecyclerView.LayoutManager

    init {
        mLayoutManager = layoutManager
        visibleThreshold = days
        //layoutManager.scrollToPosition((Integer.MAX_VALUE / 2) - (Integer.MAX_VALUE / 2) % days.size());
    }

    @Suppress("unused")
    fun getLastVisibleItem(lastVisibleItemPositions: IntArray): Int {
        var maxSize = 0
        for (i in lastVisibleItemPositions.indices) {
            if (i == 0) {
                maxSize = lastVisibleItemPositions[i]
            } else if (lastVisibleItemPositions[i] > maxSize) {
                maxSize = lastVisibleItemPositions[i]
            }
        }
        return maxSize
    }

    @Suppress("unused")
    fun getFirstVisibleItem(lastVisibleItemPositions: IntArray): Int {
        var maxSize = 0
        for (i in lastVisibleItemPositions.indices) {
            if (i == 0) {
                maxSize = lastVisibleItemPositions[i]
            } else if (lastVisibleItemPositions[i] > maxSize) {
                maxSize = lastVisibleItemPositions[i]
            }
        }
        return maxSize
    }

    // This happens many times a second during a scroll, so be wary of the code you place here.
    // We are given a few useful parameters to help us work out if we need to load some more data,
    // but first we check if we are waiting for the previous load to finish.
    override fun onScrolled(view: RecyclerView, dx: Int, dy: Int) {
        val lastVisibleItemPosition =
            (mLayoutManager as LinearLayoutManager).findLastVisibleItemPosition()
        @Suppress("UNUSED_VARIABLE")
        val firstVisibleItemPosition =
            (mLayoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
        val totalItemCount = mLayoutManager.itemCount
        /*
        if (mLayoutManager instanceof StaggeredGridLayoutManager) {
            int[] lastVisibleItemPositions = ((StaggeredGridLayoutManager) mLayoutManager).findLastVisibleItemPositions(null);
            // get maximum element within the list
            lastVisibleItemPosition = getLastVisibleItem(lastVisibleItemPositions);
        } else if (mLayoutManager instanceof GridLayoutManager) {
            lastVisibleItemPosition = ((GridLayoutManager) mLayoutManager).findLastVisibleItemPosition();
        } else if (mLayoutManager instanceof LinearLayoutManager) {
            lastVisibleItemPosition =  // TODO same linear
            firstVisibleItemPosition
        }*/

        // If the total item count is zero and the previous isn't, assume the
        // list is invalidated and should be reset back to initial state
        if (totalItemCount < previousTotalItemCount) {
            if (totalItemCount == 0) {
                loading = true
            }
            currentPage = startingPageIndex
            //this.negcurrentPage = this.startingPageIndex;
            previousTotalItemCount = totalItemCount
        }
        // If it’s still loading, we check to see if the dataset count has
        // changed, if so we conclude it has finished loading and update the current page
        // number and total item count.
        if (loading && totalItemCount > previousTotalItemCount) {
            previousTotalItemCount = totalItemCount
            loading = false
        }

        // If it isn’t currently loading, we check to see if we have breached
        // the visibleThreshold and need to reload more data.
        // If we do need to reload some more data, we execute onLoadMore to fetch the data.
        // threshold should reflect how many total columns there are too
        if (lastVisibleItemPosition % dateController.days.size + visibleThreshold > (currentPage + 1) * mainLayout.calendarLayout.dayAmount) {
            loading = true
            currentPage++
            onLoadMore(currentPage, view)
            //mLayoutManager.scrollToPosition((Integer.MAX_VALUE / 2) - (Integer.MAX_VALUE / 2) % days.size());
        }
        /*
        if (!loading && (firstVisibleItemPosition % days.size() - visibleThreshold) < 0) { // TODO reverseScroll !!!
            loading = true;
            currentPage++;
            negcurrentPage--;
            onLoadMore(negcurrentPage, view);
            //mLayoutManager.scrollToPosition((Integer.MAX_VALUE / 2) - (Integer.MAX_VALUE / 2) % days.size());
        }*/
    }

    // Call this method whenever performing new searches
    fun resetState() {
        loading = true
        currentPage = startingPageIndex
        //this.negcurrentPage = this.startingPageIndex;
        previousTotalItemCount = 0
        //mLayoutManager.scrollToPosition((Integer.MAX_VALUE / 2) - (Integer.MAX_VALUE / 2) % days.size());
    }

    abstract fun onLoadMore(page: Int, view: RecyclerView?)
}
