<template>
  <div id="header">
    <h1>
      <router-link to="/">SS_log</router-link>
    </h1>
    <button v-if="isLogin" class="btn btn-danger" @click="gowrite">글쓰기</button>
    <div class="right">
      <div class="search-input">
        <i class="fas fa-search"></i>  
        <input v-model="keyword" type="text" />
      </div>
      <span v-if="!isLogin">
        <Login class="login-btn" />
      </span>
      <span v-else>
        <router-link to="/user/modify" class="login-btn">My</router-link>
        <button class="login-btn" @click="logout">로그아웃</button>
      </span>
    </div>
  </div>
</template>

<script>
import Login from "../../page/user/Login";

export default {
  name: "Header",
  components: { Login },
  methods: {
    logout() {
      alert("BYE " + this.$session.get("user").nickname);
      console.log(this.$session.get("user").uid);
      this.$session.destroy();
      this.$router.push("/");
      this.isLogin = false;
      this.usernick = "";
      this.$cookie.delete("auth-token");
    },
    gowrite() {
      this.$router.push("/board/write");
    },
  },
  data: function () {
    return {
      isLogin: false,
      keyword: null,
    };
  },
  mounted() {
    // console.log(this.$cookie.get("auth-token"));
    if (this.$cookie.get("auth-token") != null) {
      this.isLogin = true;
    } else {
      this.isLogin = false;
    }
  },
};
</script>

<style></style>
