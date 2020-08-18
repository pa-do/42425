<template>
  <div>
    <div class="page-header clear-filter" filter-color="orange">
      <parallax class="page-header-image" style="background-image:url('img/header.jpg')"></parallax>
      <div class="container">
        <div class="content-center brand">
          <img class="n-logo" src="img/now-logo.png" alt />
          <h1
            class="animate__animated animate__heartBeat mt-5 animate__infinite infinite animate__slow"
          >포 투 포 이 오</h1>
          <h3></h3>
        </div>
        <h6 class="category category-absolute">
          Project by
          <i class="now-ui-icons sport_user-run"></i>
          Team 지 렸 조
        </h6>
      </div>
    </div>
    <div class="main">
      <div class="section section-images">
        <div class="container">
          <div class="row">
            <div class="col-md-12">
              <div class="hero-images-container">
                <img src="img/hero-image-1.png" alt />
              </div>
              <div class="hero-images-container-1">
                <img src="img/hero-image-2.png" alt />
              </div>
              <div class="hero-images-container-2">
                <img src="img/hero-image-3.png" alt />
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="section">
      <div class="container text-center">
        <div class="row justify-content-md-center">
          <div class="col-md-12 col-lg-8">
            <h2 class="title mb-2">포트폴리오의 모든 것</h2>
            <h2 class="title">포투포이오에서 간편하게</h2>
            <h5 class="description">
              The kit comes with three pre-built pages to help you get started
              faster. You can change the text and images and you're good to go.
              More importantly, looking at them will give you a picture of what
              you can built with this powerful Bootstrap 4 ui kit.
            </h5>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  name: "index",
  bodyClass: "index-page",
  components: {},
};
</script>
<style scoped>
@import "https://cdn.jsdelivr.net/npm/animate.css@3.5.1";
</style>