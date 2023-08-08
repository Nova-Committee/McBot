package cn.evole.mods.mcbot.model;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Author cnlimiter
 * CreateTime 2023/7/29 1:58
 * Name Response
 * Description
 */
@AllArgsConstructor
@Data
public class Response {
    @SerializedName("id")
    String id;
    @SerializedName("code")
    int code;
    @SerializedName("echo")
    String echo;
    @SerializedName("data")
    String data;
}
