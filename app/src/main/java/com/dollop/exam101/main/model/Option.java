package com.dollop.exam101.main.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class Option {

    @SerializedName("options")
    @Expose
    public String options;

    public boolean Selected = false;


    public Option(boolean selected) {
        Selected = selected;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Option option = (Option) o;

        return Selected == option.Selected;
    }

    @Override
    public int hashCode() {
        return Objects.hash(Selected);
    }

    @Override
    public String toString() {
        return "Option{" +
                "options='" + options + '\'' +
                ", Selected=" + Selected +
                '}';
    }
}