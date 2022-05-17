package com.boot.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "user")
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@Getter
public class User {

    @Id
    @Column(name = "userId", nullable = false)
    private String userId;
	
	@Column(name = "password", nullable = false)
    private String password;
    
	@Column(name = "name")
	private String name;
    
	@Column(name = "regNo")
	private String regNo;
	
	@Builder
    public User(String userId, String password, String name, String regNo) {
    	this.userId = userId;
    	this.password = password;
    	this.name = name;
    	this.regNo = regNo;
    }
    
	@Builder(builderMethodName = "loginBuilder")
    public User(String userId, String password) {
    	this.userId = userId;
    	this.password = password;
    }
    
    
}
