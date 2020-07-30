<template>
  <div class="container-fluid">
    <div class="row">
      <BlogSettingsSidebar/>
      <div class="col col-lg-10">
        <div>
          <h1>블로그 정보 수정</h1>
        </div>
        <div class="col">
          <div class="row">
            <p>블로그명</p>
            <input type="text">
          </div>
          <div class="row">
            <p>블로그 부제</p>
            <input type="text">
          </div>
          <div class="row">
            <p>블로그 설명</p>
            <textarea name="" id="" cols="30" rows="10"></textarea>
          </div>
          <div class="row">
            <p>해시태그</p>
            <textarea name="" id="" cols="30" rows="10"></textarea>
          </div>
          <div class="row">
            <p>멤버 관리</p>
            <div>
              <div>
                <!--사용자 검색-->
                <input type="text">
                <button>추가</button>
              </div>
              <div>
                <p>멤버 목록</p>
                <!-- 멤버 목록 -->
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import BlogSettingsSidebar from '@/components/blog/sidebar/BlogSettingsSidebar.vue'

export default {
  name: 'BlogSettingsInfo',
  components : {
    BlogSettingsSidebar,
  },
  props: {
    bid : Number
  },
  created() {
    console.log(this.bid)
  }
}
</script>

<style scoped>

</style>