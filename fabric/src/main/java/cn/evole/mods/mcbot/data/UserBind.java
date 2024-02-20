package cn.evole.mods.mcbot.data;

import com.xykj.easycsv.entity.CsvProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @Project: McBot-fabric
 * @Author: cnlimiter
 * @CreateTime: 2024/2/20 14:56
 * @Description:
 */

@Getter
@Setter
public class UserBind {
    @CsvProperty("学号")
    private int no;
    @CsvProperty("姓名")
    private String name;
    @CsvProperty("年龄")
    private Integer age;
}
