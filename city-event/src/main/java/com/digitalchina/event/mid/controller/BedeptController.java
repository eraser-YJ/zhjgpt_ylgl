package com.digitalchina.event.mid.controller;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.http.HttpStatus;
import com.baomidou.mybatisplus.core.conditions.Condition;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.digitalchina.common.exception.ServiceException;
import com.digitalchina.common.web.RespMsg;
import com.digitalchina.event.dto.BedeptDto;
import com.digitalchina.event.mid.entity.Bedept;
import com.digitalchina.event.mid.entity.Bestep;
import com.digitalchina.event.mid.service.BedeptService;
import com.digitalchina.event.mid.service.BestepService;
import com.digitalchina.event.mid.service.EventService;
import com.digitalchina.modules.constant.SecurityConstant;
import com.digitalchina.modules.constant.TransConstant;
import com.digitalchina.modules.security.Authorize;
import com.digitalchina.modules.security.SecurityUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.activiti.engine.IdentityService;
import org.activiti.engine.identity.Group;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 部门管理 前端控制器
 * </p>
 *
 * @author liuping
 * @since 2019-09-12
 */
@Api(tags = "部门管理接口")
@RestController
@RequestMapping("bedept")
public class BedeptController {

    @Autowired
    private BedeptService bedeptService;

    @Autowired
    private BestepService bestepService;

    @Autowired
    private IdentityService identityService;

    @Autowired
    private EventService eventService;

    @Authorize(SecurityConstant.FORBIDDEN)
    @PostMapping(value = "saveorupdate")
    @ApiOperation(value = "创建或者更新部门信息")
    @Transactional(value = TransConstant.EVENT_TRANSACTION_MANAGER)
    public RespMsg<Void> saveOrUpdate(@RequestBody @Valid Bedept bedept) {
        Boolean isUpdate = null != bedept.getBedid();
        bedept.setBdpntids(null);
        if (null != bedept.getBdpntid()) {
            // 设置父路径
            Bedept parent = bedeptService.getById(bedept.getBdpntid());
            bedept.setBdpntids(parent.getPids());
        }
        if (isUpdate) {
            // 没有查询、更新接口 删了再插入
            identityService.deleteGroup(bedept.getBedid().toString());
            // 查询原来的值
            Bedept old = bedeptService.getById(bedept.getBedid());
            // 比较父节点 不一致更新子树
            if (null == old.getBdpntid() || null == bedept.getBdpntid()
                    || old.getBdpntid().intValue() != bedept.getBdpntid().intValue()) {
                // 旧路径
                Integer[] oldPath = old.getPids();
                // 当前路径
                Integer[] newPath = bedept.getPids();
                // 上界
                Integer upperBound = oldPath.length;
                // 下界
                Integer lowerBound = oldPath.length + 1;
                // 更新其子树
                bedeptService.updateSongTreePaths(oldPath, newPath, lowerBound, upperBound);
            }
        }
        bedeptService.saveOrUpdate(bedept);
        // 更新部门类型
        bedeptService.updateBdtype();
        Group group = identityService.newGroup(bedept.getBedid().toString());
        group.setName(bedept.getBdnm());
        group.setType(bedept.getBdtype().toString());
        identityService.saveGroup(group);
        return RespMsg.ok();
    }

    @Authorize
    @GetMapping("find")
    @ApiOperation(value = "查找单个部门信息")
    @ApiImplicitParam(name = "id", value = "主键id", dataType = "Integer", required = true)
    public RespMsg<Bedept> find(@RequestParam(required = true) Integer id) {
        return RespMsg.ok(bedeptService.findOne(id));
    }

