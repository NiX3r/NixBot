package cz.iliev.managers.role_manager;

import cz.iliev.interfaces.IManager;
import cz.iliev.managers.role_manager.commands.MessageCommand;
import cz.iliev.managers.role_manager.commands.SetCommand;
import cz.iliev.managers.role_manager.instances.RoleSetterInstance;
import cz.iliev.managers.role_manager.utils.FileUtils;
import cz.iliev.utils.LogUtils;
import org.javacord.api.interaction.SlashCommandInteraction;

import java.util.List;

public class RoleManager implements IManager {

    private boolean ready;
    private List<RoleSetterInstance> roleSetter;

    public static final String ROLES_CHANNEL_ID = "1219225196594991124";

    public RoleManager(){
        setup();
    }

    @Override
    public void setup() {
        LogUtils.info("Load and start RoleManager");
        roleSetter = FileUtils.loadRoleSetter();
        ready = true;
        LogUtils.info("RoleManager loaded and started. Ready to use");
    }

    @Override
    public void kill() {
        LogUtils.info("Kill RoleManager");
        FileUtils.saveRoleSetter(roleSetter);
        ready = false;
        LogUtils.info("RoleManager killed");
    }

    @Override
    public boolean restart() {
        kill();
        setup();
        return ready;
    }

    @Override
    public void onCommand(SlashCommandInteraction interaction) {
        switch (interaction.getOptions().get(0).getName()){
            case "message":
                new MessageCommand().run(interaction);
                break;
            case "set":
                new SetCommand().run(interaction);
                break;
        }
    }

    @Override
    public void onConsoleCommand(Object data) {

    }

    @Override
    public boolean isReady() {
        return ready;
    }

    public List<RoleSetterInstance> getRoleSetter(){
        return this.roleSetter;
    }

    public RoleSetterInstance getRoleSetterByRoleId(long roleId){
        return getRoleSetterByRoleId(String.valueOf(roleId));
    }

    public RoleSetterInstance getRoleSetterByRoleId(String roleId){
        for(RoleSetterInstance roleSetterInstance : roleSetter){
            if(String.valueOf(roleSetterInstance.getRoleId()).equals(roleId))
                return roleSetterInstance;
        }
        return null;
    }
}
