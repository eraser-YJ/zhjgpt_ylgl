package com.digitalchina.admin.mid.entity.emergency;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.digitalchina.admin.mid.entity.Gis;
import com.digitalchina.admin.utils.PgGisTypeHandler;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 应急避难场所管理
 * </p>
 *
 * @author Bruce Li
 * @since 2019-12-06
 */
@TableName("emergency.asylum_resource")
@ApiModel(value="AsylumResource对象", description="应急避难场所管理")
public class AsylumResource implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    @ApiModelProperty(value = "避难场所名称")
    private String name;

    @ApiModelProperty(value = "负责区域")
    private String area;

    @ApiModelProperty(value = "负责人")
    private String head;

    @ApiModelProperty(value = "负责人电话")
    private String phone;

    @ApiModelProperty(value = "详情")
    private String details;

    @ApiModelProperty(value = "所属机构")
    private String affiliation;

    @ApiModelProperty(value = "所在地")
    private String addr;

    /**
     * 坐标对象,需要转换成数据库坐标对象和大地坐标系, 隐式在typeHandler和sqlHandler中执行 不需要关注这块逻辑
     * ps:不同地图采用坐标系不同,需要对应
     *
     * @see PgGisTypeHandler
     */
    @TableField(value = "ST_AsGeoJSON  ( st_transform(xy, 4326 ) ) :: JSON")
    @ApiModelProperty(value = "经纬度",required = true)
    private Gis xy;

    @ApiModelProperty(value = "创建人id")
    private Integer cruid;

    @ApiModelProperty(value = "创建时间")
    private Date crdt;

    @ApiModelProperty(value = "修改时间")
    private Date modt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }
    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
    public String getAffiliation() {
        return affiliation;
    }

    public void setAffiliation(String affiliation) {
        this.affiliation = affiliation;
    }
    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }
    public Gis getXy() {
        return xy;
    }

    public void setXy(Gis xy) {
        this.xy = xy;
    }
    public Integer getCruid() {
        return cruid;
    }

    public void setCruid(Integer cruid) {
        this.cruid = cruid;
    }
    public Date getCrdt() {
        return crdt;
    }

    public void setCrdt(Date crdt) {
        this.crdt = crdt;
    }
    public Date getModt() {
        return modt;
    }

    public void setModt(Date modt) {
        this.modt = modt;
    }

    public static final String ID = "id";

    public static final String NAME = "name";

    public static final String AREA = "area";

    public static final String HEAD = "head";

    public static final String PHONE = "phone";

    public static final String DETAILS = "details";

    public static final String AFFILIATION = "affiliation";

    public static final String ADDR = "addr";

    public static final String XY = "xy";

    public static final String CRUID = "cruid";

    public static final String CRDT = "crdt";

    public static final String MODT = "modt";

    @Override
    public String toString() {
        return "AsylumResource{" +
        "id=" + id +
        ", name=" + name +
        ", area=" + area +
        ", head=" + head +
        ", phone=" + phone +
        ", details=" + details +
        ", affiliation=" + affiliation +
        ", addr=" + addr +
        ", xy=" + xy +
        ", cruid=" + cruid +
        ", crdt=" + crdt +
        ", modt=" + modt +
        "}";
    }
}
