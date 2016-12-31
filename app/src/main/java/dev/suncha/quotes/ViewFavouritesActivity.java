package dev.suncha.quotes;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ViewFavouritesActivity extends AppCompatActivity {

    favouritesAdapter adapter;
    List<QuoteModel> quotes = new ArrayList<>();
    long count;

    @BindView(R.id.fav_recyclerview)
    RecyclerView favRecyclerview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);
        ButterKnife.bind(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        favRecyclerview.setLayoutManager(linearLayoutManager);

        count = QuoteModel.count(QuoteModel.class);

        if (count >= 0) {
            quotes = QuoteModel.listAll(QuoteModel.class);
            adapter = new favouritesAdapter(ViewFavouritesActivity.this, quotes);
            favRecyclerview.setAdapter(adapter);

            if (quotes.isEmpty()) {
                Snackbar.make(favRecyclerview, "Nothing here", Snackbar.LENGTH_SHORT).show();
            }

        }

        ItemTouchHelper helper = new ItemTouchHelper(simpleCallback);
        helper.attachToRecyclerView(favRecyclerview);

    }

    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            final int position = viewHolder.getAdapterPosition();
            final QuoteModel quote = quotes.get(viewHolder.getAdapterPosition());
            quotes.remove(viewHolder.getAdapterPosition());
            adapter.notifyItemRemoved(position);
            quote.delete();
            count -= 1;

            Snackbar.make(favRecyclerview, R.string.quoteDeleted, Snackbar.LENGTH_SHORT)
                    .setAction(R.string.undo, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            quote.save();
                            quotes.add(position, quote);
                            adapter.notifyItemInserted(position);
                            count += 1;
                        }
                    }).show();
        }
    };
}
