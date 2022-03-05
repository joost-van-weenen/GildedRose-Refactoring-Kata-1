package com.gildedrose

fun main(args: Array<String>) {

    println("OMGHAI!")

    val items = arrayOf<Item>(
        ShopItem("+5 Dexterity Vest", 10, 20), //
        AgedBrie(2, 0), //
        ShopItem("Elixir of the Mongoose", 5, 7), //
        Sulfuras(),
        BackstagePass(15, 20),
        BackstagePass(10, 49),
        BackstagePass(5, 49),
        // this conjured item does not work properly yet
        Conjured(3, 6)
    )

    val app = GildedRose(items)

    var days = 30
    if (args.size > 0) {
        days = Integer.parseInt(args[0]) + 1
    }

    for (i in 0 until days) {
        println("-------- day $i --------")
        println("name, sellIn, quality")
        for (item in items) {
            println(item)
        }
        println()
        app.updateQuality()
    }


}
