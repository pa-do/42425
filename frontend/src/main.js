import Vue from "vue"
import App from "./App.vue"
import router from "./router"
/* 뷰 쿠키 */
import VueCookie from "vue-cookie"

Vue.use(VueCookie)

/* 이벤트 버스 */
export const eventBus = new Vue()

/* eslint-disable no-new */
new Vue({
  el: "#app",
  router,
  components: { App },
  template: "<App/>",
})

/* vue-session 추가*/
import VueSession from "vue-session"
var sessionOptions = {
  persist: true,
}
Vue.use(VueSession, sessionOptions)
