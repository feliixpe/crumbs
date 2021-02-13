package net.dodogang.crumbs.forge;

import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Function5;
import net.dodogang.ash.forge.ModEventBus;
import net.dodogang.crumbs.CrumbsCore;
import net.dodogang.crumbs.block.CrumbsBarrelBlock;
import net.dodogang.crumbs.forge.client.CrumbsClientForge;
import net.dodogang.crumbs.mixin.PointOfInterestTypeAccessor;
import net.dodogang.crumbs.platform.AbstractPlatform;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.poi.PointOfInterestType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;

@Mod(CrumbsCore.MOD_ID)
public class CrumbsForge implements AbstractPlatform {
    private static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_REGISTRY = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, CrumbsCore.MOD_ID);

    public CrumbsForge() {
        ModEventBus.registerModEventBus(CrumbsCore.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());
        CrumbsCore.init(this);
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> CrumbsClientForge::new);

        BLOCK_ENTITY_REGISTRY.register(FMLJavaModLoadingContext.get().getModEventBus());

        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
    }

    public void setup(FMLCommonSetupEvent event) {
        CrumbsCore.setup();
        registerPointsOfInterest();
    }

    public static void registerPointsOfInterest() {
        // Copied from PoiType#registerBlockStates
        // Edited to allow adding blocks to a Poi
        CrumbsBarrelBlock.MOD_BARRELS.forEach((barrel) -> {
            PointOfInterestType poiType = PointOfInterestTypeAccessor.getBlockStatePoiMap()
                    .put(barrel, PointOfInterestType.FISHERMAN);
            if (poiType != null) {
                throw Util.throwOrPause(new IllegalStateException(
                        String.format("%s is defined in too many tags", barrel)
                ));
            }
        });
        PointOfInterestTypeAccessor fishermanAccessor = (PointOfInterestTypeAccessor) PointOfInterestType.FISHERMAN;

        ArrayList<BlockState> blockStates = new ArrayList<>(fishermanAccessor.getBlockStates());
        blockStates.addAll(CrumbsBarrelBlock.MOD_BARRELS);
        fishermanAccessor.setBlockStates(ImmutableSet.copyOf(blockStates));
    }

    @Override
    public void registerOnRightClickBlockHandler(Function5<PlayerEntity, World, Hand, BlockPos, Direction, ActionResult> event) {
        MinecraftForge.EVENT_BUS.<PlayerInteractEvent.RightClickBlock>addListener(e -> {
            if (!e.getPlayer().isSpectator()) {
                e.setCancellationResult(event.apply(e.getPlayer(), e.getWorld(), e.getHand(), e.getPos(), e.getFace()));
            }
        });
    }

    @Override
    public boolean isAxe(ItemStack item) {
        return item.getItem().getToolTypes(item).contains(ToolType.AXE);
    }
}
