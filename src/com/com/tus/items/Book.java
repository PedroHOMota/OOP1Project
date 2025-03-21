
package com.tus.items;

public final class Book extends Item {
    private String author;
    public Book(final String name, final String publishedDate, final int totalUnits, final int availableUnits, final String author, ItemTypeEnum typeEnum) {
        super(name, publishedDate, totalUnits, availableUnits, typeEnum);
        this.author = author;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(final String author) {
        this.author = author;
    }
}
