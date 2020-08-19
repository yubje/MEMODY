<template>
  <v-container class="ranking-container">
    <v-card outlined>
      <v-card-title class="ranking-title">TOP 10</v-card-title>
      <hr>

      <div class="ranking-contents-top3">
        <div class="ranking-contents-top3-detail" v-for="idx in 3" :key="idx">
          <font-awesome-icon id="rank-icon-top3" :color="colors[idx-1]" :icon="['fas','crown']"/>
          <div>
            <img v-if="rankedUsers[idx-1].profile" class="rank-img-top3" id="profile-img-small" :src="rankedUsers[idx-1].profile">
            <img v-else class="rank-img-top3" id="profile-img-small" src="@/assets/img/user-default.png">
          </div>
          <div>
            {{rankedUsers[idx-1].uid}}
          </div>
          <div>
            LV.{{ parseInt(rankedUsers[idx-1].exp/10) }} ({{ (rankedUsers[idx-1].exp%10)*10 }}%)
          </div>
          <div>
            {{idx}}위
          </div>
        </div>
      </div>
      <hr>

      <v-list disabled>
        <v-list-item class="ranking-contents">
          <v-list-item-content style="display:unset">순위</v-list-item-content>
          <v-list-item-content>닉네임</v-list-item-content>
          <v-list-item-content>레벨</v-list-item-content>
        </v-list-item>
        <hr>

        <div v-for="n in rankedUsers.length-3" :key="n">
          <v-list-item class="ranking-contents">
            <v-list-item-content>
                {{ n+3 }}위
            </v-list-item-content>
            <v-list-item-content>
              <img v-if="rankedUsers[n+2].profile" id="profile-img-small" :src="rankedUsers[n+2].profile">
              <img v-else id="profile-img-small" src="@/assets/img/user-default.png">
              {{ rankedUsers[n+2].uid }}
            </v-list-item-content>
            <v-list-item-content>LV.{{ parseInt(rankedUsers[n+2].exp/10) }} ({{ (rankedUsers[n+2].exp%10)*10 }}%)</v-list-item-content>
          </v-list-item>
          <hr v-if="n < rankedUsers.length-3">
        </div>
      </v-list>
    </v-card>
  </v-container>
</template>

<script>
import { mapState } from 'vuex'
export default {
  name: 'MainRankingView',
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
}
</script>