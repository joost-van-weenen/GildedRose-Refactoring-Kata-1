package com.gildedrose

class GildedRose(var items: Array<Item>) {

    fun updateQuality() {
        for (item in items) {
            if (item.name != "Aged Brie" && item.name != "Backstage passes to a TAFKAL80ETC concert") {
                if (item.hasQuality()) {
                    if (!item.isLegendary()) {
                        item.increaseQuality(-1)
                    }
                }
            } else {
                if (!item.hasMaxQuality()) {
                    item.increaseQuality()

                    if (item.name == "Backstage passes to a TAFKAL80ETC concert") {
                        if (item.isSellDateReached(11)) {
                            if (!item.hasMaxQuality()) {
                                item.increaseQuality()
                            }
                        }

                        if (item.isSellDateReached(6)) {
                            if (!item.hasMaxQuality()) {
                                item.increaseQuality()
                            }
                        }
                    }
                }
            }

            if (!item.isLegendary()) {
                item.decreaseSellin()
            }

            if (item.isSellDateReached()) {
                if (item.name != "Aged Brie") {
                    if (item.name != "Backstage passes to a TAFKAL80ETC concert") {
                        if (item.hasQuality()) {
                            if (!item.isLegendary()) {
                                item.increaseQuality(-1)

                            }
                        }
                    } else {
                        item.dropQualityToZero()
                    }
                } else {
                    if (!item.hasMaxQuality()) {
                        item.increaseQuality()
                    }
                }
            }
        }
    }




}
private fun Item.isSellDateReached(daysToSell: Int = 0 ) = sellIn < daysToSell

private fun Item.hasQuality() = quality > 0

private fun Item.hasMaxQuality() = quality >= 50

fun Item.isLegendary() = name == "Sulfuras, Hand of Ragnaros"

fun Item.dropQualityToZero() {
    quality = 0
}

fun Item.decreaseSellin(value: Int = 1) {
    sellIn -= value
}

fun Item.increaseQuality(value: Int = 1) {
    quality += value
}

open class Item(var name: String, var sellIn: Int, var quality: Int) {
    override fun toString(): String {
        return this.name + ", " + this.sellIn + ", " + this.quality
    }
}