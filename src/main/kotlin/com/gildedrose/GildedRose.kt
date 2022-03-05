package com.gildedrose

class GildedRose(private var items: Array<Item>) {
    init {
        if (items.any { it !is ShopItem }) {
            throw IllegalArgumentException("Items may only contain objects of type ShopItem")
        }
    }

    fun updateQuality() {
        items.forEach {
            it as ShopItem
            it.decreaseSellIn()
            it.updateQuality()
        }
    }
}

open class Item(private var name: String, var sellIn: Int, var quality: Int) {
    override fun toString(): String {
        return this.name + ", " + this.sellIn + ", " + this.quality
    }
}

private const val LEGENDARY_ITEM_QUALITY = 80
private const val MAX_ITEM_QUALITY = 50
private const val MIN_ITEM_QUALITY = 0

open class ShopItem(name: String, sellIn: Int, quality: Int) : Item(name, sellIn, quality) {
    open fun updateQuality() {
        decreaseQuality()
        voidItemQualityWhenSellDateReached()
    }

    open fun decreaseSellIn() {
        sellIn -= 1
    }

    fun decreaseQuality(value: Int = 1) {
        if (quality > MIN_ITEM_QUALITY) {
            quality -= value
        }
        voidItemQualityWhenSellDateReached()
    }

    fun increaseQuality(value: Int = 1) {
        if (quality < MAX_ITEM_QUALITY) {
            quality += value
        }
        if (quality > MAX_ITEM_QUALITY) {
            quality = MAX_ITEM_QUALITY
        }
    }

    fun isSellDateReached(daysToSell: Int) = sellIn < daysToSell

    fun voidItemQualityWhenSellDateReached() {
        if (isSellDateReached(0)) {
            quality = MIN_ITEM_QUALITY
        }
    }
}


open class Sulfuras : ShopItem("Sulfuras, Hand of Ragnaros", 0, LEGENDARY_ITEM_QUALITY) {
    override fun decreaseSellIn() {
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
        voidItemQualityWhenSellDateReached()
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