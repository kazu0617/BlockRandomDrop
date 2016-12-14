package net.kazu0617.blockrandomdrop;

import com.google.common.collect.Sets;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

/**
 * @author     kazu0617
 * @license    MIT
 * @copyright  Copyright kazu0617 2015
 */
class BreakListener implements Listener{
    Main plugin;
    
    public BreakListener(Main instance)
    {
        this.plugin = instance;
    }
    HashSet<Material> ItemDrop = new HashSet<>();
    HashSet<Material> drop_wood = Sets.newHashSet(Material.COAL_ORE);
    HashSet<Material> drop_stone = Sets.newHashSet(Material.IRON_ORE, Material.REDSTONE_ORE, Material.EMERALD_ORE);
    HashSet<Material> drop_iron ;
    HashMap<ItemStack,List> Item = new HashMap<>();
    ItemStack[] Item_old = {new ItemStack(Material.COAL_ORE, 1),new ItemStack(Material.IRON_ORE,1),new ItemStack(Material.REDSTONE_ORE, 1),
        new ItemStack(Material.GOLD_ORE, 1),new ItemStack(Material.EMERALD_ORE, 1),new ItemStack(Material.LAPIS_ORE, 1),new ItemStack(Material.DIAMOND_ORE, 1)};
    String[] Item_S = {"Coal","Iron","RedStone","Gold","Emerald","Lapis","Diamond"};
    int[] Item_persent = {50,70,75,90,100,150,200};//100を指定した場合は、0~999の間で読まれる…？(元々が0.12426454…という感じなので124.26454…と言った感じに)
    //int[] BlockHeight = {20,20,20,20,20,20,20};
    @EventHandler
    public void onBreak(BlockBreakEvent e)
    {
        //Todo 言ってみればピッケルの中にこのピッケルも使えるというか、このピッケルの中にこのピッケルの使用も入ってるみたいにすればEnumにまとめられる？
        //Todo 設定として、swichで作ることになりそうなので"上位互換ほど出やすくなる"ってことも可能になりそう。かめぷらぐいんをみる。
        /*Todo https://cdn.discordapp.com/attachments/190496450759753729/257914442535272448/cabf51f9e3280f80.PNG
        https://cdn.discordapp.com/attachments/190496450759753729/258287253351170048/7b2a20eb91ec26cb.PNG
        https://cdn.discordapp.com/attachments/190496450759753729/257926095389589504/74af8c49be7b3182.PNG
        */
        //Todo http://javatechnology.net/java/enum/
        Player p = e.getPlayer();
        Location L = e.getBlock().getLocation();
        ItemStack[] phand_M = {p.getInventory().getItemInMainHand(),p.getInventory().getItemInOffHand()};
        
        if (GameMode.CREATIVE == e.getPlayer().getGameMode()) return;
        if(e.getBlock().getType()!= Material.STONE) return;
        switch(phand_M[0].getType())
        {
            case WOOD_PICKAXE:
                ItemDrop.add(Material.COAL_ORE);
            case STONE_PICKAXE:
                ItemDrop.add(Material.IRON_ORE);
            case IRON_PICKAXE: 
                ItemDrop.add(Material.REDSTONE_ORE);
                ItemDrop.add(Material.LAPIS_ORE);
                ItemDrop.add(Material.LAPIS_ORE);
                ItemDrop.add(Material.DIAMOND_ORE);
            case GOLD_PICKAXE: 
            case DIAMOND_PICKAXE: break;
            default: return;
        }
        if (L.getY() <= 30) {
            for (int i = 0; i < Item_S.length; i++) {
                if (!(ItemDrop.contains(Item_old[i].getType()))) continue; 
                int r = (int) ((Math.random()%0.1) * Item_persent[i]);
                if (plugin.DebugMode) {
                    plugin.cLog.Message(p, p + "." + Item_S[i] + ".Math.random = " + r);
                    plugin.cLog.debug(p + "." + Item_S[i] + ".Math.random = " + r);
                }
                if (r != 0) return;
                e.getPlayer().getWorld().dropItemNaturally(L, Item_old[i]);
                if (plugin.DebugMode) {
                    plugin.cLog.debug("Location = " + L);
                    plugin.cLog.debug("Player = " + p);
                }
            }
        }
    }

}
