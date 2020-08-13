<template>
    <v-menu v-model="value" :disabled="disabled" :absolute="absolute" :open-on-hover="openOnHover"
      :close-on-click="closeOnClick" :close-on-content-click="closeOnContentClick" :offset-x="offsetX"
      :offset-y="offsetY">
      <template v-slot:activator="{ on, attrs }">
        <v-btn icon color="teal lighten-3" dark text v-bind="attrs" v-on="on">
          <font-awesome-icon :icon="['fas','code-branch']" class="fa-lg"/>
        </v-btn>
      </template>
      <v-list>
        <v-list-subtitle>목록</v-list-subtitle>
        <v-list-item v-for="(item, index) in items" :key="index">
          <v-list-item-title>{{item.uid}}({{ item.email }})</v-list-item-title>
        </v-list-item>
      </v-list>
    </v-menu>
</template>

<script>
  import axios from 'axios'
  import cookies from 'vue-cookies'

  export default {
    name: 'BlogForkUsers',
    props: {
      pid: Number
    },
    data: () => ({
      items: null,
      disabled: false,
      absolute: false,
      openOnHover: true,
      value: false,
      closeOnClick: true,
      closeOnContentClick: true,
      offsetX: true,
      offsetY: false,
    }),
    created() {
      axios.get(`${process.env.VUE_APP_SERVER}/blogs/fork/${this.pid}`,{headers: {"auth": cookies.get('auth-token')}})
      .then(response => {
        this.items = response.data.data
      })
    }

  }
</script>

<style>

</style>