import Vue from "vue"
import Router from "vue-router"

import constants from "../lib/constants"

// 유저
// import Login from "../page/user/Login.vue"
import Join from "../page/user/Join.vue"
import Modify from "../page/user/Modify.vue"
import Modifypw from "../page/user/Modifypw.vue"

// 포스트
import List from "../page/post/List.vue"
import BoardDetail from "../page/post/BoardDetail.vue"
import Write from "../page/post/Write.vue"
import BoardModify from "../page/post/BoardModify.vue"

// 404페이지
import NotFound from "../page/404.vue"

Vue.use(Router)

export default new Router({
  routes: [
    // 로그인/가입
    // {
    //   path: "/user/login",
    //   name: constants.URL_TYPE.USER.LOGIN,
    //   component: Login,
    // },
    {
      path: "/user/join",
      name: constants.URL_TYPE.USER.JOIN,
      component: Join,
    },
    {
      path: "/user/modify",
      name: constants.URL_TYPE.USER.MODIFY,
      component: Modify,
    },
    {
      path: "/user/modifypw",
      name: constants.URL_TYPE.USER.MODIFYPW,
      component: Modifypw,
    },
    // 포스트
    {
      path: "/",
      name: constants.URL_TYPE.POST.MAIN,
      component: List,
    },
    {
      path: "/board/write",
      name: "write",
      component: Write,
    },
    {
      path: "/boardmodify/:mid",
      name: "BoardModify",
      component: BoardModify,
    },
    {
      path: "/board/:bid",
      name: "BoardDetail",
      component: BoardDetail,
    },
    // 404 에러 페이지
    {
      path: "*",
      component: NotFound,
    },
  ],
})
