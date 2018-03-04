package me.quexer.lobbysystem.gadgets.utils;

import me.quexer.serverapi.api.ItemBuilder;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public enum Gadget {

    //Name,Seltenheit,ItemStack,Particle,ID
    HERZEN("§4Herzen", Seltenheit.LEGENDARY, new ItemBuilder(new ItemStack(Material.INK_SACK, 1, (short) 1)).setName("§8➤ §4Herzen").toItemStack(), Effect.HEART, 1),
    FLAMES("§6Flames", Seltenheit.LEGENDARY, new ItemBuilder(Material.MOB_SPAWNER).setName("§8➤ §6Flames").toItemStack(),Effect.MOBSPAWNER_FLAMES, 2),
    NOTES("§aNotes", Seltenheit.LEGENDARY, new ItemBuilder(Material.NOTE_BLOCK).setName("§8➤ §aNotes").toItemStack(),Effect.NOTE, 3),
    COINS1("§e50§7.§e000", Seltenheit.LEGENDARY, new ItemBuilder(Material.DIAMOND).setName("§8➤ §e50§7.§e000").toItemStack(),null, null),



    SMOKE("§8Smoke", Seltenheit.EPIC, new ItemBuilder(Material.SULPHUR).setName("§8➤ §8Smoke").toItemStack(),Effect.SMOKE, 4),
    CLOUD("§9Cloud", Seltenheit.EPIC, new ItemBuilder(Material.WOOL).setName("§8➤ §9Cloud").toItemStack(),Effect.CLOUD, 5),
    LAVA("§cLava", Seltenheit.EPIC, new ItemBuilder(Material.LAVA_BUCKET).setName("§8➤ §cLava").toItemStack(),Effect.LAVA_POP, 6),
    COINS2("§e10§7.§e000", Seltenheit.EPIC, new ItemBuilder(Material.GOLD_BLOCK).setName("§8➤ §e10§7.§e000").toItemStack(),null, null),

    THUNDER("§3Thunder", Seltenheit.RARE, new ItemBuilder(Material.COAL).setName("§8➤ §3Thunder").toItemStack(),Effect.VILLAGER_THUNDERCLOUD, 7),
    RAINBOW("§5Rainbow", Seltenheit.RARE, new ItemBuilder(Material.EXP_BOTTLE).setName("§8➤ §5Rainbow").toItemStack(),Effect.COLOURED_DUST, 8),
    ENDER("§dEnder", Seltenheit.RARE, new ItemBuilder(Material.EYE_OF_ENDER).setName("§8➤ §dEnder").toItemStack(),Effect.ENDER_SIGNAL, 9),
    COINS3("§e5§7.§e000", Seltenheit.RARE, new ItemBuilder(Material.GOLD_INGOT).setName("§8➤ §e5§7.§e000").toItemStack(),null, null),


    MAGIC("§5Magic", Seltenheit.COMMON, new ItemBuilder(Material.POTION).setName("§8➤ §5Magic").toItemStack(),Effect.WITCH_MAGIC, 10),
    CIRT("§bCrit", Seltenheit.COMMON, new ItemBuilder(Material.DIAMOND_SWORD).setName("§8➤ §bCrit").toItemStack(),Effect.CRIT, 11),
    BOWFIRE("§4Bow Fire", Seltenheit.COMMON, new ItemBuilder(Material.BOW).setName("§8➤ §4Bow Fire").toItemStack(),Effect.BOW_FIRE, 12),
    COINS4("§e2§7.§e000", Seltenheit.COMMON, new ItemBuilder(Material.GOLD_NUGGET).setName("§8➤ §e2§7.§e000").toItemStack(),null, null);

    private String name;
    private Seltenheit seltenheit;
    private ItemStack itemStack;
    private Effect effect;
    private Integer id;

    Gadget(String name, Seltenheit seltenheit, ItemStack itemStack, Effect effect, Integer id) {
        this.name = name;
        this.seltenheit = seltenheit;
        this.itemStack = itemStack;
        this.effect = effect;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Seltenheit getSeltenheit() {
        return seltenheit;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public Effect getEffect() {
        return effect;
    }

    public Integer getId() {
        return id;
    }
}
