<template>
  <div>
    <new-meeting-form @added="addNewMeeting($event)"></new-meeting-form>

    <span v-if="meetings.length == 0">
               Brak zaplanowanych spotkań.
           </span>
    <h3 v-else>
      Zaplanowane zajęcia ({{ meetings.length }})



    </h3>

    <meetings-list :meetings="meetings"
                   :username="username"
                   @attend="addMeetingParticipant($event)"
                   @unattend="removeMeetingParticipant($event)"
                   @delete="deleteMeeting($event)"></meetings-list>
  </div>
</template>

<script>
    import NewMeetingForm from "./NewMeetingForm";
    import MeetingsList from "./MeetingsList";

    export default {
        components: {NewMeetingForm, MeetingsList},
        props: ['username'],
        data() {
            return {
                meetings: []
            };
        },

        mounted(){
            this.fetchMeetings();
        },
        methods: {
            addNewMeeting(meeting) {
                this.$http.post('meetings', meeting).then(() => this.fetchMeetings());
			
            },
            addMeetingParticipant(meeting) {
                 this.$http.post('meetings/' + meeting.id + "/participants/" + this.username).then(response => this.fetchMeetings());
				
				//meeting.participants.push(this.username);
            },
            removeMeetingParticipant(meeting) {
                  this.$http.delete('meetings/' + meeting.id + "/participants/" + this.username).then(response => this.fetchMeetings());
            },
            deleteMeeting(meeting) {
                this.$http.delete('meetings/' + meeting.id).then(() => this.fetchMeetings());
	
			   
            },

            fetchMeetings(){
                this.$http.get('meetings').then(response => {
					this.meetings  = response.body;
});
            }
        }
    }
</script>
