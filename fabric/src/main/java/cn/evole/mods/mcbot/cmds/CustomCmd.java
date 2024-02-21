package cn.evole.mods.mcbot.cmds;

import cn.evole.onebot.sdk.util.json.GsonUtils;
import com.google.gson.JsonObject;
import lombok.val;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/9/2 13:19
 * Version: 1.0
 */
public class CustomCmd {

    private final String cmdAlies;

    private final String cmdContent;

    private final int requirePermission;

    private boolean enabled = true;

    public CustomCmd(String cmdAlies, String cmdContent, int requirePermission) {
        this.cmdAlies = cmdAlies;
        this.cmdContent = cmdContent;
        this.requirePermission = requirePermission;
    }

    public static CustomCmd loadFromJson(JsonObject json) {

        val alies = GsonUtils.getAsString(json,"alies");
        val content = GsonUtils.getAsString(json,"content");
        int role = GsonUtils.getAsInt(json,"role", 0);

        val cmd = new CustomCmd(alies, content, role);

        val enabled = GsonUtils.getAsBoolean(json,"enabled", true);

        cmd.setEnabled(enabled);

        return cmd;
    }

    public String getCmdAlies() {
        return cmdAlies;
    }

    public String getCmdContent() {
        return cmdContent;
    }

    public int getRequirePermission() {
        return requirePermission;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
