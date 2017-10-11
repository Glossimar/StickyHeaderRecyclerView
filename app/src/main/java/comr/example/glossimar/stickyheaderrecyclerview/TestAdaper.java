package comr.example.glossimar.stickyheaderrecyclerview;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.IllegalFormatCodePointException;
import java.util.List;
import java.util.zip.Inflater;

import static android.content.ContentValues.TAG;

/**
 * Created by glossimar on 2017/9/26.
 */

public class TestAdaper extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements StickyHeaderAdapter{
    public static int HEADER_VIEW_TYPE = 1;
    public static int CHILD_VIEW_TYPE = 2;

    private List<String> list;
    private int[] headerPosition;

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    private Context context;

    private StickyHeaderRecyclerView stickyRecycler;
    public TestAdaper(List<String> list, Context context, int[] headerPosition, StickyHeaderRecyclerView stickyRecycler) {
        this.list = list;
        this.context = context;
        this.headerPosition = headerPosition;
        this.stickyRecycler = stickyRecycler;
    }

    static class HeaderViewHolder extends RecyclerView.ViewHolder {
        TextView headerTextView;
        public HeaderViewHolder(View view){
            super(view);
            headerTextView = view.findViewById(R.id.header_item);
        }
    }

    static class ChildViewHolder extends RecyclerView.ViewHolder {
        TextView childTextView;
        public ChildViewHolder(View view){
            super(view);
            childTextView = view.findViewById(R.id.child_item);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ChildViewHolder childHolder ;
        HeaderViewHolder headerHolder;
        View view;

        if (viewType == HEADER_VIEW_TYPE) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.header_view, parent, false);
            headerHolder = new HeaderViewHolder(view);
            return headerHolder;
        } else if (viewType == CHILD_VIEW_TYPE){
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_view, parent, false);
            childHolder =  new ChildViewHolder(view);
            return childHolder;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
      if (holder instanceof  HeaderViewHolder)
          ((HeaderViewHolder) holder).headerTextView.setText(list.get(position));
      else if (holder instanceof ChildViewHolder)
          ((ChildViewHolder) holder).childTextView.setText(list.get(position));
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getStickyHeaderState(int position) {
        if (position < 0) {
            return STICKY_HEADER_GONE;
        }

        int currentHeaderIndex = getCurrentSubHeaderIndex(position);
        int nextHeaderIndex = currentHeaderIndex + 1;

        if (
        position == (headerPosition[nextHeaderIndex] - 1)) {
            return STICKY_HEADER_SCROLL;
        }

        return STICKY_HEADER_VISIBLE;
    }

    @Override
    public void configureHeader(View view, int position, int alpha) {
        int current = getCurrentSubHeaderIndex(position);
        if (current  >= 0) {
            ((TextView)view.findViewById(R.id.header_item)).setText(list.get(headerPosition[current]));
        }
    }

    private boolean isHeaderPoition(int position) {
        for (int headerNum : headerPosition) {
            if (headerNum == position) {
                return true;
            }
        }
        return false;
    }

    //通过position判断该position属于哪个组
    private int getCurrentSubHeaderIndex(int position){
        int a = -1;
        for(int i = headerPosition.length - 1; i < headerPosition.length; i --){
            if (position >= headerPosition[i]) {
                a = i;
                return i;
            }
        }
        return a;
    }
    @Override
    public int getItemViewType(int position) {
        if (isHeaderPoition(position)) {
            return HEADER_VIEW_TYPE;
        }
        else {
            return CHILD_VIEW_TYPE;
        }
    }
}
