package dev.suncha.quotes;

import android.content.Context;
import android.graphics.PixelFormat;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.varunest.sparkbutton.SparkButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import mehdi.sakout.fancybuttons.FancyButton;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.quoteTV)
    TextView quoteTV;
    @BindView(R.id.authorTV)
    TextView authorTV;
    @BindView(R.id.shareButton)
    FancyButton share;
    @BindView(R.id.nextButton)
    FancyButton nextButton;
    @BindView(R.id.likeButton)
    SparkButton likeButton;
    @BindView(R.id.retryButton)
    Button retryButton;
    @BindView(R.id.noInternetLayout)
    RelativeLayout noInternetLayout;
    @BindView(R.id.quoteandauthor)
    RelativeLayout quoteAndAuthor;
    @BindView(R.id.buttonHolder)
    RelativeLayout buttonHolder;

    String quotation;
    String authorName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isInternetOn(getApplicationContext())) {
                    noInternetLayout.setVisibility(View.GONE);
                    quoteAndAuthor.setVisibility(View.VISIBLE);
                    buttonHolder.setVisibility(View.VISIBLE);
                    setQuotes();
                } else {
                    //hide everything
                    //make retry button appear
                    //if internet is available is availabe once the button is clicked, call setQuotes()noInternetLayout.setVisibility(View.VISIBLE);
                    noInternetLayout.setVisibility(View.VISIBLE);
                    quoteAndAuthor.setVisibility(View.GONE);
                    buttonHolder.setVisibility(View.GONE);
                }

            }
        });
        if (isInternetOn(getApplicationContext())) {
            noInternetLayout.setVisibility(View.GONE);
            setQuotes();
        } else {
            noInternetLayout.setVisibility(View.VISIBLE);
            quoteAndAuthor.setVisibility(View.GONE);
            buttonHolder.setVisibility(View.GONE);
        }

        retryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isInternetOn(getApplicationContext())) {
                    noInternetLayout.setVisibility(View.GONE);
                    quoteAndAuthor.setVisibility(View.VISIBLE);
                    buttonHolder.setVisibility(View.VISIBLE);
                    setQuotes();
                } else {
                    //Do nothing
                }
            }
        });
    }

    public static boolean isInternetOn(Context context) {
        int[] networkTypes = {ConnectivityManager.TYPE_MOBILE,
                ConnectivityManager.TYPE_WIFI};
        try {
            ConnectivityManager connectivityManager =
                    (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            for (int networkType : networkTypes) {
                NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
                if (activeNetworkInfo != null &&
                        activeNetworkInfo.getType() == networkType)
                    return true;
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }


    private void setQuotes() {

        if (isInternetOn(getApplicationContext())) {
            noInternetLayout.setVisibility(View.GONE);
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        //Your code goes here
                        Forismatic forismatic = new Forismatic();
                        forismatic.setLanguage(Forismatic.ENGLISH);
                        Forismatic.Quote quote = forismatic.getQuote();

                        quotation = quote.getQuoteText();
                        authorName = quote.getQuoteAuthor();

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //Show on text view
                                quoteTV.setText(quotation);
                                if (authorName.trim().length() == 0) {
                                    authorName = getResources().getString(R.string.anon);
                                }
                                authorTV.setText(authorName);
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            thread.start();
        } else {
            noInternetLayout.setVisibility(View.VISIBLE);
            quoteAndAuthor.setVisibility(View.GONE);
            buttonHolder.setVisibility(View.GONE);
        }
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Window window = getWindow();
        window.setFormat(PixelFormat.RGBA_8888);
    }
}
