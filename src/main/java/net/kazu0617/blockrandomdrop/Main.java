package net.kazu0617.blockrandomdrop;

import java.io.File;
import java.io.IOException;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author     kazu0617
 * @license    MIT
 * @copyright  Copyright kazu0617 2015
 */
public class Main extends JavaPlugin{
    
    public String Pluginname = getDescription().getName();
    public String Pluginprefix = "[" + ChatColor.GREEN + Pluginname + ChatColor.RESET +"] ";
    //public Location[] L_DB = new Location();
    public BreakListener BreakListener = new BreakListener(this);
    public BlockListener BlockListener = new BlockListener(this);
    public ConsoleLog cLog = new ConsoleLog(this);
    public FileIO FileIO = new FileIO(this);
    public boolean DebugMode = false;
    String folder = getDataFolder() + File.separator;
    
    @Override
    public void onEnable()
    {
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(this.BreakListener, this);
        pm.registerEvents(this.BlockListener, this);
        cLog.info("DebugMode is now ["+DebugMode+"].");
        try {
            FileIO.PersentIO("r");
        } catch (IOException ex) {
            cLog.debug("初期化時にrモードでの読み込みに失敗");
        }
        
    }
    @Override
    public void onDisable()
    {
        
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if((args.length >= 1) && ("DebugMode").startsWith(args[0]) && p.hasPermission("blockrandomdrop.debug"))
            {
                if(DebugMode) DebugMode = false;
                else if(!DebugMode) DebugMode = true;
                cLog.BroadCast("DebugModeが"+ DebugMode +"に変更されました");
                return true;
            }
            else if((args.length >= 1) && ("export").equalsIgnoreCase(args[0]))
            {
                if((args.length >= 2) && ("blocklist").equalsIgnoreCase(args[1]))
                {
                    if(!DebugMode)
                    {
                        cLog.Message(p, "このコマンドはDebugMode専用のコマンドです。");
                        return true;
                    }
                    try {
                        FileIO.PersentIO("s");
                        cLog.Message(p, "ブロック確率データ保存に成功");
                    } catch (IOException ex) {
                        cLog.debug("ブロック確率データ保存に失敗");
                    }
                    return true;
                }
                cLog.Message(p, "このコマンドはただ今実験段階です");
                return true;
            }
        }
        else
        {
            if((args.length >= 1) && ("DebugMode").startsWith(args[0]))
            {
                if(DebugMode) DebugMode = false;
                else if(!DebugMode) DebugMode = true;
                cLog.BroadCast("DebugModeが"+ DebugMode +"に変更されました");
                return true;
            }
        }

        return true;
    }    
}
