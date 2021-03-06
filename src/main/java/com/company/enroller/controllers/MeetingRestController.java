package com.company.enroller.controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.company.enroller.model.Meeting;
import com.company.enroller.model.Participant;
import com.company.enroller.persistence.MeetingService;
import com.company.enroller.persistence.ParticipantService;

@RestController
@RequestMapping("/api/meetings")
public class MeetingRestController {

	@Autowired
	MeetingService meetingService;

	@Autowired
	ParticipantService participantService;

	@RequestMapping(value = "", method = RequestMethod.POST)
	public ResponseEntity<?> addMeeting(@RequestBody Meeting meeting) {
		try {
			this.meetingService.add(meeting);
			return new ResponseEntity<>(meeting, HttpStatus.CREATED);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}

	}

	@RequestMapping(value = "/{id}/participants/{login}", method = RequestMethod.POST)
	public ResponseEntity<?> addParticipant(@PathVariable("id") long meetingId, @PathVariable("login") String login) {

		Meeting meeting = this.meetingService.findById(meetingId);
		Participant participant = this.participantService.findByLogin(login);

		if (meeting == null || participant == null) {
			return new ResponseEntity<String>("Unable to update. Participant with login " + login
					+ " or meeting with id " + +meetingId + " does not exist", HttpStatus.NOT_FOUND);
		}
		meeting.addParticipant(participant);
		meeting = this.meetingService.updateMeeting(meeting);
		return new ResponseEntity<>(meeting.getParticipants(), HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> delMeeting(@PathVariable("id") long meetingId) {
		Meeting meeting = this.meetingService.findById(meetingId);
		if (meeting == null) {
			return new ResponseEntity<String>("Meeting with id: " + meetingId + " does not exist",
					HttpStatus.NOT_FOUND);
		}
		meeting = this.meetingService.delete(meeting);
		return new ResponseEntity<>(meeting, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}/participants/{login}", method = RequestMethod.DELETE)
	public ResponseEntity<?> delParticipant(@PathVariable("id") long meetingId, @PathVariable("login") String login) {
		Meeting meeting = this.meetingService.findById(meetingId);
		Participant participant = this.participantService.findByLogin(login);

		if (meeting == null || participant == null) {
			return new ResponseEntity<String>("Unable to update. Participant with login " + login
					+ " or meeting with id " + +meetingId + " does not exist", HttpStatus.NOT_FOUND);
		}

		meeting.removeParticipant(participant);
		meeting = this.meetingService.updateMeeting(meeting);
		return new ResponseEntity<>(meeting.getParticipants(), HttpStatus.OK);
	}

	@RequestMapping(value = "", method = RequestMethod.GET)
	public ResponseEntity<?> getMeetings() {

		Collection<Meeting> meetings = this.meetingService.getAll();
		return new ResponseEntity<>(meetings, HttpStatus.OK);
	}
}
