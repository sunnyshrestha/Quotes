package dev.suncha.quotes;

import com.orm.SugarRecord;

/**
 * Created by MSI on 12/31/2016.
 */

public class QuoteModel extends SugarRecord {
    public String quotation;
    public String author;

    public QuoteModel() {

    }

    //Default Constructor
    public QuoteModel(String quotation, String author) {

        this.quotation = quotation;
        this.author = author;
    }

    public String getQuotation() {
        return quotation;
    }

    public void setQuotation(String quotation) {
        this.quotation = quotation;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }


}
