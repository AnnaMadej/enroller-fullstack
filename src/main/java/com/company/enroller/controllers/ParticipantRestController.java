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

import com.company.enroller.model.Participant;
import com.company.enroller.persistence.ParticipantService;

@RestController
@RequestMapping("/api/participants")
public class ParticipantRestController {

	@Autowired
	ParticipantService participantService;

	@RequestMapping(value = "", method = RequestMethod.POST)
	public ResponseEntity<?> addParticipant(@RequestBody Participant participant) {
		if (this.participantService.findByLogin(participant.getLogin()) != null) {
			return new ResponseEntity<String>(
					"Unable to create. A participant with login " + participant.getLogin() + " already exist.",
					HttpStatus.CONFLICT);
		}
		this.participantService.add(participant);
		return new ResponseEntity<>(participant, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> delete(@PathVariable("id") String login) {
		Participant participant = this.participantService.findByLogin(login);
		if (participant == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		this.participantService.delete(participant);
		return new ResponseEntity<Participant>(HttpStatus.NO_CONTENT);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> get(@PathVariable("id") String login) {
		Participant participant = this.participantService.findByLogin(login);
		if (participant == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(participant, HttpStatus.OK);
	}

	@RequestMapping(value = "", method = RequestMethod.GET)
	public ResponseEntity<?> getAll() {
		Collection<Participant> participants = this.participantService.getAll();
		return new ResponseEntity<>(participants, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> update(@PathVariable("id") String login, @RequestBody Participant updatedParticipant) {
		if (this.participantService.findByLogin(login) != null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		updatedParticipant.setLogin(login); // in case of login!=updatedParticipant.getLogin()
		this.participantService.update(updatedParticipant);
		return new ResponseEntity<Participant>(HttpStatus.NO_CONTENT);
	}
}
