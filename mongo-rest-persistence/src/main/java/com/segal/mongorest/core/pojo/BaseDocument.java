package com.segal.mongorest.core.pojo;

import com.mongodb.util.JSON;
import com.segal.mongorest.core.service.CreateChecks;
import com.segal.mongorest.core.service.UpdateChecks;
import org.apache.commons.beanutils.BeanMap;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.annotation.Id;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.validation.constraints.Null;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Jeff
 * Date: 6/22/12
 * Time: 11:48 AM
 * To change this template use File | Settings | File Templates.
 */
public abstract class BaseDocument implements Cloneable, Serializable {

	private static final long serialVersionUID = -6777426265591642262L;

	protected long createdMillis;

	protected long lastUpdatedMillis;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@NotBlank(groups = UpdateChecks.class)
	@Null(groups = CreateChecks.class)
	protected String id;

//	public Map asMap() {
//		Map map = new HashMap(new BeanMap(this));
//		map.remove("class");
//		return map;
//	}
//
//	@Override
//	public String toString() {
//		return JSON.serialize(asMap());
//	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public long getCreatedMillis() {
		return createdMillis;
	}

	public long getLastUpdatedMillis() {
		return lastUpdatedMillis;
	}

	public void setCreatedMillis(long createdMillis) {
		this.createdMillis = createdMillis;
	}

	public void setLastUpdatedMillis(long lastUpdatedMillis) {
		this.lastUpdatedMillis = lastUpdatedMillis;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
				.append("createdMillis", createdMillis)
				.append("lastUpdatedMillis", lastUpdatedMillis)
				.append("id", id)
				.toString();
	}
}
