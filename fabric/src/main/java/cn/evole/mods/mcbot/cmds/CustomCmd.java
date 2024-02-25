package cn.evole.mods.mcbot.cmds;

import cn.evole.onebot.sdk.util.json.GsonUtils;
import com.google.gson.JsonObject;
import lombok.Getter;
import lombok.Setter;
import lombok.val;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/9/2 13:19
 * Version: 1.0
 */
@Getter
public class CustomCmd {

    private final String cmdAlies;

    private final String cmdContent;

    private final int requirePermission;

    private final boolean OPEscape;

    @Setter
    private boolean enabled;

    public CustomCmd(String cmdAlies, String cmdContent, int requirePermission, boolean OPEscape, boolean enabled) {
        this.cmdAlies = cmdAlies;
        this.cmdContent = cmdContent;
        this.requirePermission = requirePermission;
        this.OPEscape = OPEscape;
        this.enabled = enabled;
    }

    public static CustomCmd loadFromJson(JsonObject json) {

        val alies = GsonUtils.getAsString(json,"alies");
        val content = GsonUtils.getAsString(json,"content");
        val role = GsonUtils.getAsInt(json,"role", 0);
        val escape = GsonUtils.getAsBoolean(json, "escape");
        val enabled = GsonUtils.getAsBoolean(json,"enabled", true);

        return new CustomCmd(alies, content, role, escape, enabled);
    }
}
