//package cn.evolvefield.mods.botapi.config;
//
//import cn.evolvefield.mods.botapi.BotApi;
//import io.netty.buffer.ByteBuf;
//import net.minecraftforge.common.config.Config;
//
//@Config(modid = BotApi.MODID)
//public class ModConfig  {
//
//
//
//        @Config.Comment("群号")
//        @Config.RangeInt(min = 0, max = 1000000000)
//        public static int GROUP_ID = 337631140;
//        @Config.Comment("WS 服务器地址")
//        public static String wsHOST = "127.0.0.1";
//        @Config.Comment("WS 服务器端口")
//        @Config.RangeInt(min = 0, max = 65536)
//        public static int wsPORT = 6700;
//        @Config.Comment("发送地址")
//        public static String sendHOST = "127.0.0.1";
//        @Config.Comment("发送端口")
//        @Config.RangeInt(min = 0, max = 65536)
//        public static int sendPORT = 5700;
//        @Config.Comment("是否开启WS接收")
//        public static boolean IS_ENABLED = true;
//        @Config.Comment("Access Key")
//        public static String KEY = "";
//        @Config.Comment("是否发送QQ消息到游戏")
//        public static boolean RECEIVE_ENABLED = true;
//        @Config.Comment("是否发送游戏信息到QQ")
//        public static boolean SEND_ENABLED = true;
//
//
//
//
//
//
//
//
////    @Override
////    public void load() throws IOException {
////        config =
////                ConfigFactory.load()
////                        .withFallback(ConfigFactory.parseFile(file))
////                        .withFallback(ConfigFactory.load("assets/botapi/default.conf"));
////        save();
////    }
////
////    @Override
////    public void save() throws IOException {
////        if (!file.exists()) {
////            if (!file.getParentFile().exists()) {
////                file.getParentFile().mkdirs();
////            }
////            file.createNewFile();
////        }
////
////        ConfigObject toRender = config.root().withOnlyKey("BotApi");
////        String s =
////                toRender.render(ConfigRenderOptions.defaults().setOriginComments(false).setJson(false));
////        InputStream in = new ByteArrayInputStream(s.getBytes(StandardCharsets.UTF_8));
////        OutputStream out = new FileOutputStream(file);
////        IOUtils.copy(in, out);
////        in.close();
////        out.close();
////    }
//
////    @Override
////    public int groupId() {
////        return config.getInt("BotApi.basic.groupId");
////    }
////
////    @Override
////    public String wsHOST() {
////        return config.getString("BotApi.backend.webSocket.host");
////    }
////
////    @Override
////    public int wsPORT() {
////        return Integer.parseInt(config.getString("BotApi.backend.webSocket.port"));
////    }
////
////    @Override
////    public String sendHOST() {
////        return config.getString("BotApi.backend.http.host");
////    }
////
////    @Override
////    public int sendPORT() {
////        return Integer.parseInt(config.getString("BotApi.backend.http.port"));
////    }
////
////    @Override
////    public boolean IS_ENABLED() {
////        return Boolean.parseBoolean(config.getString("BotApi.backend.webSocket.isEnable"));
////    }
////
////    @Override
////    public String KEY() {
////        return config.getString("BotApi.backend.webSocket.token");
////    }
////
////    @Override
////    public boolean RECEIVE_ENABLED() {
////        return Boolean.parseBoolean(config.getString("BotApi.basic.receiveEnable"));
////    }
////
////    @Override
////    public boolean SEND_ENABLED() {
////        return Boolean.parseBoolean(config.getString("BotApi.basic.sendEnable"));
////    }
//}
