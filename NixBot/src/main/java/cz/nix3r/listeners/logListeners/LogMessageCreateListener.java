package cz.nix3r.listeners.logListeners;

import com.google.gson.GsonBuilder;
import cz.nix3r.instances.logInstances.LogAuthor;
import cz.nix3r.instances.logInstances.LogServer;
import cz.nix3r.instances.logInstances.LogServerCategory;
import cz.nix3r.instances.logInstances.LogTextChannel;
import cz.nix3r.utils.EventLogSystem;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

import java.util.ArrayList;
import java.util.List;

public class LogMessageCreateListener implements MessageCreateListener {

    public class Data{
        private LogTextChannel channel;
        private String message;
        private long messageId;
        private List<String> attachments;

        public Data(LogTextChannel channel, String message, long messageId, List<String> attachments) {
            this.channel = channel;
            this.message = message;
            this.messageId = messageId;
            this.attachments = attachments;
        }

        public LogTextChannel getChannel() {
            return channel;
        }

        public void setChannel(LogTextChannel channel) {
            this.channel = channel;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public long getMessageId() {
            return messageId;
        }

        public void setMessageId(long messageId) {
            this.messageId = messageId;
        }

        public List<String> getAttachments() {
            return attachments;
        }

        public void setAttachments(List<String> attachments) {
            this.attachments = attachments;
        }
    }

    @Override
    public void onMessageCreate(MessageCreateEvent messageCreateEvent) {
        long serverId = messageCreateEvent.getServer().isPresent() ? messageCreateEvent.getServer().get().getId() : 0;
        var logChannel = new LogTextChannel(
                messageCreateEvent.getChannel().getId(),
                messageCreateEvent.getServerTextChannel().isPresent() ? messageCreateEvent.getServerTextChannel().get().getName() : null,
                messageCreateEvent.getServer().isPresent() ? new LogServer(messageCreateEvent.getServer().get().getId(), messageCreateEvent.getServer().get().getName()) : null,
                messageCreateEvent.getServer().isPresent() && messageCreateEvent.getServerTextChannel().get().asChannelCategory().isPresent() ?
                        new LogServerCategory(messageCreateEvent.getServerTextChannel().get().asChannelCategory().get().getId(),
                                messageCreateEvent.getServerTextChannel().get().asChannelCategory().get().getName()) :
                        null,
                messageCreateEvent.getMessage().getType().toString()
        );
        List<String> attachments = new ArrayList<String>();
        messageCreateEvent.getMessage().getAttachments().forEach(att -> attachments.add(att.getFileName()));
        var data = new Data(logChannel, messageCreateEvent.getMessageContent(), messageCreateEvent.getMessageId(), attachments);
        String sData = new GsonBuilder().create().toJson(data);
        EventLogSystem.logEvent(
                "MessageCreateListener",
                new LogAuthor(messageCreateEvent.getMessageAuthor().getId(), messageCreateEvent.getMessageAuthor().getName(), messageCreateEvent.getMessageAuthor().getAvatar().getUrl().toString()),
                sData
        );
    }
}
