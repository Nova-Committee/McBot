package cn.evolvefield.mods.botapi.core.service;

import cn.evolvefield.mods.botapi.BotApi;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/3/18 20:47
 * Version: 1.0
 */
public class MySqlService {
    public static Connection Join() {
        //声明Connection对象
        Connection con = null;

        //驱动程序名
        String driver = "org.sqlite.JDBC";

        try {
            //加载驱动程序
            Class.forName(driver);
            //1.getConnection()方法，连接MySQL数据库！！
            con = DriverManager.getConnection("jdbc:sqlite:" + BotApi.CONFIG_FOLDER.toFile().toString() + "/data.db");
            if (!con.isClosed())
                System.out.println("§7[§a§l*§7] §b数据库连接成功！");
            System.out.println("§7[§a§l*§7] §b开始创建表单……");
            try {
                //创建statement类对象，用来执行SQL语句！！
                Statement statement = con.createStatement();
                //要执行的SQL语句,创建一个player_data表单,已有则跳过创建
                String sql = "CREATE TABLE IF NOT EXISTS bot_player_data("
                        + "Player varchar(20) not null,"
                        + "QQ bigint not null);";
                if (0 == statement.executeLargeUpdate(sql)) {
                    System.out.println("§7[§a§l*§7] §b成功创建表单或已存在表单");
                } else {
                    System.out.println("§7[§a§l*§7] §c创建表单失败请检查数据库");
                }
                statement.executeQuery(sql);
            } catch (SQLException throwables) {
                System.out.println("§7[§a§l*§7] §c创建表单失败请检查数据库");
                throwables.printStackTrace();
            }
            return con;
        } catch (ClassNotFoundException e) {
            //数据库驱动类异常处理
            System.out.println("§7[§a§l*§7] §c数据库驱动异常，找不到驱动程序！");
            e.printStackTrace();
        } catch (SQLException throwables) {
            //数据库连接失败异常处理
            System.out.println("§7[§a§l*§7] §c连接失败，请检查配置文件");
            throwables.printStackTrace();
        }
        return null;
    }
}
