package net.dodogang.crumbs.init;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.item.AxeItem;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.RegistryObject;

public class BundledLogsBlocks {
    public static final RegistryObject<Block> OAK = create("oak", Blocks.OAK_LOG);
    public static final RegistryObject<Block> BIRCH = create("birch", Blocks.BIRCH_LOG);
    public static final RegistryObject<Block> SPRUCE = create("spruce", Blocks.SPRUCE_LOG);
    public static final RegistryObject<Block> JUNGLE = create("jungle", Blocks.JUNGLE_LOG);
    public static final RegistryObject<Block> ACACIA = create("acacia", Blocks.ACACIA_LOG);
    public static final RegistryObject<Block> DARK_OAK = create("dark_oak", Blocks.DARK_OAK_LOG);
//    public static final RegistryObject<Block> CRIMSON = create("crimson", Blocks.CRIMSON_LOG);
//    public static final RegistryObject<Block> WARPED = create("warped", Blocks.WARPED_LOG);

    public static final RegistryObject<Block> STRIPPED_OAK = create("stripped_oak", Blocks.STRIPPED_OAK_LOG);
    public static final RegistryObject<Block> STRIPPED_BIRCH = create("stripped_birch", Blocks.STRIPPED_BIRCH_LOG);
    public static final RegistryObject<Block> STRIPPED_SPRUCE = create("stripped_spruce", Blocks.STRIPPED_SPRUCE_LOG);
    public static final RegistryObject<Block> STRIPPED_JUNGLE = create("stripped_jungle", Blocks.STRIPPED_JUNGLE_LOG);
    public static final RegistryObject<Block> STRIPPED_ACACIA = create("stripped_acacia", Blocks.STRIPPED_ACACIA_LOG);
    public static final RegistryObject<Block> STRIPPED_DARK_OAK = create("stripped_dark_oak", Blocks.STRIPPED_DARK_OAK_LOG);
//    public static final RegistryObject<Block> STRIPPED_CRIMSON = create("stripped_crimson", Blocks.STRIPPED_CRIMSON_LOG);
//    public static final RegistryObject<Block> STRIPPED_WARPED = create("stripped_warped", Blocks.STRIPPED_WARPED_LOG);

    public BundledLogsBlocks() {};

    private static RegistryObject<Block> create(String adjective, Block from) {
        return CRegistry.registerBlock(
                "bundled_" + adjective + "_logs",
                new RotatedPillarBlock(Block.Properties.from(from))
        );
    }

    public static void stripLogsOnRightClick(PlayerInteractEvent.RightClickBlock event) {
        if (!(event.getItemStack().getItem() instanceof AxeItem)) return;

        // TODO: Logs need to be strip-able
        System.out.println("Hello");
    }
}
