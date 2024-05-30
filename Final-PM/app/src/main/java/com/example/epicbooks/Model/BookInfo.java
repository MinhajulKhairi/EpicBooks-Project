package com.example.epicbooks.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class BookInfo implements Parcelable {

    private String title, subtitle, publisher, publishedDate, description, thumbnail, previewLink, infoLink;
    private ArrayList<String> authors;
    private int pageCount;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public ArrayList<String> getAuthors() {
        return authors;
    }

    public void setAuthors(ArrayList<String> authors) {
        this.authors = authors;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(String publishedDate) {
        this.publishedDate = publishedDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getPreviewLink() {
        return previewLink;
    }

    public void setPreviewLink(String previewLink) {
        this.previewLink = previewLink;
    }

    public String getInfoLink() {
        return infoLink;
    }

    public void setInfoLink(String infoLink) {
        this.infoLink = infoLink;
    }

//    public String getBuyLink() {
//        return buyLink;
//    }
//
//    public void setBuyLink(String buyLink) {
//        this.buyLink = buyLink;
//    }


    public BookInfo(String title, String subtitle, ArrayList<String> authors, String publisher,
                    String publishedDate, String description, int pageCount, String thumbnail,
                    String previewLink, String infoLink) {
        this.title = title;
        this.subtitle = subtitle;
        this.authors = authors;
        this.publisher = publisher;
        this.publishedDate = publishedDate;
        this.description = description;
        this.pageCount = pageCount;
        this.thumbnail = thumbnail;
        this.previewLink = previewLink;
        this.infoLink = infoLink;
//        this.buyLink = buyLink;
    }

    protected BookInfo(Parcel in) {
        title = in.readString();
        subtitle = in.readString();
        authors = in.createStringArrayList();
        publisher = in.readString();
        publishedDate = in.readString();
        description = in.readString();
        pageCount = in.readInt();
        thumbnail = in.readString();
        previewLink = in.readString();
        infoLink = in.readString();
//        buyLink = in.readString();
    }

    public static final Creator<BookInfo> CREATOR = new Creator<BookInfo>() {
        @Override
        public BookInfo createFromParcel(Parcel in) {
            return new BookInfo(in);
        }

        @Override
        public BookInfo[] newArray(int size) {
            return new BookInfo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(subtitle);
        dest.writeStringList(authors);
        dest.writeString(publisher);
        dest.writeString(publishedDate);
        dest.writeString(description);
        dest.writeInt(pageCount);
        dest.writeString(thumbnail);
        dest.writeString(previewLink);
        dest.writeString(infoLink);
//        dest.writeString(buyLink);
    }
}
