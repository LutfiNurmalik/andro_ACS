package com.regulasiudara.aircargoshippingguidelines;
import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import java.util.List;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ViewHolder> {
    List<ArticleModel> list;
    Context context;

    private Activity activity;
    private LayoutInflater inflater;
    private List<DataModel> item;

    class ViewHolder extends RecyclerView.ViewHolder {
        public TextView headerItem;
        ViewHolder(View itemView){
            super(itemView);
            headerItem = (TextView) itemView.findViewById(R.id.headerItem);
        }
    }

    public ArticleAdapter(List<ArticleModel> list, Context context){
        this.list = list;
        this.context = context;
    }
    @Override
    public ArticleAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.artikel_list, parent, false);
        ArticleAdapter.ViewHolder holder = new ArticleAdapter.ViewHolder(v);
        return holder;
    }
    @Override
    public void onBindViewHolder(ArticleAdapter.ViewHolder holder, int position) {
        holder.headerItem.setText(list.get(position).judul);
        holder.headerItem.setText(list.get(position).link);
    }
    @Override
    public int getItemCount() {
        //returns the number of elements the RecyclerView will display
        return list.size();
    }
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }


    public static class Adapter extends BaseAdapter {
        private Activity activity;
        private LayoutInflater inflater;
        private List<ArticleModel> item;

        public Adapter(Activity activity, List<ArticleModel> item) {
            this.activity = activity;
            this.item = item;
        }

    @Override
    public int getCount() {
        return item.size();
    }

    @Override
    public Object getItem(int location) {
        return item.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null)
            convertView = inflater.inflate(R.layout.artikel_list, null);

        TextView txt_nama = (TextView) convertView.findViewById(R.id.headerItem);

        txt_nama.setText(item.get(position).getJudul());

        return convertView;
    }
}
}
