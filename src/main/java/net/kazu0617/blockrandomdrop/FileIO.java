package net.kazu0617.blockrandomdrop;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
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

    public boolean BreakStoneIO(String Mode, String pname, String Block, int Item_times) throws IOException {
        File file = new File(plugin.folder + "playerdata" + File.separator + pname + File.separator + "BreakStone.yml");
        FileConfiguration yaml = new YamlConfiguration();
        int BStone;
        /*class Item{
            String ItemName = "";
            double ItemPersent = 0;
            int ItemTimes = 0;
            int BreakItem = 0;
            int BreakMinHeight = 20;
        }*/
        //Item[] BreakBlock = new Item[20];
        //for(int Num = 0; Num < BreakBlock.length; Num++ ){
        //    BreakBlock[Num] = new Item();
        //}
        //String[] Item_S = {"Coal","Iron","RedStone","Gold","Emerald","Lapis","Diamond"};
        //int[] Item_persent = {50,70,75,90,100,150,200};//100を指定した場合は、0~999の間で読まれる…？(元々が0.12426454…という感じなので124.26454…と言った感じに)
        //int[] Item_times = {3,5,5,10,10,15,15,20};
        //int[] Item_CanBreak = {0,1,2,2,1,1,2};
        //int[] BlockHeight = {20,20,20,20,20,20,20};
        try {
            yaml.load(file);
        } catch (FileNotFoundException | InvalidConfigurationException ex) {
            plugin.cLog.info("該当するプレイヤーファイルがありませんでした。新規作成します");
            SettingFiles(yaml, file, true);
        }
        if("s".equalsIgnoreCase(Mode))
        {
            if("all".equalsIgnoreCase(Block)){
                return false;
            }
            BStone = yaml.getInt(Block);
            BStone++;
            yaml.set(Block, BStone );
            if(plugin.DebugMode) plugin.cLog.debug("Save.BStone = " + BStone );
            SettingFiles(yaml, file, true);
            return true;
        }
        else if("r".equalsIgnoreCase(Mode))
        {
            BStone = yaml.getInt(Block);
            if(plugin.DebugMode) plugin.cLog.debug("Load.BStone = " + BStone);
            int tmp = 1;
            if("Diamond".equals(Block)) tmp = 5;
            if("Coal".equals(Block)) tmp = 1;
            return BStone % tmp == 0 && BStone != 0;
        }
        return true;
    }    public boolean PersentIO(String Mode) throws IOException {
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
                yaml.set(plugin.BreakListener.Item_S[i]+".Item_times", plugin.BreakListener.Item_times[i] );
                yaml.set(plugin.BreakListener.Item_S[i]+".Item_CanBreak", plugin.BreakListener.Item_CanBreak[i] );
                //yaml.set(plugin.BreakListener.Item_S[i]+".BlockHeight", plugin.BreakListener.BlockHeight[i] );
            }
            SettingFiles(yaml, file, true);
            plugin.cLog.info("セーブ完了しました");
            return true;
        }
        else if("r".equalsIgnoreCase(Mode))
        {
            for(int i = 0 ; i < plugin.BreakListener.Item_S.length ; i++){
                plugin.BreakListener.Item[i] = (ItemStack) yaml.get(plugin.BreakListener.Item_S[i]+".Item_persent");
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
