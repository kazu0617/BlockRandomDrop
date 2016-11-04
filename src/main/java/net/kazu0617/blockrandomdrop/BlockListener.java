/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.kazu0617.blockrandomdrop;

import java.io.IOException;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.block.BlockPlaceEvent;

/**
 *
 * @author kazu0617<kazuyagi19990617@hotmail.co.jp>
 */
class BlockListener implements Listener {
    Main plugin;
    
    public BlockListener(Main instance) {
        this.plugin = instance;
    }
    @EventHandler
    public void onBreak(BlockPlaceEvent e) throws IOException {
        Block B = e.getBlock();
        Location L = B.getLocation();
        if(plugin.DebugMode) plugin.cLog.Message(e.getPlayer(), "BlockPlaceEventに入りました");
        if( B.getType() == Material.STONE && 30 >= L.getBlockY() ) {
            //plugin.FileIO.BlockIO("s",L);
            e.setCancelled(true);
            plugin.cLog.Message(e.getPlayer(), "Y=30以下の場所では設置することが出来ません");
        }
    }
    public void onFloat(BlockFromToEvent e){
        if( Material.LAVA == e.getBlock().getType() && Material.WATER == e.getToBlock().getType()
                || Material.WATER == e.getBlock().getType() && Material.LAVA == e.getToBlock().getType()){
            e.isCancelled();
        }
    }
}
