<template>
  <div class="container-fluid">
    <div class="row">
      <BlogSettingsSidebar/>
      <div class="col col-lg-10">
        <div>
          <h1>카테고리 설정 </h1>
        </div>
        <div>
          <p>카테고리 보여주기</p>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import BlogSettingsSidebar from '@/components/blog/sidebar/BlogSettingsSidebar.vue'

export default {
  name: 'BlogSettingsCategory',
  components : {
    BlogSettingsSidebar,
  },
}
</script>

<style>

</style>