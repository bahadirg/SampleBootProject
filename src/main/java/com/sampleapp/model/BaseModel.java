package com.sampleapp.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import lombok.NonNull;

@MappedSuperclass
public class BaseModel implements Serializable {

	@Id
	@NonNull
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "id_Sequence")
	@SequenceGenerator(name = "id_Sequence", sequenceName = "ID_SEQ", allocationSize=1,initialValue = 1)
    private Long      id;

	@Version
	@Column(name = "VERSION")
	private Integer version;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATION_TIME")
	private Date createTime;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "LAST_UPDATE_TIME")
	private Date lastUpdateTime;

	@PreRemove
	public void preRemove() {
		setLastUpdateTime(new Date());
	}

	@PrePersist
	public void prePersist() {
		setCreateTime(new Date());
		setLastUpdateTime(new Date());
	}

	@PreUpdate
	public void preUpdate() {
		setLastUpdateTime(new Date());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}
}
