package com.digitalchina.admin.mid.controller.sign;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.http.HttpStatus;
import com.baomidou.mybatisplus.core.conditions.Condition;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.digitalchina.admin.mid.dto.SignTreeVerDto;
import com.digitalchina.admin.mid.entity.SignTree;
import com.digitalchina.admin.mid.entity.SignTreeVer;
import com.digitalchina.admin.mid.service.SignTreeService;
import com.digitalchina.admin.mid.service.SignTreeVerService;
import com.digitalchina.common.web.RespMsg;
import com.digitalchina.modules.constant.TransConstant;
import com.digitalchina.modules.constant.TreeVerConstant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 *  树版本 前端控制器
 * </p>
 *
 * @author Warrior
 * @since 2019-10-10
 */
@RestController
@RequestMapping("admin/signTreeVer")
//@Authorize
@Api(tags ="体征树版本管理接口")
public class SignTreeVerController {
    @Autowired
    private SignTreeVerService signTreeVerService;
    @Autowired
    private SignTreeService signTreeService;

    @PostMapping(value = "list", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ApiOperation("获取树版本信息列表")
    @ApiImplicitParams({@ApiImplicitParam(name = "name", value = "版本名称", dataType = "String", required = false),
            @ApiImplicitParam(name = "status", value = "状态", dataType = "Integer", required = false),
            @ApiImplicitParam(name = "size", value = "每页条数，默认10条", dataType = "Integer", defaultValue = "10", required = false),
            @ApiImplicitParam(name = "current", value = "第几页,默认第一页", dataType = "Integer", required = false),})
    public RespMsg<IPage<SignTreeVer>> list(@RequestParam(required = false)String name, @RequestParam(required = false) Integer status,
                                            @RequestParam(defaultValue = "10") Integer size, @RequestParam(defaultValue = "1") Integer current) {
        IPage<SignTreeVer> page = new Page<>(current, size);
        LambdaQueryWrapper<SignTreeVer> queryWrapper = Condition.<SignTreeVer>lambda()
                .like(!ObjectUtil.isEmpty(name),SignTreeVer::getName, name)
                .eq(!ObjectUtil.isEmpty(status),SignTreeVer::getStatus, status)
                .orderByAsc(SignTreeVer::getTid);
        return RespMsg.ok(signTreeVerService.page(page, queryWrapper));
    }

    @GetMapping("find")
    @ApiOperation("获取树版本信息（单个）")
    @ApiImplicitParam(name = "id", required = true, value = "主键")
    public RespMsg<SignTreeVer> get(Integer id) {
        SignTreeVer signTreeVer = signTreeVerService.getById(id);
        return RespMsg.ok(signTreeVer);
    }

    @PostMapping("save")
    @ApiOperation("新增树版本信息")
    @Transactional(value = TransConstant.MID_TRANSACTION_MANAGER, rollbackFor = Exception.class)
    public RespMsg<SignTreeVer> saveOne( @Valid @RequestBody SignTreeVerDto entity) {
        int count = signTreeVerService.count(Condition.<SignTreeVer>lambda().eq(SignTreeVer::getName, entity.getName()));
        if(count>0){
            return RespMsg.error(HttpStatus.HTTP_BAD_REQUEST,"版本名称不能重复！");
        }
        SignTreeVer signTreeVer = entity.toSignTreeVer();
        signTreeVer.setStatus(0);
        signTreeVerService.save(signTreeVer);
        if(ObjectUtil.isNotEmpty(entity.getPid())) {
            //按指定模板复制版本树  使用广度优先遍历
            signTreeService.copyTree(entity.getPid(),signTreeVer.getTid());
        }
        return RespMsg.ok();
    }

    @PostMapping("update")
    @ApiOperation("修改单个树版本信息")
    public RespMsg updateOne(@Valid @RequestBody SignTreeVer entity) {
        int count = signTreeVerService.count(Condition.<SignTreeVer>lambda()
                .ne(SignTreeVer::getTid, entity.getTid())
                .eq(SignTreeVer::getName,entity.getName()));
        if(count>0){
            return RespMsg.error(HttpStatus.HTTP_BAD_REQUEST,"版本名称不能重复！");
        }
        signTreeVerService.updateById(entity);
        return RespMsg.ok(entity);
    }

    @PostMapping(value = "removeById", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ApiOperation("删除树版本信息数据")
    @ApiImplicitParam(name = "tid", value = "主键ID", dataType = "Integer", required = true)
    @CacheEvict(value = TreeVerConstant.TREE_VERSION,allEntries=true)
    public RespMsg delete(Integer tid) {
        LambdaQueryWrapper<SignTreeVer> lambdaQueryWrapper = Condition.<SignTreeVer>lambda().eq(SignTreeVer::getTid, tid);
        SignTreeVer signTreeVer = signTreeVerService.getOne(lambdaQueryWrapper);
        if(ObjectUtil.isEmpty(signTreeVer)){
            return RespMsg.error(HttpStatus.HTTP_BAD_REQUEST,"所选记录不存在,请刷新列表！");
        }
        if(signTreeVer.getStatus() == 1){
            return RespMsg.error(HttpStatus.HTTP_BAD_REQUEST,"该版本已启用，不可删除!");
        }
        int count = signTreeService.count(Condition.<SignTree>lambda().eq(SignTree::getTid,tid));
        if(count > 0){
            return RespMsg.error(HttpStatus.HTTP_BAD_REQUEST,"该版本被使用过，不可删除!");
        }
        signTreeVerService.remove(lambdaQueryWrapper);
        return RespMsg.ok();
    }

    @PostMapping(value = "open", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ApiOperation("启用版本")
    @ApiImplicitParam(name = "tid", value = "主键ID", dataType = "Integer", required = true)
    @Transactional(value = TransConstant.MID_TRANSACTION_MANAGER, rollbackFor = Exception.class)
    @CacheEvict(value = TreeVerConstant.TREE_VERSION,allEntries=true)
    public RespMsg open(Integer tid) {
        SignTreeVer signTreeVer = signTreeVerService.getById(tid);
        if(ObjectUtil.isEmpty(signTreeVer)){
            return RespMsg.error(HttpStatus.HTTP_BAD_REQUEST,"所选记录不存在,请刷新列表！");
        }
        signTreeVerService.update(SignTreeVer.builder().status(0).build(),null);
        signTreeVer.setStatus(1);
        signTreeVerService.updateById(signTreeVer);
        return RespMsg.ok();
    }

    @GetMapping("queryAll")
    @ApiOperation(value = "查询所有树版本列表，用于字典")
    public RespMsg<List<SignTreeVer>> queryAll() {
        return RespMsg.ok(signTreeVerService.list(null));
    }
}
