package net.kazu0617.blockrandomdrop;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Set;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

/**a
 * @author     kazu0617
 * @license    MIT
 * @copyright  Copyright kazu0617 2015
 */
class FileIO {
     Main plugin;
     public FileIO(Main instance)
     {
         this.plugin = instance;
     }
 
    public String HandIO(String Mode, String pname, String Hand) throws IOException {
        File file = new File(plugin.folder + "playerdata" + File.separator + pname + File.separator + "Hand.yml");
        FileConfiguration yaml = new YamlConfiguration();
        try {
            yaml.load(file);
        } catch (FileNotFoundException | InvalidConfigurationException ex) {
            plugin.cLog.info("該当するプレイヤーファイルがありませんでした。新規作成します");
            SettingFiles(yaml, file, true);
        }
        if("s".equalsIgnoreCase(Mode))
        {
            yaml.set("hand", Hand);
            SettingFiles(yaml, file, true);
            return null;
        }
        else if("r".equalsIgnoreCase(Mode))
        {
            Hand = yaml.getString("hand");
            return Hand;
        }
        return "err";
    }
/*    public String BlockIO(String Mode,Location L) throws IOException {
        File file = new File(plugin.folder + "BlockData" + File.separator + "Stone.yml");
        FileConfiguration yaml = new YamlConfiguration();
        try {
            yaml.load(file);
        } catch (FileNotFoundException | InvalidConfigurationException ex) {
            plugin.cLog.info("該当するブロックファイルがありませんでした。新規作成します");
            SettingFiles(yaml, file, true);
        }
        if("s".equalsIgnoreCase(Mode))
        {
            List<String> S = yaml.getStringList("Location");
            int S_Size = S.size();
            yaml.set("Location."+S_Size, L);
            SettingFiles(yaml, file, true);
            return null;
        }
        else if("r".equalsIgnoreCase(Mode))
        {
            List<String> S = yaml.getStringList("Location");
            return null;
        }
        return "err";
    }*/

    public HashMap ItemIO(String Mode, HashMap<String,Object> SaveDropItem) throws IOException {
        File file = new File(plugin.folder + "DropItem.yml");
        FileConfiguration yaml = new YamlConfiguration();

        try {
            yaml.load(file);
        } catch (FileNotFoundException | InvalidConfigurationException ex) {
            plugin.cLog.info("該当するプレイヤーファイルがありませんでした。新規作成します");
            SettingFiles(yaml, file, true);
        }
        if("s".equalsIgnoreCase(Mode)) {
            Set<String> DropItemConfigName = SaveDropItem.keySet();
            for(String str : DropItemConfigName) {
                ConfigurationSection yamltmp = yaml.getConfigurationSection(str);
                for(String str2 : ((Set<String>) SaveDropItem.get(str))) {
                    HashMap<String, Object> SaveDropItemConfig = new HashMap<>();
                    yaml.set( str+ "."+ str2,( (Set<String>) ( (Set<String>) SaveDropItem.get(str) ).get(str2) ));
                }
            }
            SettingFiles(yaml, file, true);
        }
        else if("r".equalsIgnoreCase(Mode)) {
            HashMap<String, HashMap> DropItemList = new HashMap<>();
            Set<String> DropItemConfigName = yaml.getKeys(false);
            for (String str : DropItemConfigName) {
                ConfigurationSection yamltmp = yaml.getConfigurationSection(str);
                HashMap<String, Object> tmpItem = new HashMap<>();
                tmpItem.putAll(yamltmp.getValues(true));
                DropItemList.put(str, tmpItem);
            }
            return DropItemList;
        }
        return null;
    }
    public boolean PersentIO(String Mode) throws IOException {
        File file = new File(plugin.folder + "blockdata.yml");
        FileConfiguration yaml = new YamlConfiguration();
        try {
            yaml.load(file);
        } catch (FileNotFoundException | InvalidConfigurationException ex) {
            plugin.cLog.info("該当するファイルがありませんでした。標準のを新規作成します");
            SettingFiles(yaml, file, true);
            Mode = "s";
        }
        if("s".equalsIgnoreCase(Mode))
        {
            for(int i = 0 ; i < plugin.BreakListener.Item_S.length ; i++){
                yaml.set(plugin.BreakListener.Item_S[i]+".Item_persent", plugin.BreakListener.Item_persent[i] );
                //yaml.set(plugin.BreakListener.Item_S[i]+".BlockHeight", plugin.BreakListener.BlockHeight[i] );
            }
            SettingFiles(yaml, file, true);
            plugin.cLog.info("セーブ完了しました");
            return true;
        }
        else if("r".equalsIgnoreCase(Mode))
        {
            for(int i = 0 ; i < plugin.BreakListener.Item_S.length ; i++){
                plugin.BreakListener.Item_old[i] = (ItemStack) yaml.get(plugin.BreakListener.Item_S[i]+".Item_persent");
                plugin.BreakListener.Item_persent[i] = (int) yaml.get(plugin.BreakListener.Item_S[i]+".Item_times");
                plugin.BreakListener.Item_CanBreak[i] = (int) yaml.get(plugin.BreakListener.Item_S[i]+".Item_CanBreak");
                //plugin.BreakListener.BlockHeight[i] = (int) yaml.get(plugin.BreakListener.Item_S[i]+".BlockHeight");
            }
            plugin.cLog.info("ロード完了しました");
            return true;
        }
        return true;
    }
 
    /**
     * ファイルの保存
     *
     * 新規作成だけで、保存しない場合はsaveをfalseに
     * 保存&新規作成の場合はtrueでもfalseでも
     * 上書きの時は必ずtrueに
     *
     * @param fileconfiguration ファイルコンフィグを指定
     * @param file ファイル指定
     * @param save 上書きをするかリセットするか(trueで上書き)
     */
    public void SettingFiles(FileConfiguration fileconfiguration, File file, boolean save)
    {
        if(!file.exists() || save)
        {
            try {
                fileconfiguration.save(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
