package com.romimoco.ores.Items;

import com.romimoco.ores.Ores;
import com.romimoco.ores.blocks.BaseOre;
import com.romimoco.ores.util.*;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelDynBucket;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.*;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.ItemHandlerHelper;

import javax.annotation.Nullable;

public class BaseBucket extends UniversalBucket implements IColoredItem, IHasCustomModel{

    private int color;
    public String name;
    private final ItemStack empty = new ItemStack(this);

    public BaseBucket(BaseOre b){
        super(Fluid.BUCKET_VOLUME, ItemStack.EMPTY, true);
        this.name = b.name;
        this.color = b.getColor();
        this.setUnlocalizedName(Ores.MODID + ":bucket" + b.name);
        this.setCreativeTab(CreativeTabs.MISC);
        this.setRegistryName(Ores.MODID, "bucket"+name);

        OreLogger.localize(this.getUnlocalizedName() + ".name=" + b.name.substring(0,1).toUpperCase() + b.name.substring(1) + " Bucket");
    }

    @Override
    public void getSubItems(@Nullable final CreativeTabs tab, final NonNullList<ItemStack> subItems){
        if(this.isInCreativeTab(tab)){
            subItems.add(empty);

            for(Fluid fluid : FluidRegistry.getRegisteredFluids().values()){
                 // Add all fluids that the bucket can be filled with
                final FluidStack fs = new FluidStack(fluid, getCapacity());
                final ItemStack stack = new ItemStack(this);
                final IFluidHandlerItem fluidHandler = stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null);
                if (fluidHandler != null && fluidHandler.fill(fs, true) == fs.amount) {
                    final ItemStack filled = fluidHandler.getContainer();
                    subItems.add(filled);
                }
            }
        }
    }


    //credit to Choonster and TestMod3 for the majority of the bucket code in this file
    @Override
 	public ActionResult<ItemStack> onItemRightClick(final World world, final EntityPlayer player, final EnumHand hand) {
 		final ItemStack heldItem = player.getHeldItem(hand);
 		final FluidStack fluidStack = getFluid(heldItem);

 		// If the bucket is full, call the super method to try and empty it
 		if (fluidStack != null) return super.onItemRightClick(world, player, hand);

 		// If the bucket is empty, try and fill it
 		final RayTraceResult target = this.rayTrace(world, player, true);

 		if (target == null || target.typeOfHit != RayTraceResult.Type.BLOCK) {
 			return new ActionResult<>(EnumActionResult.PASS, heldItem);
 		}

 		final BlockPos pos = target.getBlockPos();

 		final ItemStack singleBucket = heldItem.copy();
 		singleBucket.setCount(1);

 		final FluidActionResult filledResult = FluidUtil.tryPickUpFluid(singleBucket, player, world, pos, target.sideHit);
 		if (filledResult.isSuccess()) {
 			final ItemStack filledBucket = filledResult.result;

 			if (player.capabilities.isCreativeMode)
 				return new ActionResult<>(EnumActionResult.SUCCESS, heldItem);

 			heldItem.shrink(1);
 			if (heldItem.isEmpty())
 				return new ActionResult<>(EnumActionResult.SUCCESS, filledBucket);

 			ItemHandlerHelper.giveItemToPlayer(player, filledBucket);

 			return new ActionResult<>(EnumActionResult.SUCCESS, heldItem);
 		}

 		return new ActionResult<>(EnumActionResult.PASS, heldItem);
 	}

    @Override
    public String getItemStackDisplayName(final ItemStack stack){
        if(OreConfig.requireResourcePack) {
            return net.minecraft.client.resources.I18n.format(this.getUnlocalizedNameInefficiently(stack));
        }
        return Ores.proxy.langs.translate(this.getUnlocalizedNameInefficiently(stack) + ".name").trim();
    }

    @Override
    public ItemStack getEmpty(){
        return empty;
    }

    @Override
    public FluidStack getFluid(ItemStack container){
        return FluidUtil.getFluidContained(container);
    }

    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, NBTTagCompound tag){
        return new FluidHandlerBaseBucket(stack, getCapacity());
    }


    @SideOnly(Side.CLIENT)
    public void initModel(){
        //ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(  "forge:bucket"));
        ModelLoader.setCustomMeshDefinition(this, stack -> ModelDynBucket.LOCATION);
        ModelBakery.registerItemVariants(this, ModelDynBucket.LOCATION);
    }

    public int getColor() {
        return color;
    }

}
