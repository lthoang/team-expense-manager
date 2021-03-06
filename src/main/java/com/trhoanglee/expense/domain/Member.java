package com.trhoanglee.expense.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "MEMBERS")
public class Member {
	@Id
	@Column(name = "ID")
	private String id;

	@Embedded
	private Name name;

	@Column(name = "EMAIL")
	private String email;

	@Column(name = "MOBILE")
	private String mobile;

	@Temporal(TemporalType.DATE)
	@Column(name = "DOB")
	private Date dob;

	@OneToMany(mappedBy = "team", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<TeamMember> joinedTeams = new HashSet<>();

	@OneToMany(mappedBy = "manager", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<Team> managedTeams = new HashSet<>();

	public Member() {
		// We need to create a no-argument constructor whenever we have other argument(s) constructors 
	}
	
	public Member(Name name, String email, String mobile, Date dob) {
		this.name = name;
		this.email = email;
		this.mobile = mobile;
		this.dob = dob;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Name getName() {
		return name;
	}

	public void setName(Name name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public Set<TeamMember> getJoinedTeams() {
		return joinedTeams;
	}

	public void setJoinedTeams(Set<TeamMember> joinedTeams) {
		this.joinedTeams = joinedTeams;
	}

	public Set<Team> getManagedTeams() {
		return managedTeams;
	}

	public void setManagedTeams(Set<Team> managedTeams) {
		this.managedTeams = managedTeams;
	}
}
