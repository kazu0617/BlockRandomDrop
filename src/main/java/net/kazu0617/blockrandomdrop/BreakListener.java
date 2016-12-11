package net.kazu0617.blockrandomdrop;

import java.io.IOException;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author kazu0617<kazuyagi19990617@hotmail.co.jp>
 */
class BreakListener implements Listener{
    Main plugin;
    
    public BreakListener(Main instance)
    {
        this.plugin = instance;
    }
    
    ItemStack[] Item = {new ItemStack(Material.COAL_ORE, 1),new ItemStack(Material.IRON_ORE,1),new ItemStack(Material.REDSTONE_ORE, 1),
        new ItemStack(Material.GOLD_ORE, 1),new ItemStack(Material.EMERALD_ORE, 1),new ItemStack(Material.LAPIS_ORE, 1),new ItemStack(Material.DIAMOND_ORE, 1)};
    ItemStack[] CanBreak = {new ItemStack(Material.WOOD_PICKAXE),new ItemStack(Material.STONE_PICKAXE),new ItemStack(Material.IRON_PICKAXE),
        new ItemStack(Material.GOLD_PICKAXE),new ItemStack(Material.DIAMOND_PICKAXE)};
    String[] Item_S = {"Coal","Iron","RedStone","Gold","Emerald","Lapis","Diamond"};
    int[] Item_persent = {50,70,75,90,100,150,200};//100を指定した場合は、0~999の間で読まれる…？(元々が0.12426454…という感じなので124.26454…と言った感じに)
    int[] Item_times = {3,5,5,10,10,15,15,20};
    int[] Item_CanBreak = {0,1,2,2,1,1,2};
    //int[] BlockHeight = {20,20,20,20,20,20,20};
    @EventHandler
    public void onBreak(BlockBreakEvent e)
    {

        //if (plugin.DebugMode = true) plugin.cLog.debug(e.getPlayer()+"がBlockBreakEventに入りました");
        Player p = e.getPlayer();
        Location L = e.getBlock().getLocation();
        Material[] phand_M = {p.getInventory().getItemInMainHand().getType(),p.getInventory().getItemInOffHand().getType()};

        int phand_I = -1;
        if (GameMode.CREATIVE !=e.getPlayer().getGameMode())
        {
            Material B = e.getBlock().getType();
            for(int i =0;i<CanBreak.length;i++)
            {
                if(phand_M[0]==CanBreak[i].getType()) {
                    phand_I=i;
                    plugin.cLog.Message(p, p+".phand_I = "+phand_I);
                    break;
                }
            }
            if(phand_I==-1) return;
            if( B == Material.STONE && L.getY() <= 30 )
            {
                for(int i=0;i<Item_S.length;i++)
                {
                    if(phand_I<Item_CanBreak[i]) continue;
                    int r = (int) (Math.random() * Item_persent[i]);
                    if (plugin.DebugMode) {
                        plugin.cLog.Message(p, p+"."+Item_S[i]+".Math.random = " + r);
                        plugin.cLog.debug(p+"."+Item_S[i]+".Math.random = " + r);
                    }
                    if (r == 0) {
                        try {
                            if (plugin.FileIO.BreakStoneIO("r", p.getName(), Item_S[i], Item_times[i])) {
                                e.getPlayer().getWorld().dropItemNaturally(L, Item[i]);
                                if (plugin.DebugMode) {
                                    plugin.cLog.debug("Location = " + L);
                                    plugin.cLog.debug("Player = " + p);
                                }
                            }
                        } catch (IOException ex) {
                            plugin.cLog.debug(e.getPlayer() + "のファイルの読み込みに失敗");
                        }
                        try {
                            plugin.FileIO.BreakStoneIO("s", p.getName(), Item_S[i], Item_times[i]);
                        } catch (IOException ex) {
                            plugin.cLog.debug(e.getPlayer() + "のファイルの書き込みに失敗");
                        }
                    }
                }
                if(plugin.DebugMode) plugin.cLog.Message(p, "---");
            }
        }


    }
    
}
