package net.snowshock.goldentreasures.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.snowshock.goldentreasures.GoldenTreasures;
import net.snowshock.goldentreasures.items.IHeldBlockAction;
import net.snowshock.goldentreasures.references.ReferencesModInfo;
import net.snowshock.goldentreasures.utils.LanguageHelper;

public class BlockGoldenTreasures extends Block implements IHeldBlockAction {

    //defaults to only showing the tooltip when shift is pressed. you can override this behavior at the item level by setting the item's showTooltipsAlways bool to true.
    private boolean showTooltipsAlways = false;

    public BlockGoldenTreasures(Material material) {
        super(material);
        this.setCreativeTab(GoldenTreasures.CREATIVE_TAB);
    }

    @Override
    public String getUnlocalizedName() {
        return String.format("tile.%s%s", ReferencesModInfo.MOD_ID + ":", unwrapUnlocalizedName(super.getUnlocalizedName()));
    }

    private String unwrapUnlocalizedName(String unlocalizedName) {
        return LanguageHelper.unwrapUnlocalizedName(unlocalizedName);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister) {
        blockIcon = iconRegister.registerIcon(String.format("%s", unwrapUnlocalizedName(this.getUnlocalizedName())));
    }

    public void doHeldItemUpdate(ItemStack ist, World world, Entity entity, int i) {
        // Do nothing by default.
    }
}
