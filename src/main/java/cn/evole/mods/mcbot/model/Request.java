package cn.evole.mods.mcbot.model;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

/**
 * Author cnlimiter
 * CreateTime 2023/7/28 17:31
 * Name Request
 * Description
 */
@Data
public class Request {
    @SerializedName("id")
    String id;
    @SerializedName("type")
    String type;
    @SerializedName("data")
    String data;
}
