package com.digitalchina.event.mid.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 *  历史的流程实例
 * </p>
 *
 * @author lzy
 * @since 2019-09-06
 */
@TableName("act_hi_procinst")
@ApiModel(value="HiProcinst对象", description="")
@Data
public class HiProcinst implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId
    private String id;

    private String procInstId;

    private String businessKey;

    private String procDefId;

    private Date startTime;

    private Date endTime;

    private Long duration;

    private String startUserId;

    private String startActId;

    private String endActId;

    private String superProcessInstanceId;

    private String deleteReason;

    private String tenantId;

    private String name;


    public static final String ID_ = "id_";

    public static final String PROC_INST_ID_ = "proc_inst_id_";

    public static final String BUSINESS_KEY_ = "business_key_";

    public static final String PROC_DEF_ID_ = "proc_def_id_";

    public static final String START_TIME_ = "start_time_";

    public static final String END_TIME_ = "end_time_";

    public static final String DURATION_ = "duration_";

    public static final String START_USER_ID_ = "start_user_id_";

    public static final String START_ACT_ID_ = "start_act_id_";

    public static final String END_ACT_ID_ = "end_act_id_";

    public static final String SUPER_PROCESS_INSTANCE_ID_ = "super_process_instance_id_";

    public static final String DELETE_REASON_ = "delete_reason_";

    public static final String TENANT_ID_ = "tenant_id_";

    public static final String NAME_ = "name_";

}
