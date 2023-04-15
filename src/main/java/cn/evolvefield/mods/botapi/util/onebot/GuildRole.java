package cn.evolvefield.mods.botapi.util.onebot;

/**
 * Project: Bot-Connect-fabric-1.18
 * Author: cnlimiter
 * Date: 2023/1/21 18:57
 * Description:
 */
public enum GuildRole {

    GUILD_MASTER("频道主"),
    GUILD_ADMIN("管理员"),
    BOT_ADMIN("机器人管理员"),
    CHANNEL_ADMIN("子频道管理员");
    public final String role;

    GuildRole(String role) {
        this.role = role;
    }


}
