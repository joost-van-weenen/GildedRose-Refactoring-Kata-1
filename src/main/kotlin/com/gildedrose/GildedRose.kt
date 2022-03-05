package com.gildedrose

class GildedRose(var items: Array<Item>) {
    init {
        if (items.any { it !is ShopItem }) {
            throw IllegalArgumentException("Items may only contain objects of type ShopItem")
        }
    }

    fun updateQuality() {
        items.forEach {
            it as ShopItem
            it.updateSellIn()
            it.updateQuality()
        }
    }
}

open class Item(var name: String, var sellIn: Int, var quality: Int) {
    override fun toString(): String {
        return this.name + ", " + this.sellIn + ", " + this.quality
    }
}

open class ShopItem(name: String, sellIn: Int, quality: Int) : Item(name, sellIn, quality) {
    open fun updateQuality() {
        decreaseQuality()
        if (sellIn <= 0) {
            quality = 0
        }
    }

    open fun updateSellIn() {
        sellIn -= 1
    }
    fun isSellDateReached(daysToSell: Int = 0) = sellIn < daysToSell


    fun decreaseQuality(value: Int = 1) {
        if (quality > 0) {
            quality -= value
        }
        if (quality < 0) {
            quality = 0
        }
    }
    fun increaseQuality(value: Int = 1) {
        if (quality < 50) {
            quality += value
        }
    }



}

open class Sulfuras() : ShopItem("Sulfuras, Hand of Ragnaros", 0, 80) {
    override fun updateSellIn() {
        // Never update sellIn value
    }
    override fun updateQuality() {
        // Never updates its quality
    }
}

open class BackstagePass(sellIn: Int, quality: Int) :
    ShopItem("Backstage passes to a TAFKAL80ETC concert", sellIn, quality) {
    override fun updateQuality() {
        increaseQuality()
        if (isSellDateReached(11)) {
            increaseQuality()
        }
        if (isSellDateReached(6)) {
            increaseQuality()
        }

        if (isSellDateReached(0)) {
            quality = 0
        }
    }
}

open class AgedBrie(sellIn: Int, quality: Int) :
    ShopItem("Aged Brie", sellIn, quality) {
    override fun updateQuality() {
        increaseQuality()
    }
}


open class Conjured(sellIn: Int, quality: Int) :
    ShopItem("Conjured Mana Cake", sellIn, quality) {
    override fun updateQuality() {
        decreaseQuality(2)
    }
}