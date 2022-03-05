package com.gildedrose


import org.junit.jupiter.api.Test
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Disabled


//
//======================================
//Gilded Rose Requirements Specification
//======================================
//
//Hi and welcome to team Gilded Rose. As you know, we are a small inn with a prime location in a
//prominent city ran by a friendly innkeeper named Allison. We also buy and sell only the finest goods.
//Unfortunately, our goods are constantly degrading in quality as they approach their sell by date. We
//have a system in place that updates our inventory for us. It was developed by a no-nonsense type named
//Leeroy, who has moved on to new adventures. Your task is to add the new feature to our system so that
//we can begin selling a new category of items. First an introduction to our system:
//
//- All items have a SellIn value which denotes the number of days we have to sell the item
//- All items have a Quality value which denotes how valuable the item is
//- At the end of each day our system lowers both values for every item
//
//Pretty simple, right? Well this is where it gets interesting:
//
//- Once the sell by date has passed, Quality degrades twice as fast
//- The Quality of an item is never negative
//- "Aged Brie" actually increases in Quality the older it gets
//- The Quality of an item is never more than 50
//- "Sulfuras", being a legendary item, never has to be sold or decreases in Quality
//- "Backstage passes", like aged brie, increases in Quality as its SellIn value approaches;
//Quality increases by 2 when there are 10 days or less and by 3 when there are 5 days or less but
//Quality drops to 0 after the concert
//
//We have recently signed a supplier of conjured items. This requires an update to our system:
//
//- "Conjured" items degrade in Quality twice as fast as normal items
//
//Feel free to make any changes to the UpdateQuality method and add any new code as long as everything
//still works correctly. However, do not alter the Item class or Items property as those belong to the
//goblin in the corner who will insta-rage and one-shot you as he doesn't believe in shared code
//ownership (you can make the UpdateQuality method and Items property static if you like, we'll cover
//for you).
//
//Just for clarification, an item can never have its Quality increase above 50, however "Sulfuras" is a
//legendary item and as such its Quality is 80 and it never alters.
//



@Suppress("DANGEROUS_CHARACTERS")
internal class GildedRoseTest {

    @Test
    fun `Can only add ShopItems to GildedRose`() {
        assertThatThrownBy {
            GildedRose(arrayOf(Item("item1", 1, 1)))
        }.isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("Items may only contain objects of type ShopItem")


    }

//- All items have a SellIn value which denotes the number of days we have to sell the item
//- All items have a Quality value which denotes how valuable the item is
//- At the end of each day our system lowers both values for every item
    @Test
    fun `At the end of each day our system lowers both values for every item`() {
        val items = arrayOf<Item>(
            ShopItem("item1", 1, 1),
            ShopItem("item2", 2, 2))
        val app = GildedRose(items)

        app.updateQuality()

        assertThat(items).usingRecursiveFieldByFieldElementComparator().containsExactly(
            ShopItem("item1", 0, 0),
            ShopItem("item2", 1, 1))
    }

    @Test
    fun `Once the sell by date has passed, Quality degrades twice as fast`() {
        val item = ShopItem("item1", 0, 2)
        val app = GildedRose(arrayOf(item))

        app.updateQuality()

        assertThat(item).extracting(Item::sellIn, Item::quality)
            .containsExactly(-1, 0)
    }

    @Test
    fun `The Quality of an item is never negative`() {
        val item = ShopItem("item1", 0, 0)
        val app = GildedRose(arrayOf(item))

        app.updateQuality()

        assertThat(item).extracting(Item::quality).isEqualTo(0)
    }

    @Test
    fun `"Aged Brie" actually increases in Quality the older it gets`() {
        val item = ShopItem("Aged Brie", 0, 5)
        val app = GildedRose(arrayOf(item))

        app.updateQuality()

        assertThat(item).extracting(Item::sellIn, Item::quality)
            .containsExactly(-1, 7)
    }

    @Test
    fun `The Quality of an item is never more than 50`() {
        val item = ShopItem("Aged Brie", 1, 50)
        val app = GildedRose(arrayOf(item))

        app.updateQuality()

        assertThat(item).extracting(Item::quality).isEqualTo(50)
    }

    @Test
    fun `"Sulfuras, Hand of Ragnaros", being a legendary item, never has to be sold or decreases in Quality`() {
        val item = ShopItem("Sulfuras, Hand of Ragnaros", 0, 80)
        val app = GildedRose(arrayOf(item))

        app.updateQuality()

        assertThat(item).extracting(Item::sellIn, Item::quality)
            .containsExactly(0, 80)
    }

    @Test
    fun `"Backstage passes to a TAFKAL80ETC concert" increases in Quality as its SellIn value approaches`() {
        val item = ShopItem("Backstage passes to a TAFKAL80ETC concert", 15, 20)
        val app = GildedRose(arrayOf(item))

        app.updateQuality()

        assertThat(item).extracting(Item::sellIn, Item::quality)
            .containsExactly(14, 21)
    }

    @Test
    fun `"Backstage passes to a TAFKAL80ETC concert" increases double in Quality as its SellIn value approaches`() {
        val item = ShopItem("Backstage passes to a TAFKAL80ETC concert", 10, 20)
        val app = GildedRose(arrayOf(item))

        app.updateQuality()

        assertThat(item).extracting(Item::sellIn, Item::quality)
            .containsExactly(9, 22)
    }

    @Test
    fun `"Backstage passes to a TAFKAL80ETC concert" increases triple in Quality as its SellIn value approaches`() {
        val item = ShopItem("Backstage passes to a TAFKAL80ETC concert", 5, 20)
        val app = GildedRose(arrayOf(item))

        app.updateQuality()

        assertThat(item).extracting(Item::sellIn, Item::quality)
            .containsExactly(4, 23)
    }

    @Test
    fun `"Backstage passes to a TAFKAL80ETC concert" increases drop to zero when concert is over`() {
        val item = ShopItem("Backstage passes to a TAFKAL80ETC concert", 0, 20)
        val app = GildedRose(arrayOf(item))

        app.updateQuality()

        assertThat(item).extracting(Item::sellIn, Item::quality)
            .containsExactly(-1, 0)
    }

    @Disabled
    @Test
    fun `"Conjured" items degrade in Quality twice as fast as normal items`() {
        val item = ShopItem("Conjured Mana Cake", 3, 6)
        val app = GildedRose(arrayOf(item))

        app.updateQuality()

        assertThat(item).extracting(Item::sellIn, Item::quality)
            .containsExactly(2, 4)
    }
}


