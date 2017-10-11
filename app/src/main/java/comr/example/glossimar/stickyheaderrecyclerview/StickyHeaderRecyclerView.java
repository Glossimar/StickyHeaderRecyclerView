package comr.example.glossimar.stickyheaderrecyclerview;


import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

/**
 * Created by glossimar on 2017/9/24.
 */

public class StickyHeaderRecyclerView extends RecyclerView{
    private TestAdaper adapter;
    private LinearLayoutManager linearLayoutManager;

    private View headerView;
    private Activity activity;

    private int headerViewHeight;
    private int headerViewWidth;
    public final static int MAX_ALPHA = 255;

    private boolean headerViewVisible;

    public StickyHeaderRecyclerView(Context context) {
        super(context);
    }

    public StickyHeaderRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public StickyHeaderRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setHeaderView(StickyHeaderRecyclerView recyclerView) {
        View view = LayoutInflater.from((Activity)adapter.getContext())
                .inflate(R.layout.header_view, recyclerView, false);
        headerView = view;
        if (headerView != null) setFadingEdgeLength(0);
        requestLayout();
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void setAdapter(Adapter adapter) {
        super.setAdapter(adapter);
        this.adapter = (TestAdaper)adapter;
    }

    public StickyHeaderAdapter getSticyAdapter() {
        return adapter;
    }

    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        super.onMeasure(widthSpec, heightSpec);
        if (headerView != null) {
            measureChild(headerView, widthSpec, heightSpec);
            headerViewWidth = headerView.getMeasuredWidth();
            headerViewHeight = headerView.getMeasuredHeight();
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        headerView.layout(0, 0, headerViewWidth, headerViewHeight);
        configureHeaderView(linearLayoutManager.findFirstVisibleItemPosition() ,0);
    }

    @Override
    public void setLayoutManager(LayoutManager layout) {
        super.setLayoutManager(layout);
        this.linearLayoutManager = (LinearLayoutManager)layout;
    }

    public LinearLayoutManager getSticyLayoutManager() {
        return linearLayoutManager;
    }

    public void configureHeaderView(int position, int direction) {
        if (headerView == null) return;

        int state = adapter.getStickyHeaderState(position);
        switch (state) {
            case StickyHeaderAdapter.STICKY_HEADER_GONE:
                headerViewVisible = false;
                break;

            case StickyHeaderAdapter.STICKY_HEADER_VISIBLE:

                adapter.configureHeader(headerView, position, MAX_ALPHA);
                if (headerView.getTop() != 0)
                    headerView.layout(0, 0, headerViewWidth, headerViewHeight);
                headerViewVisible = true;
                break;

            case StickyHeaderAdapter.STICKY_HEADER_SCROLL:
                View firstItem = getChildAt(0);
                int bottom = firstItem.getBottom();
                int headerHeight = headerView.getHeight();
                int y;
                int height;
                int alpha;

                if (bottom < headerHeight) {
                    y = headerHeight - bottom;
//                    alpha = (MAX_ALPHA * (-y + headerHeight)) / headerHeight;
                    Log.d("StickyHeade rRecycler", "configureHeaderView: kkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk    " + y);
                } else {
                    y = 0;
                    alpha = MAX_ALPHA;
                }
                adapter.configureHeader(headerView, position, MAX_ALPHA);

                if (direction < 0)
                    headerView.layout(0, -y, headerViewWidth, (headerViewHeight - y));
                else if (direction > 0)
                    headerView.layout(0, -y, headerViewWidth, headerViewHeight -y);
                headerViewVisible = true;
                break;
        }
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if (headerViewVisible) drawChild(canvas, headerView, getDrawingTime());
    }
}
