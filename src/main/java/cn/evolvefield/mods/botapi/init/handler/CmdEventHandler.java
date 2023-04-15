package cn.evolvefield.mods.botapi.init.handler;

import cn.evolvefield.mods.botapi.common.cmds.*;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;


/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/10/2 1:50
 * Version: 1.0
 */
public class CmdEventHandler extends CommandBase {

    @Override
    public String getCommandName() {
        return "mcbot";
    }

    @Override
    public String getCommandUsage(ICommandSender p_71518_1_) {
        return "请使用 mcbot help查询用法";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        switch (args.length){
            case 1:{
                switch (args[0]){
                    case "customs":{
                        ListCustomCommand.execute(sender, args);
                        break;
                    }
                    case "reload" :{
                        ReloadConfigCmd.execute(sender, args);
                        break;
                    }
                    case "disconnect" :{
                        DisconnectCommand.execute(sender, args);
                        break;
                    }
                    case "help" :{
                        HelpCommand.execute(sender, args);
                        break;
                    }
                    case "status" :{
                        StatusCommand.execute(sender, args);
                        break;
                    }
                }
                break;
            }
            case 2:{
                switch (args[0]){
                    case "connect":{
                        switch (args[1]){
                            case "mirai":{
                                ConnectCommand.miraiCommonExecute(sender, args);
                                break;
                            }
                            case "cqhttp":{
                                ConnectCommand.cqhttpCommonExecute(sender, args);
                                break;
                            }
                        }
                        break;
                    }
                    case "addChannelId":{
                        AddChannelIDCommand.execute(sender, args);
                        break;
                    }
                    case "delChannelId":{
                        RemoveChannelIDCommand.execute(sender, args);
                        break;
                    }
                    case "setGuild":{
                        GuildIDCommand.execute(sender, args);
                        break;
                    }
                    case "debug":{
                        DebugCommand.execute(sender, args);
                        break;
                    }
                    case "addGroup":{
                        AddGroupIDCommand.execute(sender, args);
                        break;
                    }
                    case "removeGroup":{
                        RemoveGroupIDCommand.execute(sender, args);
                        break;
                    }
                    case "setBot":{
                        BotIDCommand.execute(sender, args);
                        break;
                    }
                    case "setVerifyKey":{
                        VerifyKeyCommand.execute(sender, args);
                        break;
                    }
                }
                break;
            }
            case 3:{
                switch (args[0]){
                    case "send":{
                        switch (args[1]){
                            case "all":{
                                SendCommand.allExecute(sender, args);
                                break;
                            }
                            case "join":{
                                SendCommand.joinExecute(sender, args);
                                break;
                            }
                            case "leave":{
                                SendCommand.leaveExecute(sender, args);
                                break;
                            }
                            case "death":{
                                SendCommand.deathExecute(sender, args);
                                break;
                            }
                            case "chat":{
                                SendCommand.chatExecute(sender, args);
                                break;
                            }
                            case "achievements":{
                                SendCommand.achievementsExecute(sender, args);
                                break;
                            }
                            case "welcome":{
                                SendCommand.welcomeExecute(sender, args);
                                break;
                            }
                        }
                        break;
                    }
                    case "receive":{
                        switch (args[1]){
                            case "all":{
                                ReceiveCommand.allExecute(sender, args);
                                break;
                            }
                            case "chat":{
                                ReceiveCommand.chatExecute(sender, args);
                                break;
                            }
                            case "cmd":{
                                ReceiveCommand.cmdExecute(sender, args);
                                break;
                            }
                        }
                        break;
                    }
                    case "connect":{
                        switch (args[1]){
                            case "mirai":{
                                ConnectCommand.miraiExecute(sender, args);
                                break;
                            }
                            case "cqhttp":{
                                ConnectCommand.cqhttpExecute(sender, args);
                                break;
                            }
                        }
                        break;
                    }
                }
            }
        }

    }

    //懒得写tab了 qwq

}
