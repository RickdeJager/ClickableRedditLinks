package ClickableRedditLinks;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * This plugin replaces all shortened reddit links with a clickable link
 */
public class MainClass extends JavaPlugin {

    @Override
    public void onEnable(){

        if(getServer().getPluginManager().getPlugin("ProtocolLib") != null) {   //If ProtocolLib is found, use it!
            ReplaceAllChat replaceAllChat = new ReplaceAllChat();
            replaceAllChat.init(this);
        }else{      //If ProtocolLib is not found, use a less powerfull version of the plugin
            System.out.println("[ClickableRedditLinks] ProtocolLib was not found, so only reddit links in player chat will be recognised.");
            Bukkit.getPluginManager().registerEvents(new ReplacePlayerChat(), this);
        }

    }

    @Override
    public void onDisable(){

    }


}
