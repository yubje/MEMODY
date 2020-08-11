<template>
  <v-container>
    <v-card outlined>
      <v-card-title>전체 랭킹</v-card-title>
      <v-list disabled>
        <v-list-item>
          <v-list-item-content>순위</v-list-item-content>
          <v-list-item-content>레벨</v-list-item-content>
          <v-list-item-content>닉네임</v-list-item-content>
          <v-list-item-content>이메일</v-list-item-content>
        </v-list-item>
        <v-list-item v-for="(rankedUser, n) in rankedUsers.entries()" :key="n">
          <v-list-item-content>
            <v-col class="text-left">
              {{ n+1 }}위
            <v-icon v-if="n+1===1" color="orange">mdi-crown</v-icon>
            <v-icon v-else-if="n+1<=3" color="orange">mdi-crown-outline</v-icon>
            </v-col>
          </v-list-item-content>
          <v-list-item-content>LV.{{ parseInt(rankedUser[1].exp/10) }} ({{ (rankedUser[1].exp%10)*10 }}%)</v-list-item-content>
          <v-list-item-content>{{ rankedUser[1].uid }}</v-list-item-content>
          <v-list-item-content>{{ rankedUser[1].email }}</v-list-item-content>
           
        </v-list-item>
      </v-list>
    </v-card>
  </v-container>
</template>

<script>
import { mapState } from 'vuex'
export default {
  name: 'MainRankingView',
  computed: {
    ...mapState('main', ['rankedUsers']),
  },
}
</script>

<style>

</style>