    @Authorize(SecurityConstant.FORBIDDEN)
    @GetMapping(value = "delete")
    @ApiOperation(value = "删除单个部门信息，包括这个部门下的子树也删除", notes = "检查了其它表是否使用了，删除可能会失败")
    @ApiImplicitParam(name = "id", value = "主键id", dataType = "Integer", required = true)
    @Transactional(value = TransConstant.EVENT_TRANSACTION_MANAGER)
    public RespMsg<Void> del(@RequestParam(required = true) Integer id) {
        Bedept bedept = bedeptService.findOne(id);
        if (ObjectUtil.isEmpty(bedept)) {
            return RespMsg.error(HttpStatus.HTTP_BAD_REQUEST, "该部门信息不存在，请刷新列表！");
        }
        LambdaQueryWrapper<Bestep> queryWrapper = Condition.<Bestep>create().lambda().eq(Bestep::getEsupdept, id).or()
                .eq(Bestep::getEsopdept, id).or().eq(Bestep::getBepcdpt0, id);
        if (bestepService.count(queryWrapper) > 0) {
            return RespMsg.error(HttpStatus.HTTP_BAD_REQUEST, "该部门信息已被使用，不可删除！");
        }
        List<Integer> idList = new LinkedList<Integer>();
        idList.add(id);
        // 找到子节点
        Integer[] pids = bedept.getPids();
        String column = Bedept.BDPNTIDS + "[:" + pids.length + "]";
        List<Bedept> songList = bedeptService.list(Condition.<Bedept>create().select(Bedept.BEDID).eq(column, pids));
        if (ObjectUtil.isNotEmpty(songList)) {
            List<Integer> songIds = songList.stream().map(Bedept::getBedid).collect(Collectors.toList());
            idList.addAll(songIds);
        }
        // 同步删除两表数据
        bedeptService.removeByIds(idList);
        for (Integer did : idList) {
            identityService.deleteGroup(did.toString());
        }
        return RespMsg.ok();
    }

    @Authorize
    @ApiOperation(value = "展示部门树")
    @GetMapping("all")
    public RespMsg<List<BedeptDto>> findAll() {
        List<Bedept> list = bedeptService.list(Condition.<Bedept>create().orderByAsc(Bedept.BEDID));
        return RespMsg
                .ok(BedeptDto.makeTree(list.stream().map(item -> new BedeptDto(item)).collect(Collectors.toList())));
    }

    @Authorize
    @ApiOperation(value = "展示有下级部门树")
    @GetMapping("all2")
    public RespMsg<List<BedeptDto>> findAll2() {
        List<Bedept> bdpntidList = bedeptService.list(Condition.<Bedept>create().select(Bedept.BEDID, Bedept.BDPNTID));
        Set bdpntidset = new HashSet();
        bdpntidList.forEach(bedept -> {
            Integer id = bedept.getBdpntid();
            if (null != id) {
                bdpntidset.add(id);
            }
        });
        List<Bedept> list = bedeptService.list(Condition.<Bedept>create().in(Bedept.BEDID, bdpntidset).orderByAsc(Bedept.BEDID));
        return RespMsg
                .ok(BedeptDto.makeTree(list.stream().map(item -> new BedeptDto(item)).collect(Collectors.toList())));
    }

    @Authorize
    @ApiOperation(value = "获取子部门列表")
    @GetMapping("sons")
    @ApiImplicitParam(name = "bedid", value = "父级部门id", dataType = "Integer", required = true)
    public RespMsg<List<Bedept>> findSons(Integer bedid) {
        if (null == SecurityUtil.currentUserId()) {
            throw new ServiceException("当前用户未登录");
        }
        Bedept bedept = eventService.getBedptByUserId(SecurityUtil.currentUserId());
        QueryWrapper<Bedept> wrapper = Condition.<Bedept>create().eq(Bedept.BDPNTID, bedid);
        List<Bedept> list = bedeptService.list(wrapper.ne(Bedept.BEDID, bedept.getBedid()).orderByAsc(Bedept.BEDID));
        return RespMsg.ok(list);
    }

    @Authorize
    @ApiOperation(value = "展示一二级部门树")
    @GetMapping("1or2")
    public RespMsg<List<BedeptDto>> find1or2() {
        String column = "array_length(bdpntids, 1)";
        List<Bedept> list = bedeptService.list(Condition.<Bedept>create().isNull(column).or().lt(column, 2));
        return RespMsg
                .ok(BedeptDto.makeTree(list.stream().map(item -> new BedeptDto(item)).collect(Collectors.toList())));
    }

