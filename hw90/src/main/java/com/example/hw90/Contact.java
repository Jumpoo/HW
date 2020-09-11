package com.example.hw90;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Objects;

@Entity(tableName = "ContactTable")
public class Contact implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private int icon;

    private String line1;

    private String line2;

    public Contact() {
    }

    public Contact(int icon, String line1, String line2) {
        this.icon = icon;
        this.line1 = line1;
        this.line2 = line2;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public void setLine1(String line1) {
        this.line1 = line1;
    }

    public void setLine2(String line2) {
        this.line2 = line2;
    }

    public int getIcon() {
        return icon;
    }

    public String getLine1() {
        return line1;
    }

    public String getLine2() {
        return line2;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "icon=" + icon +
                ", line1='" + line1 + '\'' +
                ", line2='" + line2 + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contact contact = (Contact) o;
        return icon == contact.icon &&
                Objects.equals(line1, contact.line1) &&
                Objects.equals(line2, contact.line2);
    }

    @Override
    public int hashCode() {
        return Objects.hash(icon, line1, line2);
    }
}