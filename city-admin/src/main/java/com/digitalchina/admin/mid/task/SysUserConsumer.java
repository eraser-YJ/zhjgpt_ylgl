package com.digitalchina.admin.mid.task;

import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.Condition;
import com.digitalchina.admin.remoteservice.CityEventService;
import com.digitalchina.common.exception.ServiceException;
import com.digitalchina.modules.constant.enums.SysCodeEnum;
import com.digitalchina.modules.entity.SysRole;
import com.digitalchina.modules.entity.SysRoleUser;
import com.digitalchina.modules.entity.SysUser;
import com.digitalchina.modules.service.SysRoleAppService;
import com.digitalchina.modules.service.SysRoleService;
import com.digitalchina.modules.service.SysRoleUserService;
import com.digitalchina.modules.service.SysUserService;
import com.digitalchina.modules.utils.AesUtils;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 统一门户用户同步
 * </p>
 *
 * @author liuping
 * @since 2019-11-01
 */
@Profile({"remotedev", "prod"})
@Component
public class SysUserConsumer {

	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private SysRoleService sysRoleService;
	@Autowired
	private SysRoleUserService sysRoleUserService;
	@Autowired
	private SysRoleAppService sysRoleAppService;
	@Autowired
	private CityEventService cityEventService;

	@Value("${sso-user-queue.aes-salt}")
	private String salt;
	@Value("${spring.profiles.active}")
	private String active;

	private static final Logger LOG = LoggerFactory.getLogger(SysUserConsumer.class);

	// ackMode 指定手动确认(版本低了) exclusive 独占队列，只会发到一个消费者
	@RabbitListener(queues = "${sso-user-queue.name}", exclusive = true)
	@RabbitHandler
	@Transactional(rollbackFor = Exception.class)
	public void syncUserInfo(Message message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag)
			throws IOException {
//		if (!ProfileEnum.TEST.getCode().equals(active)) {
//			// 不能多环境消费
//			return;
//		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("同步统一门户用户信息中.....");
		}
		String aesEncrpt = new String(message.getBody());
		try {
			String decrptStr = AesUtils.aesDecryptBySalt(aesEncrpt, salt);
			if (LOG.isDebugEnabled()) {
				LOG.debug("用户信息为:{}", decrptStr);
			}
			JSONObject jsonobject = JSONObject.parseObject(decrptStr);
			LOG.error("用户信息为:{}", decrptStr);
			/*
			 * userName：用户名 userGuid：用户唯一uuid realName：用户真实姓名 phone：用户电话 description：描述，备注
			 * optType：操作类型 订阅应用需要根据操作类型字段即optType进行相应的操作 optType=1 表示此用户是新增
			 * optType=2表示此用户修改 optType=3表示删除此用户 此时只有userGuid字段有信息，其它信息为空。
			 */

			String userGuid = jsonobject.getString("userGuid");
			String optType = jsonobject.getString("optType");
			String userName = jsonobject.getString("userName");
			String realName = jsonobject.getString("realName");
			String phone = jsonobject.getString("phone");
			String description = jsonobject.getString("description");
			Validator.validateNotNull(userGuid, "userGuid为空");
			Validator.validateNotNull(optType, "optType为空");
			SysUser sysUser = new SysUser();
			sysUser.setLogin(userName);
			sysUser.setName(realName);
			sysUser.setSsoid(userGuid);
			sysUser.setTel(phone);
			sysUser.setMark(description);
			switch (optType) {
			// 去掉1和2类型判断，如果user为空则添加，不为空则修改
			/*case "1":
				sysUser.setStatus(0);
				sysUserService.save(sysUser);
				// 赋予默认角色
				List<SysRole> defaultRoles = sysRoleService.findDefaultRoles();

				if (ObjectUtil.isNotEmpty(defaultRoles)) {
					List<SysRoleUser> sysRoleUsers = defaultRoles.stream()
							.map(item -> new SysRoleUser(sysUser.getId(), item.getId())).collect(Collectors.toList());
					for (SysRoleUser sr : sysRoleUsers) {
						sysRoleUserService.save(sr);
					}
					List<Integer> ids = defaultRoles.stream().map(SysRole::getId).collect(Collectors.toList());
					Integer[] roleIds = ids.toArray(new Integer[ids.size()]);
					// 如果有事件系统的权限则添加到事件系统，异常手动处理
					int cnt = sysRoleAppService.selectSysCodeCnt(roleIds, SysCodeEnum.CITYEVENTNEW.getCode());
					if (cnt > 0) {
						try {
							SysUser sysUser2 = sysUserService.loadUserRoleById(sysUser.getId());
							cityEventService.syncUserInfo(sysUser2);
						} catch (Exception e) {
							LOG.error("用户同步至事件系统失败：{},请手动重新授权！", userGuid);
						}
					}
				}

				break;
			case "2":
				sysUserService.update(sysUser, Condition.<SysUser>create().lambda().eq(SysUser::getSsoid, userGuid));
				break;*/
			case "3":
				SysUser deleteUser = sysUserService
						.getOne(Condition.<SysUser>create().lambda().eq(SysUser::getSsoid, userGuid));
				if (null != deleteUser) {
					sysUserService.removeById(deleteUser);
					sysRoleUserService.remove(
							Condition.<SysRoleUser>create().lambda().eq(SysRoleUser::getUid, deleteUser.getId()));
				} else {
					LOG.error("未找到用户：{},未被删除", userGuid);
				}
				break;
			default:
				SysUser user = sysUserService.getUserBySsoid(userGuid);
				// 根据ssoid查询user,为空则新增
				if (ObjectUtil.isEmpty(user)) {
					sysUser.setStatus(0);
					sysUserService.save(sysUser);
					// 赋予默认角色
					List<SysRole> defaultRoles = sysRoleService.findDefaultRoles();

					if (ObjectUtil.isNotEmpty(defaultRoles)) {
						List<SysRoleUser> sysRoleUsers = defaultRoles.stream()
								.map(item -> new SysRoleUser(sysUser.getId(), item.getId())).collect(Collectors.toList());
						for (SysRoleUser sr : sysRoleUsers) {
							sysRoleUserService.save(sr);
						}
						List<Integer> ids = defaultRoles.stream().map(SysRole::getId).collect(Collectors.toList());
						Integer[] roleIds = ids.toArray(new Integer[ids.size()]);
						// 如果有事件系统的权限则添加到事件系统，异常手动处理
						int cnt = sysRoleAppService.selectSysCodeCnt(roleIds, SysCodeEnum.CITYEVENTNEW.getCode());
						if (cnt > 0) {
							try {
								SysUser sysUser2 = sysUserService.loadUserRoleById(sysUser.getId());
								cityEventService.syncUserInfo(sysUser2);
							} catch (Exception e) {
								LOG.error("用户同步至事件系统失败：{},请手动重新授权！", userGuid);
							}
						}
					}
				} else { // user不为空则修改
					sysUserService.update(sysUser, Condition.<SysUser>create().lambda().eq(SysUser::getSsoid, userGuid));
				}
//				throw new ServiceException("操作类型不明：" + optType);
			}

			channel.basicAck(tag, false);
		} catch (Exception e) {
			LOG.error("同步统一门户用户信息异常：{}", e.getMessage());
			LOG.error("失败的用户信息为:{}", aesEncrpt);
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			channel.basicNack(tag, false, false);
		}
	}

}
