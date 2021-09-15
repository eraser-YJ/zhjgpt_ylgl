package com.digitalchina.common.base.entity;

import java.io.Serializable;

import cn.hutool.core.util.ObjectUtil;

/**
 * <p>
 * ================================================
 * <p>
 * Title: 抽象实体基类
 * <p>
 * Description:
 * <p>
 * Date: 2018/2/25 15:48
 * <p>
 * ================================================
 *
 * @author Hope Chen
 * @version 1.0
 */
public abstract class AbstractEntity<ID extends Serializable> implements BaseEntity {

	private static final long serialVersionUID = -2790714035519460609L;

	public abstract ID getId();

	public abstract void setId(ID id);

	@Override
	public boolean equals(Object obj) {

		if (null == obj) {
			return false;
		}

		if (this == obj) {
			return true;
		}

		if (!getClass().equals(obj.getClass())) {
			return false;
		}

		@SuppressWarnings("unchecked")
		AbstractEntity<ID> that = (AbstractEntity<ID>) obj;

		return null != this.getId() && this.getId().equals(that.getId());
	}

	@Override
	public int hashCode() {

		int hashCode = 17;

		hashCode += null == getId() ? 0 : getId().hashCode() * 31;

		return hashCode;
	}

	@Override
	public String toString() {
		return ObjectUtil.toString(this);
	}
}
