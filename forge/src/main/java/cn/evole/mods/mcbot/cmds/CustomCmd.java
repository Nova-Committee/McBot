package cn.evole.mods.mcbot.cmds;

import cn.evole.onebot.sdk.util.GsonUtils;
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

    private final boolean vanishSupport;

    @Setter
    private boolean enabled;

    public CustomCmd(String cmdAlies, String cmdContent, int requirePermission, boolean vanish, boolean enabled) {
        this.cmdAlies = cmdAlies;
        this.cmdContent = cmdContent;
        this.requirePermission = requirePermission;
        this.vanishSupport = vanish;
        this.enabled = enabled;
    }

    public static CustomCmd loadFromJson(JsonObject json) {

        val alies = GsonUtils.getAsString(json,"alies");
        val content = GsonUtils.getAsString(json,"content");
        val role = GsonUtils.getAsInt(json,"role", 0);
        val vanish = GsonUtils.getAsBoolean(json, "vanish", false);
        val enabled = GsonUtils.getAsBoolean(json,"enabled", true);

        return new CustomCmd(alies, content, role, vanish, enabled);
    }
}
