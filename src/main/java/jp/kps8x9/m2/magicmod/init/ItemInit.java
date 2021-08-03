package jp.kps8x9.m2.magicmod.init;

import jp.kps8x9.m2.magicmod.MagicMod;
import jp.kps8x9.m2.magicmod.items.MagicWand;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemInit {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MagicMod.MOD_ID);

    public static RegistryObject<MagicWand> magicWand = ITEMS.register("magic_wand",() -> {
        return new MagicWand(new Item.Properties().tab(ItemGroup.TAB_COMBAT));
    });

}
