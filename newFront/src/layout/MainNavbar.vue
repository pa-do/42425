<template>
  <navbar
    position="fixed"
    type="primary"
    :transparent="transparent"
    :color-on-scroll="colorOnScroll"
    menu-classes="ml-auto"
  >
    <template>
      <router-link v-popover:popover1 class="navbar-brand" to="/">
        <img class="n-logo" src="img/wide-logo.png" style="width: 100px" alt />
      </router-link>
      <el-popover
        ref="popover1"
        popper-class="popover"
        placement="bottom"
        width="200"
        trigger="hover"
      >
        <div class="popover-body">포 투 포 이 오</div>
      </el-popover>
    </template>
    <template slot="navbar-menu">
      <!-- <li class="nav-item">
        <a
          class="nav-link"
          href="https://www.creative-tim.com/product/vue-now-ui-kit"
          target="_blank"
        >
          <i class="now-ui-icons arrows-1_cloud-download-93"></i>
          <p>Download</p>
        </a>
      </li>-->
      <drop-down tag="li" title="Components" icon="now-ui-icons design_app" class="nav-item">
        <nav-link to="/">
          <i class="now-ui-icons business_chart-pie-36"></i> All components
        </nav-link>
        <a
          href="https://demos.creative-tim.com/vue-now-ui-kit/documentation"
          target="_blank"
          class="dropdown-item"
        >
          <i class="now-ui-icons design_bullet-list-67"></i> Documentation
        </a>
      </drop-down>
      <drop-down tag="li" title="Examples" icon="now-ui-icons design_image" class="nav-item">
        <nav-link to="/landing">
          <i class="now-ui-icons education_paper"></i> Landing
        </nav-link>
        <div v-if="!isLogin">
          <nav-link to="/login">
            <i class="now-ui-icons users_circle-08"></i> Login
          </nav-link>
          <nav-link to="/join">
            <i class="now-ui-icons users_single-02"></i> Join Us
          </nav-link>
        </div>
        <div v-else>
          <nav-link to="/logout">
            <i class="now-ui-icons users_circle-08"></i> Logout
          </nav-link>
          <nav-link :to="`/profile/${this.uid}`">
            <i class="now-ui-icons users_single-02"></i> Profile
          </nav-link>
        </div>
      </drop-down>
      <!-- <li class="nav-item">
        <a
          class="nav-link btn btn-neutral"
          href="https://www.creative-tim.com/product/vue-now-ui-kit-pro"
          target="_blank"
        >
          <i class="now-ui-icons arrows-1_share-66"></i>
          <p>Upgrade to PRO</p>
        </a>
      </li>-->

      <!-- <li class="nav-item">
        <a
          class="nav-link"
          rel="tooltip"
          title="Follow us on Twitter"
          data-placement="bottom"
          href="https://twitter.com/CreativeTim"
          target="_blank"
        >
          <i class="fab fa-twitter"></i>
          <p class="d-lg-none d-xl-none">Twitter</p>
        </a>
      </li>
      <li class="nav-item">
        <a
          class="nav-link"
          rel="tooltip"
          title="Like us on Facebook"
          data-placement="bottom"
          href="https://www.facebook.com/CreativeTim"
          target="_blank"
        >
          <i class="fab fa-facebook-square"></i>
          <p class="d-lg-none d-xl-none">Facebook</p>
        </a>
      </li>
      <li class="nav-item">
        <a
          class="nav-link"
          rel="tooltip"
          title="Follow us on Instagram"
          data-placement="bottom"
          href="https://www.instagram.com/CreativeTimOfficial"
          target="_blank"
        >
          <i class="fab fa-instagram"></i>
          <p class="d-lg-none d-xl-none">Instagram</p>
        </a>
      </li>-->
    </template>
  </navbar>
</template>

<script>
import { DropDown, Navbar, NavLink } from "@/components";
import { Popover } from "element-ui";
export default {
  name: "main-navbar",
  props: {
    transparent: Boolean,
    colorOnScroll: Number,
  },
  components: {
    DropDown,
    Navbar,
    NavLink,
    [Popover.name]: Popover,
  },
  methods: {
    gowrite() {
      this.$router.push("/board/write");
    },
  },
  data: function () {
    return {
      isLogin: false,
      keyword: null,
      uid: "",
    };
  },
  mounted() {
    console.log(this.$cookie.get("auth-token"));
    if (this.$cookie.get("auth-token") != null) {
      this.isLogin = true;
      this.uid = this.$cookie.get("auth-token");
    } else {
      this.isLogin = false;
    }
  },
};
</script>

<style scoped></style>
