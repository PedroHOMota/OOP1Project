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

public class Cd extends Item{
    private String artist;

    public Cd(final String name, final Date publishedDate, final int totalUnits, final int availableUnits, final String artist) {
        super(name, publishedDate, totalUnits, availableUnits);
        this.artist = artist;
    }

    public String getArtist() {
        return artist;
    }
}
