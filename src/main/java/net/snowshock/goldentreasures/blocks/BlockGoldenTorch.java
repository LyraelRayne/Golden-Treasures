package net.snowshock.goldentreasures.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockTorch;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.snowshock.goldentreasures.GoldenTreasures;
import net.snowshock.goldentreasures.references.ReferencesConfigInfo;
import net.snowshock.goldentreasures.references.ReferencesModBlocks;
import net.snowshock.goldentreasures.references.ReferencesModInfo;

import java.util.List;
import java.util.Random;

public class BlockGoldenTorch extends BlockTorch {
    public BlockGoldenTorch() {
        super();
        this.setBlockName(ReferencesModBlocks.GOLDEN_TORCH);
        this.setBlockTextureName(ReferencesModBlocks.GOLDEN_TORCH);
        this.setCreativeTab(GoldenTreasures.CREATIVE_TAB);
        this.setHardness(0.0F);
        this.setLightLevel(1.0F);
        this.setTickRandomly(false);
        this.setStepSound(BlockTorch.soundTypeWood);
    }

    @Override
    public String getUnlocalizedName() {
        return String.format("tile.%s%s", ReferencesModInfo.MOD_ID + ":", getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister) {
        blockIcon = iconRegister.registerIcon(String.format("%s", getUnwrappedUnlocalizedName(this.getUnlocalizedName())));
    }

    protected String getUnwrappedUnlocalizedName(String unlocalizedName) {
        return unlocalizedName.substring(unlocalizedName.indexOf(".") + 1);
    }

    @Override
    public int onBlockPlaced(World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int meta) {
        world.scheduleBlockUpdate(x, y, z, this, tickRate());
        return super.onBlockPlaced(world, x, y, z, side, hitX, hitY, hitZ, meta);
    }

    @Override
    public void updateTick(World world, int x, int y, int z, Random random) {
        super.updateTick(world, x, y, z, random);
        world.scheduleBlockUpdate(x, y, z, this, tickRate());
        if (world.isRemote)
            return;
        int radius = ReferencesConfigInfo.GeneralConfigs.PUSH_RADIUS;

        //List<String> entitiesThatCanBePushed = (List<String>) ReferencesConfigInfo
        //List<String> projectilesThatCanBePushed = (List<String>) ReferencesConfigInfo.GeneralConfigs.get(ReferencesModBlocks.GOLDEN_TORCH, "projectiles_that_can_be_pushed");


        List<Entity> entities = (List<Entity>) world.getEntitiesWithinAABB(Entity.class, AxisAlignedBB.getBoundingBox(x - radius, y - radius, z - radius, x + radius, y + radius, z + radius));
        for (Entity entity : entities) {
            // TODO: Add a blacklist via config option.
            if (entity instanceof EntityPlayer)
                continue;
            Class entityClass = entity.getClass();
            String entityName = (String) EntityList.classToStringMapping.get(entityClass);
            if (ReferencesConfigInfo.GeneralConfigs.CAN_PROJECTILES_BE_PUSHED) {
                double distance = entity.getDistance((double) x, (double) y, (double) z);
                if (distance >= radius || distance == 0)
                    continue;

                // the multiplier is based on a set rate added to an inverse
                // proportion to the distance.
                // we raise the distance to 1 if it's less than one, or it becomes a
                // crazy multiplier we don't want/need.
                if (distance < 1D)
                    distance = 1D;
                double knockbackMultiplier = 1D + (1D / distance);

                // we also need a reduction coefficient because the above force is
                // WAY TOO MUCH to apply every tick.
                double reductionCoefficient = 0.04D;

                // the resultant vector between the two 3d coordinates is the
                // difference of each coordinate pair
                // note that we do not add 0.5 to the y coord, if we wanted to be
                // SUPER accurate, we would be using
                // the entity height offset to find its "center of mass"
                Vec3 angleOfAttack = Vec3.createVectorHelper(entity.posX - (x + 0.5D), entity.posY - y, entity.posZ - (z + 0.5D));

                // we use the resultant vector to determine the force to apply.
                double xForce = angleOfAttack.xCoord * knockbackMultiplier * reductionCoefficient;
                double yForce = angleOfAttack.yCoord * knockbackMultiplier * reductionCoefficient;
                double zForce = angleOfAttack.zCoord * knockbackMultiplier * reductionCoefficient;
                entity.motionX += xForce;
                entity.motionY += yForce;
                entity.motionZ += zForce;
            }
        }
    }

    public int tickRate() {
        return 1;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(World world, int x, int y, int z, Random random) {
        int orientation = world.getBlockMetadata(x, y, z);
        double xOffset = (double) ((float) x + 0.5F);
        double yOffset = (double) ((float) y + 0.7F);
        double zOffset = (double) ((float) z + 0.5F);
        double verticalModifier = 0.2199999988079071D;
        double horizontalModifier = 0.27000001072883606D;

        if (orientation == 1) {
            world.spawnParticle("mobSpell", xOffset - horizontalModifier, yOffset + verticalModifier, zOffset, 0.0D, 0.0D, 0.0D);
            world.spawnParticle("flame", xOffset - horizontalModifier, yOffset + verticalModifier, zOffset, 0.0D, 0.0D, 0.0D);
        } else if (orientation == 2) {
            world.spawnParticle("mobSpell", xOffset + horizontalModifier, yOffset + verticalModifier, zOffset, 0.0D, 0.0D, 0.0D);
            world.spawnParticle("flame", xOffset + horizontalModifier, yOffset + verticalModifier, zOffset, 0.0D, 0.0D, 0.0D);
        } else if (orientation == 3) {
            world.spawnParticle("mobSpell", xOffset, yOffset + verticalModifier, zOffset - horizontalModifier, 0.0D, 0.0D, 0.0D);
            world.spawnParticle("flame", xOffset, yOffset + verticalModifier, zOffset - horizontalModifier, 0.0D, 0.0D, 0.0D);
        } else if (orientation == 4) {
            world.spawnParticle("mobSpell", xOffset, yOffset + verticalModifier, zOffset + horizontalModifier, 0.0D, 0.0D, 0.0D);
            world.spawnParticle("flame", xOffset, yOffset + verticalModifier, zOffset + horizontalModifier, 0.0D, 0.0D, 0.0D);
        } else {
            world.spawnParticle("mobSpell", xOffset, yOffset, zOffset, 0.0D, 0.0D, 0.0D);
            world.spawnParticle("flame", xOffset, yOffset, zOffset, 0.0D, 0.0D, 0.0D);
        }
    }
}
