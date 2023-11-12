package cn.evole.mods.mcbot.init.config;

import cn.evole.libs.tomlj.TomlTable;
import lombok.Getter;
import lombok.Setter;
import cn.evole.config.toml.AutoLoadTomlConfig;
import cn.evole.config.toml.annotation.TableField;

import java.util.*;

/**
 * Name: McBot-fabric / CommonConfig
 * Author: cnlimiter
 * CreateTime: 2023/11/7 20:24
 * Description:
 */
@Getter
@Setter
public class CommonConfig extends AutoLoadTomlConfig {

    @TableField(rightComment = "开启q群功能")
    private boolean groupOn = true;
    @TableField(rightComment = "支持多个q群")
    private List<Long> groupIdList = new ArrayList<Long>(){};//支持多个q群
    @TableField(rightComment = "是否开启频道")
    private boolean guildOn = false;//是否开启频道
    @TableField(rightComment = "频道id")
    private String guildId = "";//频道id
    @TableField(rightComment = "子频道列表")
    private List<String> channelIdList = new ArrayList<String>(){};//子频道列表
    @TableField(rightComment = "机器人qq")
    private long botId = 0;//机器人qq
    @TableField(rightComment = "是否启用")
    private boolean enable = true;//是否启用
    @TableField(rightComment = "是否开发模式，将显示事件信息操作")
    private boolean debug = false;//是否开发模式，将显示事件信息操作
    @TableField(rightComment = "选择语言系统")
    private String languageSelect = "zh_cn";//选择语言系统
    @TableField(rightComment = "自动连接")
    private boolean autoOpen = true;//自动连接
    @TableField(rightComment = "是否开启聊天栏图片功能")
    private boolean imageOn = true;//是否开启聊天栏图片功能

    public CommonConfig() {
        super(null);
    }

    public CommonConfig(TomlTable source) {
        super(source);
        this.load(CommonConfig.class);
    }

    public void addChannelId(String id) {
        if (!channelIdList.contains(id)) channelIdList.add(id);
    }

    public void removeChannelId(String id) {
        channelIdList.remove(id);
    }

    public void removeGroupId(long id) {
        groupIdList.remove(id);
    }

    public void addGroupId(long id) {
        if (!groupIdList.contains(id)) groupIdList.add(id);
    }

}
