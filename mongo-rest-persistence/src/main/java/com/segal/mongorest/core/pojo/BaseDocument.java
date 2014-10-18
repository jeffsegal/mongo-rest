package com.segal.mongorest.core.pojo;

import com.segal.mongorest.core.service.CreateChecks;
import com.segal.mongorest.core.service.UpdateChecks;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.annotation.Id;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.validation.constraints.Min;
import javax.validation.constraints.Null;
import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: Jeff
 * Date: 6/22/12
 * Time: 11:48 AM
 * To change this template use File | Settings | File Templates.
 */
public abstract class BaseDocument implements Cloneable, Serializable {

	private static final long serialVersionUID = -6777426265591642262L;

	@Min(0)
	protected long createdMillis;

	@Min(0)
	protected long lastUpdatedMillis;

	protected String owner;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@NotBlank(groups = UpdateChecks.class)
	@Null(groups = CreateChecks.class)
	protected String id;

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

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
