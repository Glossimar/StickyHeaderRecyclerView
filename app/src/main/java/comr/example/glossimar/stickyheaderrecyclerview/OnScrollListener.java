package comr.example.glossimar.stickyheaderrecyclerview;

import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.animation.LinearInterpolator;

/**
 * Created by glossimar on 2017/9/26.
 */

public class OnScrollListener extends RecyclerView.OnScrollListener{
    private StickyHeaderRecyclerView stickyRecycler;
    private Activity activity;

    public OnScrollListener(StickyHeaderRecyclerView recyclerView, Activity activity) {
        this.stickyRecycler = recyclerView;
        this.activity = activity;
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        if (dy > 0)
        stickyRecycler
                .configureHeaderView(stickyRecycler
                        .getSticyLayoutManager().findFirstVisibleItemPosition(), 2);
        else if (dy < 0)
            stickyRecycler.configureHeaderView(stickyRecycler
                    .getSticyLayoutManager().findFirstVisibleItemPosition(), -2);
    }
}
