package comr.example.glossimar.stickyheaderrecyclerview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    StickyHeaderRecyclerView stickyHeaderRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        List<String> list = new ArrayList<>();
        int[] headerPosition  = {0,5,12,17,23,29,36,57};
        for (int i = 0; i < 40; i ++) {
            if (i == 0 || i == 5 || i == 12 || i == 17 || i ==23 || i == 29|| i == 36 || i ==57) {
                list.add("This is the group " + i);
            } else {
                list.add("test  : " + i);
            }
        }
        if (list.size() != 0 ) {
            stickyHeaderRecyclerView = findViewById(R.id.recycler_view);
//            stickyHeaderRecyclerView.setNestedScrollingEnabled(true);
            stickyHeaderRecyclerView.setActivity(this);
            TestAdaper adaper = new TestAdaper(list, this, headerPosition, stickyHeaderRecyclerView);
            stickyHeaderRecyclerView.addOnScrollListener(new OnScrollListener(stickyHeaderRecyclerView, this));
            stickyHeaderRecyclerView.setAdapter(adaper);
            stickyHeaderRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            stickyHeaderRecyclerView.setHeaderView(stickyHeaderRecyclerView);


        }
    }
}
