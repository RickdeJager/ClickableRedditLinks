package ClickableRedditLinks;


import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.reflect.StructureModifier;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import org.bukkit.plugin.Plugin;


/**
 * This method replaces reddit links in all messages, including motd, tell. msg, etc
 */
public class ReplaceAllChat {

    HelperClass hc = new HelperClass(); //Grab an object of the helperclass to use it's methods

    /**
     *This method set's up the onPacketSending listener
     */
    public void init(Plugin pl) {

        ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(pl, PacketType.Play.Server.CHAT) { //Add the packet listener
            @Override
            public void onPacketSending(PacketEvent event) {
                PacketContainer packet = event.getPacket();
                StructureModifier<WrappedChatComponent> chatComponents = packet.getChatComponents();
                WrappedChatComponent msg = chatComponents.read(0);
                String message = msg.getJson(); //Get the message as a Json string

                message = hc.ReplaceAllLinks(message);  //Filter all links
                try {
                    msg.setJson(message);               //Converting back to a WrappedChatComponent
                    chatComponents.write(0, msg);
                } catch (Exception e) {
                    System.out.println("[ClickableRedditLinks] An error occurred while trying to convert back to json chat.");
                    return;
                }
            }
        });

    }
}
