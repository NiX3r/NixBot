package cz.iliev.managers.user_verification_manager;

import cz.iliev.interfaces.IManager;
import cz.iliev.managers.user_verification_manager.instances.InviteInstance;
import cz.iliev.managers.user_verification_manager.listeners.UserVerificationManagerMessageCreateListener;
import cz.iliev.managers.user_verification_manager.listeners.UserVerificationManagerServerMemberJoinListener;
import cz.iliev.managers.user_verification_manager.utils.FileUtils;
import cz.iliev.utils.CommonUtils;
import cz.iliev.utils.LogUtils;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.user.User;
import org.javacord.api.interaction.SlashCommandInteraction;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class UserVerificationManager implements IManager {

    private boolean ready;
    private HashMap<Long, String> usersCodes;
    private java.util.List<InviteInstance> invites;

    public static final List<String> DEFAULT_ROLES_ID = new ArrayList<String>() {{add("1058009225491656724"); add("1219589725833265152");}};

    public UserVerificationManager(){
        setup();
    }

    @Override
    public void setup() {
        LogUtils.info("Load and start UserVerificationManager");
        usersCodes = FileUtils.loadVerification();
        invites = new ArrayList<InviteInstance>();
        CommonUtils.getNixCrew().getInvites().thenAccept(richInvites -> {
            invites.forEach(invite ->{
                invites.add(new InviteInstance(
                        invite.getCode(),
                        invite.getCreator_id(),
                        invite.getUses()
                ));
            });
        });
        CommonUtils.bot.addMessageCreateListener(new UserVerificationManagerMessageCreateListener());
        CommonUtils.bot.addServerMemberJoinListener(new UserVerificationManagerServerMemberJoinListener());
        ready = true;
        LogUtils.info("UserVerificationManager loaded and started. Ready to use");
    }

    @Override
    public void kill() {
        LogUtils.info("Kill UserVerificationManager");
        FileUtils.saveVerification(usersCodes);
        ready = false;
        LogUtils.info("UserVerificationManager killed");
    }

    @Override
    public boolean restart() {
        kill();
        setup();
        return ready;
    }

    @Override
    public void onCommand(SlashCommandInteraction interaction) {
        return;
    }

    @Override
    public void onConsoleCommand(Object data) {
        return;
    }

    @Override
    public boolean isReady() {
        return ready;
    }

    @Override
    public String managerName() {
        return "User verification manager";
    }

    @Override
    public String managerDescription() {
        return "Manager for verificate users\nFeatures: \n- Verificate user via captcha\n- Send welcome message\n- Give default roles\n- Update statistics";
    }

    @Override
    public String color() {
        return "#0e4c09";
    }

    public void sendCode(User user){

        String code = addUser(user.getId());
        if(code == null)
            return;

        int width = 600, height = 300;
        Color backgroundColor = Color.decode("#2c2c2c");

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = image.createGraphics();

        graphics.setColor(backgroundColor);
        graphics.fillRect(0, 0, width, height);

        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics.setColor(Color.WHITE);
        graphics.setFont(new Font("Consola", Font.BOLD, 80));
        int textWidth = graphics.getFontMetrics().stringWidth(code);
        int textHeight = graphics.getFontMetrics().getHeight();
        int textX = (width - textWidth) / 2;
        int textY = (height - textHeight) / 2 + textHeight;

        graphics.drawString(code, textX, textY);

        Random random = new Random();
        graphics.setStroke(new java.awt.BasicStroke(3));
        for (int i = 0; i < 70; i++) {
            int x1 = random.nextInt(width);
            int y1 = random.nextInt(height);
            int x2 = random.nextInt(width);
            int y2 = random.nextInt(height);
            Color lineColor = Color.WHITE;
            graphics.setColor(lineColor);
            graphics.drawLine(x1, y1, x2, y2);
        }

        graphics.dispose();

        var embed = new EmbedBuilder()
                .setTitle("Welcome to NiXCrew")
                .setDescription("```To continue please verificate using our system.\nType in code below```")
                .setColor(Color.decode("#2100FF"))
                .setFooter("Version: " + CommonUtils.VERSION);
        embed.setImage(image);
        user.sendMessage(embed);
    }

    private String addUser(long userId){
        if(usersCodes.containsKey(userId))
            return null;
        Random rand = new Random();
        int r = rand.nextInt(1048576,11184810);
        String code = Integer.toHexString(r);
        usersCodes.put(userId, code);
        return code;
    }

    public InviteInstance addInvite(InviteInstance instance){
        invites.add(instance);
        return instance;
    }

    public InviteInstance getInviteByCode(String code){
        for (var invite : invites){
            if(invite.getCode().equals(code))
                return invite;
        }
        return null;
    }

    public HashMap<Long, String> getUsersCodes() {
        return usersCodes;
    }
    public List<InviteInstance> getInvites() { return invites; }
}
