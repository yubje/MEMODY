<template>
  <v-carousel class="main-ranking-container" cycle hide-delimiters interval="5000" height="50px" light :show-arrows="false" vertical>
    <v-carousel-item v-for="(rankedUser, idx) in rankedUsers" :key="idx">
      <v-sheet height="100%">
        <v-row align="center" justify="center">
          <font-awesome-icon id="rank-icon" v-if="idx<3" :color="colors[idx]" :icon="['fas','crown']"/>
          <div>
            <span id="rank-number">{{ idx+1 }}</span> <span>위</span>  
            <img v-if="rankedUser.profile" id="profile-img-small" :src="rankedUser.profile">
            <img v-else id="profile-img-small" src="@/assets/img/user-default.png">
            <span>{{ rankedUser.uid }}</span>
          </div>
        </v-row>
      </v-sheet>
    </v-carousel-item>
  </v-carousel>
</template>

<script>
import { mapState, mapActions } from 'vuex'

export default {
  name: 'MainRanking',
  data() {
    return {
      colors: [
        '#ffcc33',
        '#b4b4b4',
        '#dd8a3e'
      ]
    }
  },
  computed: {
    ...mapState('main', ['rankedUsers']),
  },
  methods: {
    ...mapActions('main', ['getRankedUsers']),
  },
  created() {
    this.getRankedUsers()
  }
}
</script>
