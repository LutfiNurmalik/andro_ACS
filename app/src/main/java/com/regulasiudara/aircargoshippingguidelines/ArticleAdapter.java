package com.regulasiudara.aircargoshippingguidelines;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import java.util.List;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ViewHolder> {
    List<ArticleModel> list;
    Context context;
    class ViewHolder extends RecyclerView.ViewHolder {
        public TextView headerItem;
        public ImageView gambarItem;
        ViewHolder(View itemView){
            super(itemView);
            headerItem = (TextView) itemView.findViewById(R.id.headerItem);
            gambarItem = (ImageView) itemView.findViewById(R.id.gambarItem);
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
}
