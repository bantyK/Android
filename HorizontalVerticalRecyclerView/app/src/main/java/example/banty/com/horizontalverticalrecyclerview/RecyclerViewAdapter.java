package example.banty.com.horizontalverticalrecyclerview;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ItemRowHolder> {

    private static final int VIEWTYPE1 = 1;
    private static final int VIEWTYPE2 = 2;
    private final ArrayList<SectionDataModel> allSampleData;
    private final Context context;

    public RecyclerViewAdapter(Context context, ArrayList<SectionDataModel> allSampleData) {
        this.allSampleData = allSampleData;
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) {
        if (position % 2 == 0)
            return VIEWTYPE1;
        else
            return VIEWTYPE2;
    }

    @Override
    public ItemRowHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // if we have a switch case here then that means we will have different type of layout in different rows of the main recycler view
        View v = null;
        if (viewType == VIEWTYPE1) {
            v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item_1, parent, false);
        } else {
            v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item_2, parent, false);
        }
        return new ItemRowHolder(v);
    }

    @Override
    public void onBindViewHolder(ItemRowHolder holder, int position) {
        final String sectionName = allSampleData.get(position).getHeaderTitle();
        ArrayList singleSectionItems = allSampleData.get(position).getAllItemsInSection();

        holder.itemTitle.setText(sectionName);
        SectionListDataAdapter sectionListDataAdapter = new SectionListDataAdapter(context, singleSectionItems);
        holder.recycler_view_list.setHasFixedSize(true);
        holder.recycler_view_list.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        holder.recycler_view_list.setAdapter(sectionListDataAdapter);


        holder.btnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "click event on more, " + sectionName, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return allSampleData != null ? allSampleData.size() : 0;
    }


    public class ItemRowHolder extends RecyclerView.ViewHolder {
        protected TextView itemTitle;

        protected RecyclerView recycler_view_list;

        protected Button btnMore;

        public ItemRowHolder(View view) {
            super(view);
            this.itemTitle = (TextView) view.findViewById(R.id.itemTitle);
            this.recycler_view_list = (RecyclerView) view.findViewById(R.id.horizontal_recycler_view);
            this.btnMore = (Button) view.findViewById(R.id.btnMore);

        }


    }
}
