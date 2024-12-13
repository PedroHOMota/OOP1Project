

package com.tus.items;


public class Game extends Item{
    private GamePlatforms platform;
    public Game(final String name, final String publishedDate, final int totalUnits, final int availableUnits, final GamePlatforms platform, final ItemTypeEnum itemTypeEnum) {
        super(name, publishedDate, totalUnits, availableUnits,itemTypeEnum);
        this.platform = platform;
    }

    public GamePlatforms getPlatform() {
        return platform;
    }
}
