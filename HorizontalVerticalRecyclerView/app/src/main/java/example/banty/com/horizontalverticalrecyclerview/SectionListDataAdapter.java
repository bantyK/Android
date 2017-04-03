package example.banty.com.horizontalverticalrecyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

class SectionListDataAdapter extends RecyclerView.Adapter<SectionListDataAdapter.SingleItemRowHolder> {
    private final Context context;
    private final ArrayList<SingleItemModel> itemList;

    SectionListDataAdapter(Context context, ArrayList<SingleItemModel> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @Override
    public SingleItemRowHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_card,parent,false);
        return new SingleItemRowHolder(view);
    }

    @Override
    public void onBindViewHolder(SingleItemRowHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return itemList != null ? itemList.size() : 0;
    }

     class SingleItemRowHolder extends RecyclerView.ViewHolder{

        protected TextView mTextView;
        protected ImageView mImageView;

        public SingleItemRowHolder(View itemView) {
            super(itemView);

            this.mTextView = (TextView) itemView.findViewById(R.id.tvTitle);
            this.mImageView = (ImageView) itemView.findViewById(R.id.itemImage);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(), mTextView.getText(), Toast.LENGTH_SHORT).show();
                }
            });
        }


    }
}
