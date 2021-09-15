package com.digitalchina.admin.remoteservice;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.digitalchina.admin.mid.dto.AdmdivDto;
import com.digitalchina.admin.mid.entity.event.Admdiv;
import com.digitalchina.admin.mid.entity.event.Bedept;
import com.digitalchina.admin.mid.entity.event.Befrom;
import com.digitalchina.admin.mid.entity.event.Betype;
import com.digitalchina.admin.mid.entity.event.LoginUser;
import com.digitalchina.admin.mid.entity.event.Workflow;
import com.digitalchina.common.web.RespMsg;
import com.digitalchina.modules.config.FeignMultipartSupportConfig;
import com.digitalchina.modules.dto.BedeptDto;
import com.digitalchina.modules.entity.SysUser;

import io.swagger.annotations.ApiOperation;

/**
 * 事件 远程接口 接口改动返回值,两边返回的实体类也对应修改下
 *
 * @author liuping
 * @since 2019/9/16
 */
@FeignClient(value = "cityevent2", url = "${cityevent2.url:}", configuration = FeignMultipartSupportConfig.class)
public interface CityEventService {

	/**
	 * 同步用户信息至本系统
	 * 
	 * @param sysUser
	 * @return
	 */
	@PostMapping("/loginUser/syncUserInfo")
	RespMsg<Void> syncUserInfo(@RequestBody SysUser sysUser);

	/**
	 * 给用户指定部门
	 * 
	 * @param uid 用户Id
	 * @param did 部门id
	 * @return
	 */
	@GetMapping(value = "/loginUser/empower")
	RespMsg<Void> empowerDept(@RequestParam(value = "uid", required = true) Integer uid,
			@RequestParam(value = "did", required = true) Integer did);

