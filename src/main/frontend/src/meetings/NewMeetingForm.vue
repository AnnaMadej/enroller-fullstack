<template>
  <div>
    <form @submit.prevent="addNewMeeting()" v-if="adding">
      <h3>Dodaj nowe spotkanie</h3>
      <label>Nazwa</label>
      <input type="text" v-model="newMeeting.title">
      <label>Opis</label>
      <textarea v-model="newMeeting.description"/>
      <label>Data</label>
      <input type="date" v-model="newMeeting.date" placeholder="rrrr.mm.dd" pattern="[0-9]{4}.(0[1-9]|1[012]).(0[1-9]|1[0-9]|2[0-9]|3[01])">
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
                else {
                    this.$emit('added', this.newMeeting);
                    this.newMeeting = {participants: []};
                    this.adding = false;
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
