package com.company.enroller.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "meeting")
public class Meeting {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column
	private String title;

	@Column
	private String description;

	@Column
	private Date date;

	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.EAGER)
	@JoinTable(name = "meeting_participant", joinColumns = { @JoinColumn(name = "meeting_id") }, inverseJoinColumns = {
			@JoinColumn(name = "participant_login") })
	Set<Participant> participants = new HashSet<>();

	public void addParticipant(Participant participant) {
		this.participants.add(participant);
	}

	public String getDate() {
		return new SimpleDateFormat("yyyy-MM-dd").format(this.date);
	}

	public String getDescription() {
		return this.description;
	}

	public long getId() {
		return this.id;
	}

	public Collection<Participant> getParticipants() {
		return this.participants;
	}

	public String getTitle() {
		return this.title;
	}

	public void removeParticipant(Participant participant) {
		this.participants.remove(participant);
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void setDate(String date) {
		date.replaceAll(".", "-");
		try {
			this.date = new SimpleDateFormat("yyyy-MM-dd").parse(date);
		} catch (ParseException e) {
			this.date = new Date();
		}
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
