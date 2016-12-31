package dev.suncha.quotes;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by MSI on 12/31/2016.
 */

public class favouritesAdapter extends RecyclerView.Adapter<favouritesAdapter.favouritesModelVH> {
    Context context;
    List<QuoteModel> quoteModel;
    AdapterView.OnItemClickListener clickListener;

    public favouritesAdapter(Context context, List<QuoteModel> quoteModel) {
        this.context = context;
        this.quoteModel = quoteModel;
    }

    @Override
    public favouritesAdapter.favouritesModelVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_favourites, parent, false);
        favouritesModelVH viewHolder = new favouritesModelVH(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(favouritesAdapter.favouritesModelVH holder, int position) {
        holder.favQuote.setText(quoteModel.get(position).getQuotation());
        holder.favAuthor.setText(quoteModel.get(position).getAuthor());

    }

    @Override
    public int getItemCount() {
        return quoteModel.size();
    }

    class favouritesModelVH extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView favQuote, favAuthor;

        public favouritesModelVH(View itemView) {
            super(itemView);
            favQuote = (TextView) itemView.findViewById(R.id.row_quote);
            favAuthor = (TextView) itemView.findViewById(R.id.row_author);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

        }

    }


}
