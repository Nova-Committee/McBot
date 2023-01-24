<p align="center">
    <img width="300" src="https://s2.loli.net/2022/10/02/zrebhtAKjc3GyIl.png" alt="title">  
</p>
<div align="center">

# Bot-Connect

_✨ 基于 [OneBot](https://github.com/howmanybots/onebot/blob/master/README.md) 协议的 我的世界 QQ机器人✨_

</div>
<hr>
<p align="center">
    <a href="https://github.com/Nova-Committee/Bot-Connect/issues"><img src="https://img.shields.io/github/issues/Nova-Committee/Bot-Connect?style=flat" alt="issues" /></a>
    <a href="https://www.curseforge.com/minecraft/mc-mods/botconnect">
        <img src="http://cf.way2muchnoise.eu/botconnect.svg" alt="CurseForge Download">
    </a>
    <img src="https://img.shields.io/badge/license-GPLV3-green" alt="License">
    <a href="https://github.com/howmanybots/onebot"><img src="https://img.shields.io/badge/OneBot-v11-blue?style=flat&logo=data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAEAAAABABAMAAABYR2ztAAAAIVBMVEUAAAAAAAADAwMHBwceHh4UFBQNDQ0ZGRkoKCgvLy8iIiLWSdWYAAAAAXRSTlMAQObYZgAAAQVJREFUSMftlM0RgjAQhV+0ATYK6i1Xb+iMd0qgBEqgBEuwBOxU2QDKsjvojQPvkJ/ZL5sXkgWrFirK4MibYUdE3OR2nEpuKz1/q8CdNxNQgthZCXYVLjyoDQftaKuniHHWRnPh2GCUetR2/9HsMAXyUT4/3UHwtQT2AggSCGKeSAsFnxBIOuAggdh3AKTL7pDuCyABcMb0aQP7aM4AnAbc/wHwA5D2wDHTTe56gIIOUA/4YYV2e1sg713PXdZJAuncdZMAGkAukU9OAn40O849+0ornPwT93rphWF0mgAbauUrEOthlX8Zu7P5A6kZyKCJy75hhw1Mgr9RAUvX7A3csGqZegEdniCx30c3agAAAABJRU5ErkJggg=="></a>

</p>

<p align="center">
    <a href="README_EN.md">English</a> | 
    <a href="长期支持版本">长期支持版本</a> |
    <a href="快速开始">快速开始</a>
</p>

# 长期支持版本

> Forge-1.19.3    
> Fabric-1.19.3

# 快速开始

### 使用api进行请求

```java
public class TestCmd {
    public static ArgumentBuilder<CommandSourceStack, ?> register() {
        return Commands.literal("test")
                .executes(TestCmd::execute);
    }

    public static int execute(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        System.out.println(BotApi.bot.sendGroupMsg(337631140, MsgUtils.builder().text("1").build(), true));
        //群里发送消息
        return 0;
    }
}
```

### 事件监听示例

```java
public class WebSocketServerTest {
    public static void main(String[] args) throws Exception {
        LinkedBlockingQueue<String> blockingQueue = new LinkedBlockingQueue<>();//使用队列传输数据
        Connection service = ConnectFactory.createWebsocketClient(new BotConfig("ws://127.0.0.1:8080", null, false), blockingQueue);
        EventDispatchers dispatchers = new EventDispatchers(blockingQueue);//创建事件分发器
        GroupMessageListener groupMessageListener = new GroupMessageListener();
        groupMessageListener.addHandler("天气", new Handler<GroupMessageEvent>() {
            @Override
            public void handle(GroupMessageEvent groupMessage) {
                System.out.println(groupMessage);

            }
        });
        dispatchers.addListener(groupMessageListener);//加入监听
        dispatchers.addListener(new SimpleListener<PrivateMessageEvent>() {//私聊监听
            @Override
            public void onMessage(PrivateMessageEvent privateMessage) {
                System.out.println(privateMessage);
            }
        });

        dispatchers.start(10);//线程组处理任务

    }
}
```

# 支持

Bot-Connect 以 [OneBot-v11](https://github.com/howmanybots/onebot/tree/master/v11/specs)
标准协议进行开发，兼容所有支持正向WebSocket的OneBot协议客户端

| 项目地址 | 平台                                            | 核心作者 | 备注 |
| --- |-----------------------------------------------| --- | --- |
| [koishijs/koishi](https://github.com/koishijs/koishi) | [koishi](https://koishi.js.org/)              | shigma |  |
| [onebot-walle/walle-q](https://github.com/onebot-walle/walle-q) |                                               | abrahum |  |
| [Yiwen-Chan/OneBot-YaYa](https://github.com/Yiwen-Chan/OneBot-YaYa) | [先驱](https://www.xianqubot.com/)              | kanri |  |
| [richardchien/coolq-http-api](https://github.com/richardchien/coolq-http-api) | CKYU                                          | richardchien | 可在 Mirai 平台使用 [mirai-native](https://github.com/iTXTech/mirai-native) 加载 |
| [Mrs4s/go-cqhttp](https://github.com/Mrs4s/go-cqhttp) | [MiraiGo](https://github.com/Mrs4s/MiraiGo)   | Mrs4s |  |
| [yyuueexxiinngg/OneBot-Mirai](https://github.com/yyuueexxiinngg/onebot-kotlin) | [Mirai](https://github.com/mamoe/mirai)       | yyuueexxiinngg |  |
| [takayama-lily/onebot](https://github.com/takayama-lily/onebot) | [OICQ](https://github.com/takayama-lily/oicq) | takayama |  |

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

感谢 [JetBrains](https://www.jetbrains.com/?from=Shiro) 提供了这么好用的软件~

[<img src="https://mikuac.com/images/jetbrains-variant-3.png" width="200"/>](https://www.jetbrains.com/?from=mirai)

## 星星（要要）~⭐

[![Stargazers over time](https://starchart.cc/Nova-Committee/Bot-Connect.svg)](https://starchart.cc/Nova-Committee/Bot-Connect)


