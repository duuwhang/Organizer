package com.example.organizer;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import static com.example.organizer.Functions.*;

public abstract class EndlessRecyclerViewScrollListener extends RecyclerView.OnScrollListener {
    // True if we are still waiting for the last set of data to load.
    private boolean loading = true;
    // The minimum amount of items to have below your current scroll position
    // before loading more.
    private int visibleThreshold = dayAmount;
    // The current offset index of data you have loaded
    private int currentPage = 0;
    // The current offset index of data you have loaded
    private int negcurrentPage = 0; // TODO
    // The total number of items in the dataset after the last load
    private int previousTotalItemCount = 0;
    // Sets the starting page index
    private int startingPageIndex = 0;



    RecyclerView.LayoutManager mLayoutManager;

    public EndlessRecyclerViewScrollListener(LinearLayoutManager layoutManager) {
        this.mLayoutManager = layoutManager;
        layoutManager.scrollToPosition((Integer.MAX_VALUE / 2) - (Integer.MAX_VALUE / 2) % days.size());
    }

    public int getLastVisibleItem(int[] lastVisibleItemPositions) {
        int maxSize = 0;
        for (int i = 0; i < lastVisibleItemPositions.length; i++) {
            if (i == 0) {
                maxSize = lastVisibleItemPositions[i];
            }
            else if (lastVisibleItemPositions[i] > maxSize) {
                maxSize = lastVisibleItemPositions[i];
            }
        }
        return maxSize;
    }
    public int getFirstVisibleItem(int[] lastVisibleItemPositions) {
        int maxSize = 0;
        for (int i = 0; i < lastVisibleItemPositions.length; i++) {
            if (i == 0) {
                maxSize = lastVisibleItemPositions[i];
            }
            else if (lastVisibleItemPositions[i] > maxSize) {
                maxSize = lastVisibleItemPositions[i];
            }
        }
        return maxSize;
    }

    // This happens many times a second during a scroll, so be wary of the code you place here.
    // We are given a few useful parameters to help us work out if we need to load some more data,
    // but first we check if we are waiting for the previous load to finish.
    @Override
    public void onScrolled(RecyclerView view, int dx, int dy) {
        int lastVisibleItemPosition = ((LinearLayoutManager) mLayoutManager).findLastVisibleItemPosition();
        int firstVisibleItemPosition = ((LinearLayoutManager) mLayoutManager).findFirstVisibleItemPosition();
        int totalItemCount = mLayoutManager.getItemCount();
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
                this.loading = true;
            }
            this.currentPage = this.startingPageIndex;
            this.negcurrentPage = this.startingPageIndex;
            this.previousTotalItemCount = totalItemCount;
        }
        // If it’s still loading, we check to see if the dataset count has
        // changed, if so we conclude it has finished loading and update the current page
        // number and total item count.
        if (loading && (totalItemCount > previousTotalItemCount)) {
            previousTotalItemCount = totalItemCount;
            loading = false;
        }

        // If it isn’t currently loading, we check to see if we have breached
        // the visibleThreshold and need to reload more data.
        // If we do need to reload some more data, we execute onLoadMore to fetch the data.
        // threshold should reflect how many total columns there are too
        if (!loading && (lastVisibleItemPosition % days.size() + visibleThreshold) > (currentPage + 1) * dayAmount) {
            loading = true;
            currentPage++;
            onLoadMore(currentPage, view);
            //mLayoutManager.scrollToPosition((Integer.MAX_VALUE / 2) - (Integer.MAX_VALUE / 2) % days.size());
        }

        if (!loading && (firstVisibleItemPosition % days.size() - visibleThreshold) < 0) { // TODO reverseScroll !!!
            loading = true;
            currentPage++;
            negcurrentPage--;
            onLoadMore(negcurrentPage, view);
            //mLayoutManager.scrollToPosition((Integer.MAX_VALUE / 2) - (Integer.MAX_VALUE / 2) % days.size());
        }
    }

    // Call this method whenever performing new searches
    public void resetState() {
        this.loading = true;
        this.currentPage = this.startingPageIndex;
        this.negcurrentPage = this.startingPageIndex;
        this.previousTotalItemCount = 0;
        mLayoutManager.scrollToPosition((Integer.MAX_VALUE / 2) - (Integer.MAX_VALUE / 2) % days.size());
    }
    public abstract void onLoadMore(int page, RecyclerView view);
}