    @Authorize
    @GetMapping("query")
    @ApiOperation(value = "分页查询部门信息列表")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdnm", value = "部门名称", dataType = "String", required = false),
            @ApiImplicitParam(name = "bdtype", value = "部门类型", dataType = "Integer", required = false),
            @ApiImplicitParam(name = "size", value = "每页条数，默认10条", dataType = "int", required = true),
            @ApiImplicitParam(name = "current", value = "第几页", dataType = "int", required = true)})
    public RespMsg<IPage<Bedept>> query(@RequestParam(defaultValue = "10", required = true) Integer size,
                                        @RequestParam(defaultValue = "1", required = true) Integer current,
                                        @RequestParam(required = false) String bdnm, @RequestParam(required = false) Integer bdtype) {
        IPage<Bedept> page = new Page<>(current, size);
        QueryWrapper<Bedept> queryWrapper = Condition.<Bedept>create()
                .like(StringUtils.isNotEmpty(bdnm), Bedept.BDNM, bdnm).eq(null != bdtype, Bedept.BDTYPE, bdtype)
                .orderByDesc(Bedept.BEDID);
        return RespMsg.ok(bedeptService.page(page, queryWrapper));
    }

    /*
     *
     *
     *
     * 管理系统同步部门 事件开发初期已经分库，为了保证系统的完整性和稳定 通过服务同步修改
     *
     *
     */
    @ApiOperation(value = "编辑部门")
    @PostMapping(value = "edit")
    @ApiImplicitParams({@ApiImplicitParam(name = "bedid", value = "部门ID", dataType = "Integer", required = false),
            @ApiImplicitParam(name = "bdnm", value = "部门名称", dataType = "String", required = false)})
    @Transactional(value = TransConstant.EVENT_TRANSACTION_MANAGER)
    public RespMsg<Void> edit(Integer bedid, String bdnm) {
        Bedept ent = new Bedept();
        ent.setBedid(bedid);
        ent.setBdnm(bdnm);
        bedeptService.updateById(ent);

        identityService.deleteGroup(bedid.toString()); // 删除再插入
        Group group = identityService.newGroup(bedid.toString());
        group.setName(bdnm);
        identityService.saveGroup(group);
        return RespMsg.ok();
    }

    @ApiOperation(value = "移除部门")
    @PostMapping("remove")
    @ApiImplicitParam(name = "bedid", value = "部门ID", dataType = "Integer", required = false)
    @Transactional(value = TransConstant.EVENT_TRANSACTION_MANAGER)
    public RespMsg<Void> remove(Integer bedid) {
        bedeptService.removeById(bedid);
        bedeptService.removeTreeById(bedid);// 移除下级所有部门

        identityService.deleteGroup(bedid.toString());// 删下级
        List<Bedept> bedepts = bedeptService.getChildById(bedid);
        for (Bedept bedept : bedepts) {
            identityService.deleteGroup(bedept.getBedid().toString());//
        }
        return RespMsg.ok();
    }

    @ApiOperation(value = "添加子部门")
    @GetMapping("add")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdpntid", value = "上级部门ID", dataType = "Integer", required = false),
            @ApiImplicitParam(name = "bdnm", value = "部门名称", dataType = "String", required = false)})
    @Transactional(value = TransConstant.EVENT_TRANSACTION_MANAGER)
    public RespMsg<Void> add(Integer bdpntid, String bdnm) {
        Bedept pnt = bedeptService.getById(bdpntid);
        Integer[] pntids = addArrary(pnt.getBdpntids(), pnt.getBedid());// 累加上级

        // 保存
        Bedept newnt = new Bedept();
        newnt.setBdpntid(bdpntid);
        newnt.setBdpntids(pntids);
        newnt.setBdnm(bdnm);
        newnt.setBdtype(pnt.getBdtype() + 1);// 层级+1
        bedeptService.save(newnt);

        Group group = identityService.newGroup(newnt.getBedid().toString());
        group.setType(String.valueOf(pnt.getBdtype() + 1));
        group.setName(bdnm);
        identityService.saveGroup(group);
        return RespMsg.ok();
    }

    @ApiOperation(value = "添加一级部门")
    @GetMapping("addfirst")
    @ApiImplicitParam(name = "bdnm", value = "部门名称", dataType = "String", required = false)
    @Transactional(value = TransConstant.EVENT_TRANSACTION_MANAGER)
    public RespMsg<Void> add(String bdnm) {
        // 保存
        Bedept newnt = new Bedept();
        newnt.setBdnm(bdnm);
        newnt.setBdtype(1);// 层级+1
        bedeptService.save(newnt);

        Group group = identityService.newGroup(newnt.getBedid().toString());
        group.setType("1");
        group.setName(bdnm);
        identityService.saveGroup(group);
        return RespMsg.ok();
    }

    private Integer[] addArrary(Integer[] bdpntids, Integer dpid) {
        if (null == bdpntids || bdpntids.length < 1) {
            return new Integer[]{dpid};
        }
        Integer[] result = Arrays.copyOf(bdpntids, bdpntids.length + 1);
        result[result.length - 1] = dpid;
        return result;
    }
}
