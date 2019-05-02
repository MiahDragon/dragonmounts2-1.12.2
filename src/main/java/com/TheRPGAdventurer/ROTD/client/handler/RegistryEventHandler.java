package com.TheRPGAdventurer.ROTD.client.handler;

import com.TheRPGAdventurer.ROTD.DragonMounts;
import com.TheRPGAdventurer.ROTD.client.gui.GuiHandler;
import com.TheRPGAdventurer.ROTD.client.render.TileEntityDragonShulkerRenderer;
import com.TheRPGAdventurer.ROTD.server.blocks.BlockDragonBreedEgg;
import com.TheRPGAdventurer.ROTD.server.blocks.tileentities.TileEntityDragonShulker;
import com.TheRPGAdventurer.ROTD.server.blocks.tileentities.TileEntityHandler;
import com.TheRPGAdventurer.ROTD.server.initialization.ModArmour;
import com.TheRPGAdventurer.ROTD.server.initialization.ModBlocks;
import com.TheRPGAdventurer.ROTD.server.initialization.ModItems;
import com.TheRPGAdventurer.ROTD.server.initialization.ModTools;
import com.TheRPGAdventurer.ROTD.server.items.ItemDragonBreedEgg;
import com.TheRPGAdventurer.ROTD.server.util.IHasModel;
import com.TheRPGAdventurer.ROTD.server.entity.breeds.EnumDragonBreed;
import com.TheRPGAdventurer.ROTD.util.DMUtils;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

@Mod.EventBusSubscriber
public class RegistryEventHandler {
    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        event.getRegistry().registerAll(ModBlocks.BLOCKS.toArray(new Block[0]));
        TileEntityHandler.registerTileEntities();
        DMUtils.getLogger().info("Block Registries Successfully Registered");
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        event.getRegistry().registerAll(ModItems.ITEMS.toArray(new Item[0]));
        event.getRegistry().registerAll(ModTools.BOWS.toArray(new Item[0]));
        event.getRegistry().registerAll(ModTools.TOOLS.toArray(new Item[0]));
        event.getRegistry().registerAll(ModArmour.ARMOR);

        for (Block block : ModBlocks.BLOCKS) {
            event.getRegistry().register(new ItemBlock(block).setRegistryName(block.getRegistryName()));
        }

        DMUtils.getLogger().info("Item Registries Successfully Registered!");
    }

    @SubscribeEvent
    public static void registerDragonEggItem(RegistryEvent.Register<Item> event) {
        event.getRegistry().register(ItemDragonBreedEgg.DRAGON_BREED_EGG.setRegistryName("dragon_egg"));
    }

    @SubscribeEvent
    public static void registerDragonnEggBlock(RegistryEvent.Register<Block> event) {
        event.getRegistry().register(BlockDragonBreedEgg.DRAGON_BREED_EGG.setRegistryName("dragon_egg"));
    }

    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event) {
        DragonMounts.proxy.registerModel(Item.getItemFromBlock(ModBlocks.dragonshulker), 0);


        for (Block block : ModBlocks.BLOCKS) {
            ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0, new ModelResourceLocation(block.getRegistryName(), "inventory"));
        }

        for (Item item : ModItems.ITEMS) {
            if (item instanceof IHasModel) {
                ((IHasModel) item).RegisterModels();
            }
        }

        for (Item item : ModTools.BOWS) {
            if (item instanceof IHasModel) {
                ((IHasModel) item).RegisterModels();
            }
        }
        for (Item item : ModTools.TOOLS) {
            ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName().toString(), "inventory"));
        }

        for (Item item : ModArmour.ARMOR) {
            ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName().toString(), "inventory"));
        }

        for (Item itemegg : ItemDragonBreedEgg.ITEM_EGG) {
            // register item renderer for dragon egg block variants
            ResourceLocation eggModelItemLoc = new ResourceLocation(DragonMounts.MODID, "dragon_egg");
            Item itemBlockDragonEgg = Item.REGISTRY.getObject(eggModelItemLoc);
            EnumDragonBreed.META_MAPPING.forEach((breed, meta) -> {
                ModelResourceLocation eggModelLoc = new ModelResourceLocation(DragonMounts.MODID + ":dragon_egg", "breed=" + breed.getName());
                ModelLoader.setCustomModelResourceLocation(itemBlockDragonEgg, meta, eggModelLoc);
            });
        }

        for (Block blockegg : BlockDragonBreedEgg.BLOCK_EGG) {
            // register item renderer for dragon egg block variants
            ResourceLocation eggModelItemLoc = new ResourceLocation(DragonMounts.MODID, "dragon_egg");
            Item itemBlockDragonEgg = Item.REGISTRY.getObject(eggModelItemLoc);
            EnumDragonBreed.META_MAPPING.forEach((breed, meta) -> {
                ModelResourceLocation eggModelLoc = new ModelResourceLocation(DragonMounts.MODID + ":dragon_egg", "breed=" + breed.getName());
                ModelLoader.setCustomModelResourceLocation(itemBlockDragonEgg, meta, eggModelLoc);
            });
        }

        DMUtils.getLogger().info("Models Sucessfully Registered");
    }

    public static void preInitRegistries() {
    }

    public static void initRegistries() {
        NetworkRegistry.INSTANCE.registerGuiHandler(DragonMounts.instance, new GuiHandler());
        DMUtils.getLogger().info("Gui's Successfully Registered");
    }
}