	/**
	 *
	 * @param size    每页条数，默认10条
	 * @param current 第几页
	 * @param login   登录名
	 * @param name    用户名称
	 * @param dept    所属部门
	 * @return
	 */
	@GetMapping(value = "/loginUser/query")
	RespMsg<Page<LoginUser>> queryLoginUser(
			@RequestParam(value = "size", defaultValue = "10", required = true) Integer size,
			@RequestParam(value = "current", defaultValue = "1", required = true) Integer current,
			@RequestParam(value = "login", required = false) String login,
			@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "dept", required = false) String dept);

	/**
	 * 获取行政区划数据（列表）
	 * 
	 * @param adnm    区划名称
	 * @param adflnm  区划全称
	 * @param adlev   行政区级别
	 * @param adpid   父级ID
	 * @param size    每页条数，默认10条
	 * @param current 第几页,默认第一页
	 * @return
	 */
	@GetMapping("/admdiv/list")
	RespMsg<Page<Admdiv>> listAdmdiv(@RequestParam(value = "adnm") String adnm,
			@RequestParam(value = "adflnm") String adflnm, @RequestParam(value = "adlev") Integer adlev,
			@RequestParam(value = "adpid") Integer adpid,
			@RequestParam(value = "size", defaultValue = "10") Integer size,
			@RequestParam(value = "current", defaultValue = "1") Integer current);

	/**
	 * 获取行政区划数据（不分页）
	 * 
	 * @param adnm   区划名称
	 * @param adflnm 区划全称
	 * @param adlev  行政区级别
	 * @param adpid  父级ID
	 * @return
	 */
	@GetMapping("/admdiv/listall")
	RespMsg<List<Admdiv>> listallAdmdiv(@RequestParam(value = "adnm") String adnm,
			@RequestParam(value = "adflnm") String adflnm, @RequestParam(value = "adlev") Integer adlev,
			@RequestParam(value = "adpid") Integer adpid);

	/**
	 * 获取行政区划数据（单个）
	 * 
	 * @param adid 行政区划ID
	 * @return
	 */
	@GetMapping("/admdiv/get")
	RespMsg<Admdiv> getAdmdiv(@RequestParam(value = "adid") Integer adid);

	/**
	 * 新增行政区划数据
	 * 
	 * @param admdiv
	 * @return
	 */
	@PostMapping("/admdiv/save")
	RespMsg<Admdiv> saveOneAdmdiv(@RequestBody Admdiv admdiv);

	/**
	 * 修改单个行政区划数据
	 * 
	 * @param admdiv
	 * @return
	 */
	@PostMapping("/admdiv/update")
	RespMsg<Admdiv> updateOneAdmdiv(@RequestBody Admdiv admdiv);

	/**
	 * 删除单个行政区划数据
	 * 
	 * @param adid
	 * @return
	 */
	@GetMapping("/admdiv/removeById")
	RespMsg<Void> deleteAdmdiv(@RequestParam(value = "adid") Integer adid);

	@GetMapping("/admdiv/all")
	RespMsg<List<AdmdivDto>> findAdmdivTree();

	/**
	 * 创建或者更新部门信息
	 * 
	 * @param bedept
	 * @return
	 */
	@PostMapping(value = "/bedept/saveorupdate")
	RespMsg<Void> saveOrUpdateBedept(@RequestBody Bedept bedept);

	/**
	 * 查找单个部门信息
	 * 
	 * @param id 主键id
	 * @return
	 */
	@GetMapping("/bedept/find")
	RespMsg<Bedept> findBedept(@RequestParam(value = "id", required = true) Integer id);

	/**
	 * 删除单个部门信息
	 * 
	 * @param id 主键id
	 * @return
	 */
	@GetMapping(value = "/bedept/delete")
	RespMsg delBedept(@RequestParam(value = "id", required = true) Integer id);

	/**
	 * 展示部门树
	 * 
	 * @return
	 */
	@GetMapping("/bedept/all")
	RespMsg<List<BedeptDto>> findAllBedept();

	/**
	 *
	 * @param bedid 父级部门id
	 * @return
	 */
	@GetMapping("/bedept/sons")
	RespMsg<List<Bedept>> findSonsBedept(@RequestParam(value = "bedid") Integer bedid);

	@GetMapping("/bedept/1or2")
	RespMsg<List<BedeptDto>> find1or2();

	/**
	 * 分页查询部门信息列表
	 * 
	 * @param size    每页条数，默认10条
	 * @param current 第几页
	 * @param bdnm    部门名称
	 * @param bdtype  部门类型
	 * @return
	 */
	@GetMapping("/bedept/query")
	RespMsg<Page<Bedept>> queryBedept(@RequestParam(value = "size", defaultValue = "10", required = true) Integer size,
			@RequestParam(value = "current", defaultValue = "1", required = true) Integer current,
			@RequestParam(value = "bdnm", required = false) String bdnm,
			@RequestParam(value = "bdtype", required = false) Integer bdtype);

	/**
	 * 创建或者更新事件来源
	 * 
	 * @param befrom
	 * @return
	 */
	@PostMapping("/befrom/saveorupdate")
	RespMsg<Void> saveOrUpdateBefrom(@RequestBody Befrom befrom);

	/**
	 * 查找单个事件来源
	 * 
	 * @param id 主键id
	 * @return
	 */
	@GetMapping("/befrom/find")
	RespMsg<Befrom> findBefrom(@RequestParam(value = "id", required = true) Integer id);

	/**
	 * 删除单个事件来源
	 * 
	 * @param id 主键id
	 * @return
	 */
	@GetMapping(value = "/befrom/delete")
	RespMsg delBefrom(@RequestParam(value = "id", required = true) Integer id);

	/**
	 * 分页查询事件来源
	 * 
	 * @param size    每页条数，默认10条
	 * @param current 第几页
	 * @param efnm    事项来源名称
	 * @param rev     版本
	 * @return
	 */
	@GetMapping("/befrom/query")
	@ApiOperation(value = "分页查询事件来源")
	RespMsg<Page<Befrom>> queryBefrom(@RequestParam(value = "size", defaultValue = "10", required = true) Integer size,
			@RequestParam(value = "current", defaultValue = "1", required = true) Integer current,
			@RequestParam(value = "efnm", required = false) String efnm,
			@RequestParam(value = "rev", required = false) Integer rev);

	/**
	 * 查询事件来源列表（不分页）
	 * 
	 * @param efnm 事项来源名称
	 * @param rev  版本
	 * @return
	 */
	@GetMapping("/befrom/listall")
	RespMsg<List<Befrom>> listallBefrom(@RequestParam(value = "efnm", required = false) String efnm,
			@RequestParam(value = "rev", required = false) Integer rev);

	/**
	 * 创建或者更新事件类型
	 * 
	 * @param betype
	 * @return
	 */
	@PostMapping("/betype/saveorupdate")
	RespMsg<Void> saveOrUpdateBetype(@RequestBody Betype betype);

	/**
	 * 查找单个事件类型
	 * 
	 * @param id 主键id
	 * @return
	 */
	@GetMapping("/betype/find")
	RespMsg<Betype> findBetype(@RequestParam(value = "id", required = true) Integer id);

	/**
	 * 删除单个事件类型
	 * 
	 * @param id 主键id
	 * @return
	 */
	@GetMapping("/betype/delete")
	RespMsg delBetype(@RequestParam(value = "id", required = true) Integer id);

	/**
	 * 分页查询事件类型
	 * 
	 * @param size    每页条数，默认10条
	 * @param current 第几页
	 * @param etnm    事项类型名称
	 * @param rev     版本
	 * @return
	 */
	@GetMapping("/betype/query")
	RespMsg<Page<Betype>> queryBetype(@RequestParam(value = "size", defaultValue = "10", required = true) Integer size,
			@RequestParam(value = "current", defaultValue = "1", required = true) Integer current,
			@RequestParam(value = "etnm", required = false) String etnm,
			@RequestParam(value = "rev", required = false) Integer rev);

	/**
	 * 查询事件类型列表（不分页）
	 * 
	 * @param etnm 事项类型名称
	 * @param rev  版本
	 * @return
	 */
	@GetMapping("/betype/listall")
	RespMsg<List<Betype>> listallBetype(@RequestParam(value = "etnm", required = false) String etnm,
			@RequestParam(value = "rev", required = false) Integer rev);

	/**
	 * 创建或者更新工作流
	 * 
	 * @param workflow
	 * @return
	 */
	@PostMapping("/workflow/saveorupdate")
	RespMsg<Void> saveOrUpdateWorkflow(@RequestBody Workflow workflow);

	/**
	 * 查找单个工作流
	 * 
	 * @param id 主键id
	 * @return
	 */
	@GetMapping(value = "/workflow/find")
	RespMsg<Workflow> findWorkflow(@RequestParam(value = "id", required = true) Integer id);

	/**
	 * 删除单个工作流
	 * 
	 * @param id 主键id
	 * @return
	 */
	@GetMapping(value = "/workflow/delete")
	RespMsg delWorkflow(@RequestParam(value = "id", required = true) Integer id);

	/**
	 * 分页查询工作流
	 * 
	 * @param size    每页条数，默认10条
	 * @param current 第几页
	 * @param wfnm    流程名称
	 * @param wfkey   流程Key
	 * @param rev     版本
	 * @return
	 */
	@GetMapping("/workflow/query")
	RespMsg<Page<Workflow>> queryWorkflow(
			@RequestParam(value = "size", defaultValue = "10", required = true) Integer size,
			@RequestParam(value = "current", defaultValue = "1", required = true) Integer current,
			@RequestParam(value = "wfnm", required = false) String wfnm,
			@RequestParam(value = "wfkey", required = false) String wfkey,
			@RequestParam(value = "rev", required = false) Integer rev);

	/*
	 * 
	 * 
	 * 
	 * 
	 * 统一管理部门
	 * 
	 * 
	 * 
	 * 
	 */
	/**
	 * 编辑部门
	 * 
	 * @return
	 */
	@PostMapping(value = "bedept/edit")
	public RespMsg<Void> edit(@RequestParam("bedid") Integer bedid, @RequestParam("bdnm") String bdnm);

	/**
	 * 移除部门
	 */
	@PostMapping("bedept/remove")
	public RespMsg<Void> remove(@RequestParam("bedid") Integer bedid);

	/**
	 * 添加子部门
	 */
	@ApiOperation(value = "添加子部门")
	@GetMapping("bedept/add")
	public RespMsg<Void> add(@RequestParam("bdpntid") Integer bdpntid, @RequestParam("bdnm") String bdnm);

	/**
	 * 添加子部门
	 */
	@ApiOperation(value = "添加子部门")
	@GetMapping("bedept/addfirst")
	public RespMsg<Void> addfirst(@RequestParam("bdnm") String bdnm);

	/*
	 * 
	 * 
	 * 
	 * 统一管理用户
	 * 
	 * 
	 * 
	 */
	/**
	 * 给用户指定部门
	 */
	@GetMapping(value = "/loginUser/empower2")
	public RespMsg<Void> empowerDept2(@RequestParam("login") String login, @RequestParam("did") Integer did);

	/*
	 * 
	 * 
	 * 统一管理行政区划
	 * 
	 *
	 *
	 */
	@ApiOperation(value = "编辑区域")
	@PostMapping(value = "/admdiv/edit")
	public RespMsg<Void> admedit(@RequestParam("adid") Integer adid, @RequestParam("adnm") String adnm,
			@RequestParam("adflnm") String adflnm);

	@ApiOperation(value = "移除区域")
	@PostMapping("/admdiv/remove")
	public RespMsg<Void> admremove(@RequestParam("adid") Integer adid);

	@ApiOperation(value = "添加子区域")
	@GetMapping("/admdiv/add")
	public RespMsg<Void> admadd(@RequestParam("adpid") Integer adpid, @RequestParam("adnm") String adnm,
			@RequestParam("adflnm") String adflnm, @RequestParam("adid") Integer adid);

	@ApiOperation(value = "添加一级区域")
	@GetMapping("/admdiv/addfirst")
	public RespMsg<Void> admaddfirst(@RequestParam("adnm") String adnm, @RequestParam("adflnm") String adflnm, @RequestParam("adid") Integer adid);

	@GetMapping("/attachment/get")
	ResponseEntity<byte[]> downloadAttachement(@RequestParam("tchmtid") Long tchmtid, @RequestParam(value = "dn", required = false) Boolean download);
}
