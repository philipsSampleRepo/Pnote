package com.palo.palonote.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "notes_info", indices = @Index(value = {"note_id"}, unique = true))
public class PaloNotesModel implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private int note_id;

    @ColumnInfo(name = "note_title")
    private String note_title;

    @ColumnInfo(name = "note_content")
    private String note_content;

    public int getNote_id() {
        return note_id;
    }

    public void setNote_id(int note_id) {
        this.note_id = note_id;
    }

    public String getNote_title() {
        return note_title;
    }

    public void setNote_title(String note_title) {
        this.note_title = note_title;
    }

    public String getNote_content() {
        return note_content;
    }

    public void setNote_content(String note_content) {
        this.note_content = note_content;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.note_id);
        dest.writeString(this.note_title);
        dest.writeString(this.note_content);
    }

    public void readFromParcel(Parcel source) {
        this.note_id = source.readInt();
        this.note_title = source.readString();
        this.note_content = source.readString();
    }

    public PaloNotesModel() {
    }

    protected PaloNotesModel(Parcel in) {
        this.note_id = in.readInt();
        this.note_title = in.readString();
        this.note_content = in.readString();
    }

    public static final Parcelable.Creator<PaloNotesModel> CREATOR =
            new Parcelable.Creator<PaloNotesModel>() {
                @Override
                public PaloNotesModel createFromParcel(Parcel source) {
                    return new PaloNotesModel(source);
                }

                @Override
                public PaloNotesModel[] newArray(int size) {
                    return new PaloNotesModel[size];
                }
            };
}

