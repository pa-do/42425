<template>
  <div>
    <div class="page-header clear-filter" filter-color="black">
      <parallax
        class="page-header-image"
        style="background-image:url('img/header.jpg')"
      ></parallax>
      <div class="container">
        <div class="content-center brand">
          <img class="n-logo mb-5" src="img/now-logo.png" alt />
          <h1
            class="animate__animated animate__heartBeat mt-5 animate__infinite infinite animate__slow"
          >
            포 투 포 이 오
          </h1>
          <h3 class="mb-5"></h3>
          <h1></h1>
        </div>
        <h6 class="category category-absolute">
          Project by
          <i class="now-ui-icons sport_user-run"></i>
          Team OJO
        </h6>
      </div>
    </div>
    <div class="d-block my-5 py-5"></div>
    <div class="main mb-0">
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
    <div class="section my-0 py-0">
      <div class="container text-center">
        <div class="row justify-content-md-center">
          <div class="col-md-12 col-lg-8">
            <PortList :uid="this.testId" />
            <div class="my-5"></div>
            <h2 class="title mb-2 mt-5">포트폴리오의 모든 것</h2>
            <h2 class="title">포투포이오에서 간편하게</h2>
            <h5 class="description my-3">
              취업을 위한 이력 관리, 기술 관리, 블로그 관리, 포트폴리오까지 한
              번에!
            </h5>
            <button class="btn btn-primary btn-round btn-lg btn-icon mt-5 mb-3">
              <i class="far fa-address-card"></i>
            </button>
            <h3 class="my-1">Resume에서 당신의 이력을 관리하세요!</h3>
            <div class="col-md-10 mx-auto mb-5">
              <Resume :uid="this.testId" :mine="mine" />
            </div>
            <button class="btn btn-primary btn-round btn-lg btn-icon mt-5 mb-3">
              <i class="fas fa-sliders-h"></i>
            </button>

            <h3 class="my-1">
              My skill에서 당신이 사용가능한 기술을 등록하세요!
            </h3>
            <div class="col-md-10 mx-auto mb-5">
              <MySkill :uid="this.testId" :mine="mine" />
            </div>
            <h3 class="my-1">
              Portfolio에 당신이 진행한 프로젝트를 기록하세요!
            </h3>
            <button class="btn btn-primary btn-round btn-lg btn-icon mt-5 mb-3">
              <i class="far fa-clipboard"></i>
            </button>
            <div class="col-md-10 mx-auto mb-5">
              <p>portfolio here!!</p>
            </div>
            <h3 class="my-1">Blog에서 당신의 블로그를 만들어보세요!</h3>
            <div class="col-md-10 mx-auto mb-5">
              <Userpost v-if="show1" :uid="this.testId" />
            </div>
            <button class="btn btn-primary btn-round btn-lg btn-icon">
              <i class="far fa-envelope"></i>
            </button>
            <h3 class="my-1">
              send Email에서 마음에 드는 유저에게 메일로 연락하세요!
            </h3>
            <SendEmail :email="testId.email" />
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import Userpost from "./post/Userpost";
import Resume from "./user/Resume";
import MySkill from "./user/MySkill";
import SendEmail from "./user/SendEmail";
import PortList from "./portfolio/portList";

export default {
  name: "index",
  bodyClass: "index-page",
  components: {
    Userpost,
    Resume,
    MySkill,
    SendEmail,
    PortList,
  },
  data: () => {
    return {
      testId: 17,
      mine: false,
      show1: true,
    };
  },
};
</script>
<style scoped>
@import "https://cdn.jsdelivr.net/npm/animate.css@3.5.1";
</style>
