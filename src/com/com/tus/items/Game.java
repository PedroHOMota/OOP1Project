

package com.tus.items;


public final class Game extends Item{
    private GamePlatformsEnum platform;
    public Game(final String name, final String publishedDate, final int totalUnits, final int availableUnits, final GamePlatformsEnum platform, final ItemTypeEnum itemTypeEnum) {
        super(name, publishedDate, totalUnits, availableUnits,itemTypeEnum);
        this.platform = platform;
    }

    public GamePlatformsEnum getPlatform() {
        return platform;
    }
}
