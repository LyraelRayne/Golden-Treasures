package net.snowshock.goldentreasures.utils;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.snowshock.goldentreasures.references.ReferencesModInfo;

/**
 * A helper class for blocks and items, but not manipulating objects themselves. Right now,
 * all it does it provide a clean way to get a block identifier (as in, minecraft:water).
 *
 * @author TheMike
 */
public class ContentHelper {

    /**
     * Returns a block identifier. Examples of these are 'minecraft:water', 'xreliquary:lilypad', etc. Will return null
     * if the block itself is null.
     *
     * @param block The block to get the identifier from.
     */
    public static String getIdent(Block block) {
        return block == null ? null : Block.blockRegistry.getNameForObject(block);
    }

    /**
     * Returns a item identifier. Examples of these are 'minecraft:water', 'xreliquary:lilypad', etc. Will return null
     * if the item itself is null.
     *
     * @param item The item to get the identifier from.
     */
    public static String getIdent(Item item) {
        return item == null ? null : Item.itemRegistry.getNameForObject(item);
    }

    /**
     * Returns true if the item identifier of item1 matches that of item2. Returns false otherwise.
     */
    public static boolean areItemsEqual(Item item1, Item item2) {
        return getIdent(item1).equals(getIdent(item2));
    }

    /**
     * Returns true if the block identifier of block1 matches that of block2. Returns false otherwise.
     */
    public static boolean areBlocksEqual(Block block1, Block block2) {
        return getIdent(block1).equals(getIdent(block2));
    }

    public static Block getBlock(String blockName) {
        String selection = blockName;
        if (!selection.contains(":"))
            selection = ReferencesModInfo.MOD_ID + ":" + selection;
        if (selection.indexOf(":") == 0)
            selection = selection.substring(1);
        return (Block) Block.blockRegistry.getObject(selection);
    }

    public static Item getItem(String itemName) {
        String selection = itemName;
        if (!selection.contains(":"))
            selection = ReferencesModInfo.MOD_ID + ":" + selection;
        if (selection.indexOf(":") == 0)
            selection = selection.substring(1);
        return (Item) Item.itemRegistry.getObject(selection);
    }

    public static Item getItemBlock(String blockName) {
        String selection = blockName;
        if (!selection.contains(":"))
            selection = ReferencesModInfo.MOD_ID + ":" + selection;
        if (selection.indexOf(":") == 0)
            selection = selection.substring(1);
        return Item.getItemFromBlock((Block) Block.blockRegistry.getObject(selection));
    }

}

