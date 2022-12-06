package cn.evolvefield.mods.botapi.util.placeholder;

/**
 * Project: Bot-Connect-fabric-1.18
 * Author: cnlimiter
 * Date: 2022/12/6 15:41
 * Description:
 */
public enum DefaultContext {
    PLAYER_NAME("server", "playerName");


    private final String type;
    private final String var;


    DefaultContext(String type, String var) {
        this.type = type;
        this.var = var;
    }

    public String getType() {
        return type;
    }

    public String getVar() {
        return var;
    }

    public String getName() {
        return "${" + this.type + ":" + this.var + "}";
    }

    @Override
    public String toString() {
        return this.type + ":" + this.var;
    }
}
