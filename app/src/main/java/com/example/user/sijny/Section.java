package com.example.user.sijny;

import android.text.SpannableString;

public class Section {
    private String title;
    private SpannableString description;
    private String span;

    //Constructor

    public Section(String title, SpannableString description) {
        this.title = title;
        this.description = description;
        this.span = span;
    }

    //Setter, getter

    public String getName() {
        return title;
    }

    public void setName(String name) {
        this.title = name;
    }

    public SpannableString getDescription() {
        return description;
    }

    public String getSpan() {
        return span;
    }

    public void setDescription( SpannableString description) {
        this.description = description;
    }
}
