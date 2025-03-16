<p align="center">
    <img width="200" src="https://s2.loli.net/2022/10/02/zrebhtAKjc3GyIl.png" alt="title">  
</p>
<div align="center">

# McBot

_✨ 基于 [OneBot](https://github.com/howmanybots/onebot/blob/master/README.md) 协议的 我的世界 QQ机器人✨_

</div>
<hr>
<p align="center">
    <a href="https://github.com/Nova-Committee/McBot/issues"><img src="https://img.shields.io/github/issues/Nova-Committee/McBot?style=flat" alt="issues" /></a>
    <a href="https://www.curseforge.com/minecraft/mc-mods/botconnect">
        <img src="http://cf.way2muchnoise.eu/botconnect.svg" alt="CurseForge Download">
    </a>
    <img src="https://img.shields.io/badge/license-GPLV3-green" alt="License">
    <a href="https://github.com/howmanybots/onebot"><img src="https://img.shields.io/badge/OneBot-v11-blue?style=flat&logo=data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAEAAAABABAMAAABYR2ztAAAAIVBMVEUAAAAAAAADAwMHBwceHh4UFBQNDQ0ZGRkoKCgvLy8iIiLWSdWYAAAAAXRSTlMAQObYZgAAAQVJREFUSMftlM0RgjAQhV+0ATYK6i1Xb+iMd0qgBEqgBEuwBOxU2QDKsjvojQPvkJ/ZL5sXkgWrFirK4MibYUdE3OR2nEpuKz1/q8CdNxNQgthZCXYVLjyoDQftaKuniHHWRnPh2GCUetR2/9HsMAXyUT4/3UHwtQT2AggSCGKeSAsFnxBIOuAggdh3AKTL7pDuCyABcMb0aQP7aM4AnAbc/wHwA5D2wDHTTe56gIIOUA/4YYV2e1sg713PXdZJAuncdZMAGkAukU9OAn40O849+0ornPwT93rphWF0mgAbauUrEOthlX8Zu7P5A6kZyKCJy75hhw1Mgr9RAUvX7A3csGqZegEdniCx30c3agAAAABJRU5ErkJggg=="></a>  
    <a href="https://github.com/Nova-Committee/McBot/actions/workflows/build.yml"><img src="https://github.com/Nova-Committee/McBot/actions/workflows/build.yml/badge.svg"></a>  
</p>  




<p align="center">
    <a href="README_EN.md">English</a> | 
    <a href="https://github.com/Nova-Committee/McBot#%E9%95%BF%E6%9C%9F%E6%94%AF%E6%8C%81%E7%89%88%E6%9C%AC">长期支持版本</a> |
    <a href="https://github.com/Nova-Committee/McBot#%E5%BF%AB%E9%80%9F%E5%BC%80%E5%A7%8B">快速开始</a>
</p>

# 长期支持版本

> Forge 1.16.5/1.18.2/1.19.2/1.20.1/1.21  
> Fabric 1.16.5/1.18.2/1.19.2/1.20.1/1.21

# 快速开始

### 使用api进行请求

```java
public class APIDemo {
    static {
        // 事件回调
        McBotEvents.ON_CHAT.register((player, msgId, msg) -> System.out.printf("McBot刚刚转发一条消息。由%s发送了%s (%s)%n", player.getName().getString(), msg, msgId));
    }

    /**
     * 群里发送消息
     * @param groupId 群号
     * @param message 消息
     */
    public static void doSend(long groupId, String message) throws CommandSyntaxException {
        Const.sendGroupMsg(groupId, message)
    }

    /**
     * 撤回消息
     * @param message_id 消息ID
     */
    public static void recallMessage(int message_id) {
        JsonObject json = new Gson().fromJson(
                String.format("{'message_id': %s}", message_id),
                JsonObject.class);
        Const.customRequest(ActionType.DELETE_MSG, json);
    }
}
```

### 事件监听示例

```java
public class ListenerDemo implements Listener {
    static OneBotClient client;
    public static void main(String[] args) throws InterruptedException {
        BotConfig config = new BotConfig("ws://192.168.1.25:5800", "123456");
        client = OneBotClient.create(config, new HandlerTest()).open();//创建websocket连接实例并监听事件
        //client.getEventsBus().register(new HandlerTest());//监听事件
    }

    @SubscribeEvent(internal = true)
    public void msg1(GroupMessageEvent event){//监听群聊消息
        System.out.println(event.getMessage());//打印消息
        System.out.println(event.getRawMessage());//打印原始消息
    }
}
```

# 支持

McBot 以 [OneBot-v11](https://github.com/howmanybots/onebot/tree/master/v11/specs)
标准协议进行开发，兼容所有支持正向WebSocket的OneBot协议端
| 项目地址                                                                              | 核心作者           | 备注                                                                    |
|-----------------------------------------------------------------------------------|----------------|-----------------------------------------------------------------------|
| [Overflow](https://github.com/MrXiaoM/Overflow)                                   | MrXiaoM        | 实现 mirai 的无缝迁移                                                        |
| [Lagrange.Core](https://github.com/LagrangeDev/Lagrange.Core)                     | NepPure        | C#实现 By Konata.Core                                                   |
| [OpenShamrock](https://github.com/whitechi73/OpenShamrock)                        | whitechi73     | Xposed框架hook实现                                                        |
| [Gensokyo](https://github.com/Hoshinonyaruko/Gensokyo)                            | Hoshinonyaruko | 基于官方api 轻量 原生跨平台                                                      |
| [LLOnebot](https://github.com/LLOneBot/LLOneBot)                                  | linyuchen      | 使用[LiteLoaderQQNT](https://github.com/LiteLoaderQQNT/LiteLoaderQQNT)  |
| [NapCatQQ](https://github.com/NapNeko/NapCatQQ)                                   | MliKiowa | 基于NTQQ的无头Bot框架  |
| [OneBot-Mirai](https://github.com/cnlimiter/onebot-mirai)                                   | cnlimiter | Mirai的onebot协议实现  |

# Credits

* [OneBot](https://github.com/botuniverse/onebot)

# 开源许可

This product is licensed under the GNU General Public License version 3. The license is as published by the Free
Software Foundation published at https://www.gnu.org/licenses/gpl-3.0.html.

Alternatively, this product is licensed under the GNU Lesser General Public License version 3 for non-commercial use.
The license is as published by the Free Software Foundation published at https://www.gnu.org/licenses/lgpl-3.0.html.

Feel free to contact us if you have any questions about licensing or want to use the library in a commercial closed
source product.

# 致谢

Thanks to [JetBrains](https://www.jetbrains.com/?from=mcbot) for allocating free open-source licences for IDEs such as [IntelliJ IDEA](https://www.jetbrains.com/idea/?from=mcbot)  
[<img src=".github/jetbrains-variant-3.png" width="200"/>](https://www.jetbrains.com/?from=mcbot)

## 星星（要要）~⭐

[![Stargazers over time](https://starchart.cc/Nova-Committee/McBot.svg)](https://starchart.cc/Nova-Committee/McBot)


