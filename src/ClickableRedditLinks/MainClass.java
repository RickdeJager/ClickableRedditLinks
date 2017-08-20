package ClickableRedditLinks;


import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;


public class MainClass extends JavaPlugin {


    @Override
    public void onEnable(){

        if(getServer().getPluginManager().getPlugin("ProtocolLib") != null) {
            ReplaceAllChat replaceAllChat = new ReplaceAllChat();
            replaceAllChat.init(this);
        }else{
            System.out.println("[ClickableRedditLinks] ProtocolLib was not found, so reddit links in commands will not be recognised");
            Bukkit.getPluginManager().registerEvents(new ReplacePlayerChat(), this);
        }

    }

    @Override
    public void onDisable(){

    }


}
