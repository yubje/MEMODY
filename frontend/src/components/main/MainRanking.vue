<template>
  <v-sheet
    class="mx-auto"
    max-width="800"
  > 
    <p>전체랭킹</p>
    <v-slide-group show-arrows>
      <v-slide-item
        v-for="(rankedUser, n) in rankedUsers.entries()"
        :key="n"
        v-slot:default="{ active, toggle }"
      >
        <router-link :to="{ name: 'MainRankingView' }">
          <v-btn
            text
            class="mx-2"
            :input-value="active"
            active-class="teal lighten-3 white--text"
            depressed
            rounded
            @click="toggle"
          >
            {{ n+1 }}위  {{ rankedUser[1].uid }}
          </v-btn>
        </router-link>
      </v-slide-item>
    </v-slide-group>
  </v-sheet>

</template>

<script>
import { mapState, mapActions } from 'vuex' 
export default {
  name: 'MainRanking',
  computed: {
    ...mapState('main', ['rankedUsers']),
  },
  methods: {
    ...mapActions('main', ['getRankedUsers']),
  },
  created() {
    this.getRankedUsers()
  },
}
</script>

<style>

</style>