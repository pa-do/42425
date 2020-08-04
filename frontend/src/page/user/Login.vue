<template>
  <div>
    <!-- Button trigger modal -->
    <button data-toggle="modal" data-target="#loginModal">로그인</button>

    <!-- Modal -->
    <div
      class="modal fade"
      id="loginModal"
      tabindex="-1"
      role="dialog"
      aria-labelledby="loginModalLabel"
      aria-hidden="true"
    >
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title" id="loginModalLabel">로그인</h5>
            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
              <span aria-hidden="true">&times;</span>
            </button>
          </div>
          <div class="modal-body">
            <div class="user" id="login">
              <div class="wrapC table">
                <div class="middle">
                  <h1>SS_log</h1>
                  <div class="input-wrap">
                    <input
                      v-model="email"
                      id="email"
                      placeholder="이메일을 입력해주세요"
                      type="text"
                      @keyup.enter="login"
                    />
                  </div>
                  <div class="input-wrap">
                    <input
                      v-model="password"
                      type="password"
                      id="password"
                      placeholder="영문, 숫자 혼용 8자 이상"
                      @keyup.enter="login"
                    />
                  </div>
                  <button class="btn btn--back btn--login" @click="login">로그인 하기</button>
                  <div class="add-option">
                    <div class="wrap">
                      <p>아직 회원이 아니신가요?</p>
                      <router-link
                        v-bind:to="{name:constants.URL_TYPE.USER.JOIN}"
                        class="btn--text"
                        data-dismiss="modal"
                      >회원가입</router-link>
                    </div>
                    <div class="add-option">
                      <p>비밀번호를 잊으셨나요?</p>
                      <a class="btn--text">비밀번호 찾기</a>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import "../../assets/css/user.scss";
import constants from "../../lib/constants";
import axios from "axios";
import { eventBus } from "../../main.js";

function enterkey() {
  if (window.event.keyCode == 13) {
    alert("enter input");
    login();
  }
}

export default {
  components: {},
  created() {},
  watch: {},
  methods: {
    login() {
      if (this.email == "") {
        alert("메일 주소를 입력하세요.");
        return;
      } else if (!this.validEmail(this.email)) {
        alert("메일 형식을 확인하세요.");
        return;
      } else if (this.password == "") {
        alert("비밀번호를 입력하세요.");
        return;
      }
      axios({
        method: "POST",
        url: `http://localhost:8080/account/login`,
        params: {
          email: this.email,
          password: this.password,
        },
      })
        .then((response) => {
          console.log(response);
          this.result = response.data;
          this.user = response.data.object;

          this.$session.set("user", this.user);
          console.log(this.$session.get("user"));
          this.$router.go();
          this.$router.push("/#/");

          eventBus.$emit("login");
          this.$cookie.set("auth-token", this.user.uid, 1);

          alert(this.user.nickname + "님 환영합니다!");
        })
        .catch((err) => {
          console.log("ERROR :", err);
          alert(
            "이메일 또는 비밀번호를 확인해주세요. \n비밀번호는 영문과 숫자를 포함해 8자 이상이어야 합니다."
          );
        });
    },
    validEmail: function (email) {
      var re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
      return re.test(email);
    },
  },
  data: () => {
    return {
      constants,
      email: "",
      password: "",
    };
  },
};
</script>


