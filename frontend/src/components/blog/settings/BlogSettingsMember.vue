<template>
  <div class="container-fluid">
    <div class="row">
      <BlogSettingsSidebar/>
      <div class="col col-lg-10">
        <div class="col">

          <h1>멤버 관리</h1>
          <div>
            사용자 검색
            <input type="text">
            <button>추가</button>
          </div>
          <div>
            <p>멤버 목록</p>
          </div>

      </div>
      </div>
    </div>

  </div>
</template>

<script>
import BlogSettingsSidebar from '@/components/blog/sidebar/BlogSettingsSidebar.vue'
export default {
  name: 'BlogSettingsMember',
  components: {
    BlogSettingsSidebar,
  },

}
</script>

<style>

</style>