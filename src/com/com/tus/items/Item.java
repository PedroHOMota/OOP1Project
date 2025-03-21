

package com.tus.items;

public sealed abstract class Item permits Book,Cd,Game {

    private String name;
    private String creationDate;
    private int totalUnits = 0;
    private int availableUnits = 0;
    private ItemTypeEnum itemType = null;

    public Item(String name, String publishedDate, int totalUnits, int availableUnits, ItemTypeEnum typeEnum){
        this.name = name;
        this.creationDate = publishedDate;
        this.availableUnits = availableUnits;
        this.totalUnits = totalUnits;
        itemType = typeEnum;
    }

    public void changeAmountOfUnits(int newTotalUnits) throws Exception{
        if (availableUnits > newTotalUnits) {
            throw new Exception(); //Change ex
            //Cant let update cause number of borrowed units would be bigger than total of available units
        }

        if(newTotalUnits > totalUnits){
            availableUnits += (newTotalUnits - totalUnits);
        } else {
            availableUnits += (totalUnits - newTotalUnits);
        }// get the difference between the new total
        //and either increases or decreses the number of availavbleUnits
        totalUnits = newTotalUnits;
    }

    public boolean borrowUnit(){
        if(availableUnits > 0){
            availableUnits--;
            return true;
        }
        return false;
    }

    public void returnUnit(){
        availableUnits++;
    }

    public String getName(){
        return name;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public int getTotalUnits() {
        return totalUnits;
    }

    public int getAvailableUnits() {
        return availableUnits;
    }

    public ItemTypeEnum getItemType() {
        return itemType;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setCreationDate(final String creationDate) {
        this.creationDate = creationDate;
    }

    public void setTotalUnits(final int totalUnits) {
        this.totalUnits = totalUnits;
    }
}
