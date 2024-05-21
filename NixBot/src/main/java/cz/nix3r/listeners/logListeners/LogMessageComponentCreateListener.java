package cz.nix3r.listeners.logListeners;

import com.google.gson.GsonBuilder;
import cz.nix3r.utils.CommonUtils;
import cz.nix3r.utils.EventLogSystem;
import cz.nix3r.utils.LogSystem;
import org.javacord.api.entity.message.Message;
import org.javacord.api.event.interaction.MessageComponentCreateEvent;
import org.javacord.api.listener.interaction.MessageComponentCreateListener;

public class LogMessageComponentCreateListener implements MessageComponentCreateListener {

    public class Data{
        private long serverId;
        private Message message;

        public Data(long serverId, Message message){
            this.serverId = serverId;
            this.message = message;
        }
    }

    @Override
    public void onComponentCreate(MessageComponentCreateEvent messageComponentCreateEvent) {
        var interaction = messageComponentCreateEvent.getMessageComponentInteraction();
        long serverId = interaction.getServer().isPresent() ? interaction.getServer().get().getId() : 0;
        var data = new Data(serverId, interaction.getMessage());
        String sData = new GsonBuilder().create().toJson(data);
        /*EventLogSystem.logEvent(
                "MessageComponentCreateListener",
                messageComponentCreateEvent.getMessageComponentInteraction().getCreationTimestamp().toEpochMilli(),
                interaction.getUser().getName(),
                interaction.getUser().getId(),
                sData
        );*/
        interaction.acknowledge();
    }
}
