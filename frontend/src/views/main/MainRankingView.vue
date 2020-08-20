<template>
  <v-container class="ranking-container">
    <v-card outlined>
      <v-card-title class="ranking-title">TOP 10</v-card-title>
      <hr>

      <div class="ranking-contents-top3">
        <div class="ranking-contents-top3-detail" v-for="idx in ['2','1','3']" :key="idx">
          <div v-if="idx == 2" style="padding-top:20px"></div>
          <div v-else-if="idx == 3" style="padding-top:25px"></div>
          <font-awesome-icon id="ranking-icon-top3" :color="colors[idx-1]" :icon="['fas','crown']"/>
          <div>
            <img v-if="rankedUsers[idx-1].profile" class="ranking-img-top3" id="profile-img-small" :src="rankedUsers[idx-1].profile">
            <img v-else class="ranking-img-top3" id="profile-img-small" src="@/assets/img/user-default.png">
          </div>
          <div class="ranking-content-uid" @click="openModal(rankedUsers[idx-1])">
            {{rankedUsers[idx-1].uid}}
          </div>
          <div>
            LV.{{ parseInt(rankedUsers[idx-1].exp/10) }} ({{ (rankedUsers[idx-1].exp%10)*10 }}%)
          </div>
          <div v-if="idx == 1" class="ranking-content-rank ranking-content-rank-first">
            {{idx}}위
          </div>
          <div v-else-if="idx == 2" class="ranking-content-rank ranking-content-rank-second">
            {{idx}}위
          </div>
          <div v-else class="ranking-content-rank">
            {{idx}}위
          </div>
        </div>
      </div>
      <hr>

      <v-list>
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
              <a class="ranking-content-uid" @click="openModal(rankedUsers[n+2])">{{ rankedUsers[n+2].uid }}</a>
            </v-list-item-content>
            <v-list-item-content>LV.{{ parseInt(rankedUsers[n+2].exp/10) }} ({{ (rankedUsers[n+2].exp%10)*10 }}%)</v-list-item-content>
          </v-list-item>
          <hr v-if="n < rankedUsers.length-3">
        </div>
      </v-list>
    </v-card>

    <div>
      <MainRankingBlogList :uid="uid" />
    </div>
  </v-container>
</template>

<script>
import MainRankingBlogList from '@/components/main/MainRankingBlogList.vue'
import { mapState, mapActions } from 'vuex'
export default {
  name: 'MainRankingView',
  data() {
    return {
      colors: [
        '#ffcc33',
        '#b4b4b4',
        '#dd8a3e'
      ],
      uid: ''
    }
  },
  components: {
    MainRankingBlogList
  },
  computed: {
    ...mapState('main', ['rankedUsers']),
    ...mapState(['modalRankingBlog'])
  },
  methods: {
    ...mapActions(['getRankingBlogList']),

    openModal(rankingBlogData) {
      this.uid = rankingBlogData.uid
      this.getRankingBlogList(rankingBlogData.email)
    }
  }
}
</script>