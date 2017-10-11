package comr.example.glossimar.stickyheaderrecyclerview;

import android.view.View;

/**
 * Created by glossimar on 2017/9/24.
 */

public interface StickyHeaderAdapter {
    int STICKY_HEADER_GONE = 0;
    int STICKY_HEADER_VISIBLE = 1;
    int STICKY_HEADER_SCROLL = 2;

    int getStickyHeaderState(int position);
    void configureHeader(View view, int position, int  alpha);
}
