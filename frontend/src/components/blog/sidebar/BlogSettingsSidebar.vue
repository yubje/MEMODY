<template>
  <div class="col col-lg-2 container bg-light">
    <div class="col">
      <div class="row">
        <router-link :to="{ name: 'BlogSettingsInfo' }"> 
          블로그 정보 수정
        </router-link>
      </div>
      <div class="row">
        <router-link :to="{ name: 'BlogSettingsCategory' }"> 
          블로그 카테고리 설정
        </router-link>
      </div>
      <div class="row">
        <router-link :to="{ name: 'BlogSettingsMember' }"> 
          블로그 멤버 설정
        </router-link>
      </div>
      <div class="row">
        <router-link :to="{ name: 'BlogView' }">블로그 홈</router-link>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'BlogSettingsSidebar',
  
}
</script>

<style>

</style>