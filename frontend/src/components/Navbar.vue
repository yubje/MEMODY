<template>
  <div id="nav">
    <b-navbar class="navbar" toggleable="lg" type="dark">
    <b-navbar-brand>
      <router-link 
      :to="{ name: 'Main' }"
      class="text-light text-decoration-none"
      >
        MEMODY
      </router-link>
    </b-navbar-brand>

    <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>
    <b-collapse id="nav-collapse" is-nav>
      <b-navbar-nav class="ml-auto">
        <b-nav-item>
          <router-link :to="{ name: 'BlogView' }" class="text-decoration-none text-light">Blog</router-link>
        </b-nav-item>
        <b-nav-item-dropdown right>
          <template v-slot:button-content >알람</template>
          <b-dropdown-item href="#">Alarm1</b-dropdown-item>
        </b-nav-item-dropdown>

        <b-nav-item-dropdown right>
          <template v-slot:button-content>로그인·회원가입</template>
          <b-dropdown-item>
            <router-link v-b-modal.login-modal :to="{ name: 'Login' }">Login</router-link>
          </b-dropdown-item>
        </b-nav-item-dropdown>

        <b-nav-item-dropdown right>
          <template v-slot:button-content>User</template>
          <b-dropdown-item>
            <router-link v-b-modal.info-modal :to="{ name: 'UserInfo' }">회원 정보</router-link>
          </b-dropdown-item>
        </b-nav-item-dropdown>
      </b-navbar-nav>
    </b-collapse>
  </b-navbar>
  </div>
</template>

<script>
export default {

}
</script>

<style scoped>
.navbar {
  background-color: #25374F !important;
  color: white !important;
}

</style>