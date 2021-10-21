package com.sampleapp.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.sampleapp.enumerations.Role;
import com.sampleapp.enumerations.UserState;
import com.sun.istack.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data //@EqualsAndHashCode, @ToString, @Getter, @Setter on all non-final fields, @RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "APP_USER")
public class User extends BaseModel {

	@NotNull
	private String    username;
	private String    password;
	
    private String    firstName;
    private String    lastName;
    private Role      role;
    private UserState state;
    private String    email;
}
