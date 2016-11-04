/*
 * @author     kazu0617
 * @license    LGPLv3
 * @copyright  Copyright kazu0617 2015
 */
package net.kazu0617.blockrandomdrop;

import java.util.logging.Logger;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ConsoleLog
{
    @SuppressWarnings("unused")
    private Main plugin;
    public Logger log = Logger.getLogger("Minecraft");
    public String cPrefix = "[BlockRandomDrop] ";

    public ConsoleLog(Main plugin)
    {
        this.plugin = plugin;
    }

    public void info(String Mess)
    {
        log.info(this.cPrefix + Mess);
    }
    public void debug(String Mess)
    {
        log.info(this.cPrefix + "[Debug] " +Mess);
    }

    public void warn(String Mess)
    {
        log.warning(this.cPrefix + Mess);
    }
    public void Message(CommandSender sender, String Text)
    {
        sender.sendMessage(plugin.Pluginprefix + Text);
        return;
    }
    public void Message(Player p, String Text)
    {
        p.sendMessage(plugin.Pluginprefix + Text);
        return;
    }
    public void BroadCast(String Text)
    {
        plugin.getServer().broadcastMessage(plugin.Pluginprefix + Text);
        return;
    }
}
