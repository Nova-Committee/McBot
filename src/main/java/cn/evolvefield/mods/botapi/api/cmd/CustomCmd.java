package cn.evolvefield.mods.botapi.api.cmd;

import cn.evolvefield.mods.botapi.util.JsonUtils;
import com.google.gson.JsonObject;
import lombok.val;
import net.minecraft.util.ResourceLocation;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/9/2 13:19
 * Version: 1.0
 */
public class CustomCmd {

    private final ResourceLocation id;

    private final String cmdAlies;

    private final String cmdContent;

    private final int requirePermission;

    private boolean enabled = true;

    public CustomCmd(ResourceLocation id, String cmdAlies, String cmdContent, int requirePermission) {
        this.id = id;
        this.cmdAlies = cmdAlies;
        this.cmdContent = cmdContent;
        this.requirePermission = requirePermission;
    }

    public static CustomCmd loadFromJson(ResourceLocation id, JsonObject json) {

        val alies = JsonUtils.getAsString(json, "alies");
        val content = JsonUtils.getAsString(json, "content");
        int role = JsonUtils.getAsInt(json, "role", 0);

        val cmd = new CustomCmd(id, alies, content, role);

        val enabled = JsonUtils.getAsBoolean(json, "enabled", true);

        cmd.setEnabled(enabled);

        return cmd;
    }

    public ResourceLocation getId() {
        return id;
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
