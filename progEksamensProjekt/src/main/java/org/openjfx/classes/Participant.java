/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openjfx.classes;

/**
 *
 * @author chris
 */
public class Participant {
    private int participant_ID;
    private User participant;
    private ParticipantStatus status;

    public Participant(int participant_ID, User participant, ParticipantStatus status) {
        this.participant_ID = participant_ID;
        this.participant = participant;
        this.status = status;
    }

    public int getParticipant_ID() {
        return participant_ID;
    }

    public User getParticipant() {
        return participant;
    }

    public void setParticipant(User participant) {
        this.participant = participant;
    }

    public ParticipantStatus getStatus() {
        return status;
    }

    public void setStatus(ParticipantStatus status) {
        this.status = status;
    }
}
