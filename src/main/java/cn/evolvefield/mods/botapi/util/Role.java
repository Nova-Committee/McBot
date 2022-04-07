package cn.evolvefield.mods.botapi.util;


import cn.evolvefield.mods.botapi.util.json.JSONObject;

public class Role {

    public enum UserRole {
        OWNER,
        ADMIN,
        USER,
        UNKNOWN
    }

    private static UserRole getUserRole(JSONObject userInfo) {
        if (userInfo.getInt("retcode") != 0) {
            return UserRole.UNKNOWN;
        }

        String role = userInfo.getJSONObject("data").getString("role");
        switch (role) {
            case "owner": {
                return UserRole.OWNER;
            }
            case "admin": {
                return UserRole.ADMIN;
            }
            default: {
                return UserRole.USER;
            }
        }
    }


//    public static boolean checkRole(long userId) {
//        for (long qq : ConfigHelper.adminList) {
//            if (qq == userId) {
//                return true;
//            }
//        }
//
//        if (ConfigHelper.groupAdminAsGameAdmin) {
//            UserRole role = getUserRole(SendMessage.getProfile(ConfigHelper.groupId, userId));
//            return role == UserRole.ADMIN || role == UserRole.OWNER;
//        }
//
//        return false;
//    }
}
