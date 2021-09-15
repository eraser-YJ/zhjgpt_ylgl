package com.digitalchina.admin.mid.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("public.sys_dict")
public class DataDictGroup {

    @TableId
    private Integer id;
    private String dictName;
    private String dictCode;
    private String remark;
    private Integer status;
}
