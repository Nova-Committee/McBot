package cn.evole.mods.mcbot.config;

import cn.evole.config.toml.AutoReloadToml;
import cn.evole.config.toml.TomlUtil;
import cn.evole.config.toml.annotation.Reload;
import cn.evole.config.toml.annotation.TableField;
import cn.evole.mods.mcbot.McBot;
import lombok.Getter;
import lombok.Setter;
import org.tomlj.TomlTable;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/10/2 13:44
 * Version: 1.0
 */

@Getter
@Setter
public class ModConfig extends AutoReloadToml {
    @Reload(autoReload = true)
    public static ModConfig INSTANCE = TomlUtil.readConfig(McBot.CONFIG_FILE, ModConfig.class, true);

    @TableField(value = "common", topComment = "通用")
    private CommonConfig common = new CommonConfig();
    @TableField(value = "status", topComment = "状态")
    private StatusConfig status = new StatusConfig();
    @TableField(value = "cmd", topComment = "命令")
    private CmdConfig cmd = new CmdConfig();
    @TableField(value = "bot_config", topComment = "机器人")
    private BotConfig botConfig = new BotConfig();


    public ModConfig() {
        super(null, McBot.CONFIG_FILE);
    }

    public ModConfig(TomlTable source) {
        super(source, McBot.CONFIG_FILE);
        this.load(ModConfig.class);
    }

    public void save(){
        TomlUtil.writeConfig(McBot.CONFIG_FILE,INSTANCE);
    }

}
