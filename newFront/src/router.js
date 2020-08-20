import Vue from "vue";
import Router from "vue-router";
import Index from "./pages/Index.vue";
import Landing from "./pages/Landing.vue";
import MainNavbar from "./layout/MainNavbar.vue";
import MainFooter from "./layout/MainFooter.vue";

// 유저
import Login from "./pages/user/Login.vue";
import Logout from "./pages/user/Logout.vue";
import Join from "./pages/user/Join.vue";
import Modifypw from "./pages/user/Modifypw.vue";
import Profile from "./pages/user/Profile.vue";

// 포스트
import BoardDetail from "./pages/post/BoardDetail.vue";
import BoardModify from "./pages/post/BoardModify.vue";

//포트폴리오
// import PortDetail from "./pages/portfolio/portDetail.vue";
// import PortModify from "./pages/portfolio/portModify.vue";
import PortWrite from "./pages/portfolio/PortWrite.vue";

// 에러페이지
import NotFound from "./pages/404.vue";

Vue.use(Router);

export default new Router({
  linkExactActiveClass: "active",
  routes: [
    {
      path: "/",
      name: "index",
      components: { default: Index, header: MainNavbar, footer: MainFooter },
      props: {
        header: { colorOnScroll: 400 },
        footer: { backgroundColor: "black" },
      },
    },
    {
      path: "/landing",
      name: "landing",
      components: { default: Landing, header: MainNavbar, footer: MainFooter },
      props: {
        header: { colorOnScroll: 400 },
        footer: { backgroundColor: "black" },
      },
    },

    // userpage
    {
      path: "/login",
      name: "login",
      components: { default: Login, header: MainNavbar },
      props: {
        header: { colorOnScroll: 400 },
      },
    },
    {
      path: "/logout",
      name: "logout",
      components: { default: Logout, header: MainNavbar },
      props: {
        header: { colorOnScroll: 400 },
      },
    },
    {
      path: "/profile/:uid",
      name: "profile",
      components: { default: Profile, header: MainNavbar, footer: MainFooter },
      props: {
        header: { colorOnScroll: 400 },
        footer: { backgroundColor: "black" },
      },
    },
    {
      path: "/join",
      name: "join",
      components: { default: Join, header: MainNavbar },
      props: {
        header: { colorOnScroll: 400 },
        footer: { backgroundColor: "black" },
      },
    },
    {
      path: "/modifypw",
      name: "modifypw",
      components: { default: Modifypw, header: MainNavbar },
      props: {
        header: { colorOnScroll: 400 },
        footer: { backgroundColor: "black" },
      },
    },
    // post 관련
    {
      path: "/boardmodify/:mid",
      name: "BoardModify",
      components: { default: BoardModify, header: MainNavbar },
      props: {
        header: { colorOnScroll: 0 },
        footer: { backgroundColor: "black" },
      },
    },
    {
      path: "/board/:bid",
      name: "BoardDetail",
      components: { default: BoardDetail, header: MainNavbar },
      props: {
        header: { colorOnScroll: 0 },
        footer: { backgroundColor: "black" },
      },
    },
    // portfolio 관련
    {
      path: "/portWrite",
      name: "PortWrite",
      components: { default: PortWrite, header: MainNavbar },
      props: {
        header: { colorOnScroll: 0 },
        footer: { backgroundColor: "black" },
      },
    },
    // 404 에러 페이지
    {
      path: "*",
      components: { default: NotFound, header: MainNavbar },
      props: {
        header: { colorOnScroll: 400 },
      },
    },
  ],
  scrollBehavior: (to) => {
    if (to.hash) {
      return { selector: to.hash };
    } else {
      return { x: 0, y: 0 };
    }
  },
});
