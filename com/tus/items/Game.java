/*------------------------------------------------------------------------------
 *******************************************************************************
 * COPYRIGHT Ericsson 2024
 *
 * The copyright to the computer program(s) herein is the property of
 * Ericsson Inc. The programs may be used and/or copied only with written
 * permission from Ericsson Inc. or in accordance with the terms and
 * conditions stipulated in the agreement/contract under which the
 * program(s) have been supplied.
 *******************************************************************************
 *----------------------------------------------------------------------------*/

package com.tus.items;

import java.util.Date;

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
