
package com.tus.items;

public class Cd extends Item{
    private String artist;

    public Cd(final String name, final String publishedDate, final int totalUnits, final int availableUnits, final String artist, final ItemTypeEnum itemTypeEnum) {
        super(name, publishedDate, totalUnits, availableUnits, itemTypeEnum);
        this.artist = artist;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(final String artist) {
        this.artist = artist;
    }
}
