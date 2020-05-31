<template>
  <div>
    <form @submit.prevent="addNewMeeting()" v-if="adding">
      <h3>Dodaj nowe spotkanie</h3>
      <label>Nazwa</label>
      <input type="text" v-model="newMeeting.title">
      <label>Opis</label>
      <textarea v-model="newMeeting.description"/>
      <label>Data</label>
      <input type="date" v-model="newMeeting.date" placeholder="rrrr-MM-dd" >
	  <label>Godzina</label>
      <input type="time" v-model="newMeeting.time" placeholder="GG:mm" >
      <button>Dodaj</button>
      <span class="error" v-if="error">{{error}}</span>
    </form>
    <button @click="adding = true" v-else>Dodaj nowe spotkanie</button>
  </div>
</template>

<script>
    export default {
        data() {
            return {
                newMeeting: {participants: []},
                adding: false,
                error: ""
            };
        },
        methods: {
            addNewMeeting() {
                this.error = "";	
				if (!this.newMeeting.title)  this.error = "Spotkanie musi mieć nazwę!";
				else if (!this.newMeeting.date)  this.error = "Spotkanie musi mieć datę!";
				else if (!this.newMeeting.time)  this.error = "Spotkanie musi mieć godzinę!";
                else {
					if (!this.newMeeting.date.match("^(19[5-9][0-9]|20[0-4][0-9]|2050)[-](0?[1-9]|1[0-2])[-](0?[1-9]|[12][0-9]|3[01])$")) this.error = "Zły format daty!";
					else if (!this.newMeeting.time.match( "^([0-1]?[0-9]|2[0-3]):[0-5][0-9]$")) this.error = "Wprowadź czas w formacie GG.mm";
                    else {
						this.$emit('added', this.newMeeting);
						this.newMeeting = {participants: []};
						this.adding = false; }
                } 
            }
        }
    }
</script>

<style scoped>
  .error {
    color: #F00;
  }
</style>
