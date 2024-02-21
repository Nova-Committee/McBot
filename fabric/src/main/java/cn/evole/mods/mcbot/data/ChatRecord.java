package cn.evole.mods.mcbot.data;

import com.xykj.easycsv.entity.CsvProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @Project: McBot-fabric
 * @Author: cnlimiter
 * @CreateTime: 2024/2/21 13:59
 * @Description:
 */

@Getter
@Setter
@AllArgsConstructor
public class ChatRecord {
    @CsvProperty("消息ID")
    private String messageId;
    @CsvProperty("添加日期")
    private long createTime;
    @CsvProperty("qq")
    private String qqId;
    @CsvProperty("群号")
    private String groupId;
    @CsvProperty("消息")
    private String message;
}
