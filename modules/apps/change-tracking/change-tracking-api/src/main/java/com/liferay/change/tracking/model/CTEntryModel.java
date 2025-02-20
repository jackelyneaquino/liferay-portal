/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.change.tracking.model;

import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.MVCCModel;
import com.liferay.portal.kernel.model.ShardedModel;

import java.util.Date;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The base model interface for the CTEntry service. Represents a row in the &quot;CTEntry&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This interface and its corresponding implementation <code>com.liferay.change.tracking.model.impl.CTEntryModelImpl</code> exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in <code>com.liferay.change.tracking.model.impl.CTEntryImpl</code>.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see CTEntry
 * @generated
 */
@ProviderType
public interface CTEntryModel
	extends BaseModel<CTEntry>, MVCCModel, ShardedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. All methods that expect a ct entry model instance should use the {@link CTEntry} interface instead.
	 */

	/**
	 * Returns the primary key of this ct entry.
	 *
	 * @return the primary key of this ct entry
	 */
	public long getPrimaryKey();

	/**
	 * Sets the primary key of this ct entry.
	 *
	 * @param primaryKey the primary key of this ct entry
	 */
	public void setPrimaryKey(long primaryKey);

	/**
	 * Returns the mvcc version of this ct entry.
	 *
	 * @return the mvcc version of this ct entry
	 */
	@Override
	public long getMvccVersion();

	/**
	 * Sets the mvcc version of this ct entry.
	 *
	 * @param mvccVersion the mvcc version of this ct entry
	 */
	@Override
	public void setMvccVersion(long mvccVersion);

	/**
	 * Returns the ct entry ID of this ct entry.
	 *
	 * @return the ct entry ID of this ct entry
	 */
	public long getCtEntryId();

	/**
	 * Sets the ct entry ID of this ct entry.
	 *
	 * @param ctEntryId the ct entry ID of this ct entry
	 */
	public void setCtEntryId(long ctEntryId);

	/**
	 * Returns the company ID of this ct entry.
	 *
	 * @return the company ID of this ct entry
	 */
	@Override
	public long getCompanyId();

	/**
	 * Sets the company ID of this ct entry.
	 *
	 * @param companyId the company ID of this ct entry
	 */
	@Override
	public void setCompanyId(long companyId);

	/**
	 * Returns the user ID of this ct entry.
	 *
	 * @return the user ID of this ct entry
	 */
	public long getUserId();

	/**
	 * Sets the user ID of this ct entry.
	 *
	 * @param userId the user ID of this ct entry
	 */
	public void setUserId(long userId);

	/**
	 * Returns the user uuid of this ct entry.
	 *
	 * @return the user uuid of this ct entry
	 */
	public String getUserUuid();

	/**
	 * Sets the user uuid of this ct entry.
	 *
	 * @param userUuid the user uuid of this ct entry
	 */
	public void setUserUuid(String userUuid);

	/**
	 * Returns the create date of this ct entry.
	 *
	 * @return the create date of this ct entry
	 */
	public Date getCreateDate();

	/**
	 * Sets the create date of this ct entry.
	 *
	 * @param createDate the create date of this ct entry
	 */
	public void setCreateDate(Date createDate);

	/**
	 * Returns the modified date of this ct entry.
	 *
	 * @return the modified date of this ct entry
	 */
	public Date getModifiedDate();

	/**
	 * Sets the modified date of this ct entry.
	 *
	 * @param modifiedDate the modified date of this ct entry
	 */
	public void setModifiedDate(Date modifiedDate);

	/**
	 * Returns the ct collection ID of this ct entry.
	 *
	 * @return the ct collection ID of this ct entry
	 */
	public long getCtCollectionId();

	/**
	 * Sets the ct collection ID of this ct entry.
	 *
	 * @param ctCollectionId the ct collection ID of this ct entry
	 */
	public void setCtCollectionId(long ctCollectionId);

	/**
	 * Returns the model class name ID of this ct entry.
	 *
	 * @return the model class name ID of this ct entry
	 */
	public long getModelClassNameId();

	/**
	 * Sets the model class name ID of this ct entry.
	 *
	 * @param modelClassNameId the model class name ID of this ct entry
	 */
	public void setModelClassNameId(long modelClassNameId);

	/**
	 * Returns the model class pk of this ct entry.
	 *
	 * @return the model class pk of this ct entry
	 */
	public long getModelClassPK();

	/**
	 * Sets the model class pk of this ct entry.
	 *
	 * @param modelClassPK the model class pk of this ct entry
	 */
	public void setModelClassPK(long modelClassPK);

	/**
	 * Returns the model mvcc version of this ct entry.
	 *
	 * @return the model mvcc version of this ct entry
	 */
	public long getModelMvccVersion();

	/**
	 * Sets the model mvcc version of this ct entry.
	 *
	 * @param modelMvccVersion the model mvcc version of this ct entry
	 */
	public void setModelMvccVersion(long modelMvccVersion);

	/**
	 * Returns the change type of this ct entry.
	 *
	 * @return the change type of this ct entry
	 */
	public int getChangeType();

	/**
	 * Sets the change type of this ct entry.
	 *
	 * @param changeType the change type of this ct entry
	 */
	public void setChangeType(int changeType);

	@Override
	public CTEntry cloneWithOriginalValues();

	public default String toXmlString() {
		return null;
	}

}