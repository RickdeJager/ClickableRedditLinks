package ClickableRedditLinks;


import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;


public class ReplacePlayerChat implements Listener{

    HelperClass hc = new HelperClass();

        @EventHandler
        public void OnPlayerChatEvent (AsyncPlayerChatEvent e){
            String message = e.getMessage();
            message = hc.FilterMessage(message, false);
            e.setMessage(message);


        }

}
