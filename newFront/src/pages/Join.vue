<template>
  <div class="page-header clear-filter" filter-color="orange">
    <div class="page-header-image" style="background-image: url('img/login.jpg')"></div>
    <div class="mb-5">
      <div class="container">
        <div class="col-md-5 ml-auto mr-auto">
          <card type="login" plain class="mt-5">
            <div slot="header" class="logo-container mt-5">
              <img v-lazy="'img/now-logo.png'" alt />
            </div>
            <div>
              <div>
                <div>
                  <fg-input
                    v-model="nickname"
                    id="nickname"
                    placeholder="닉네임을 입력해주세요"
                    class="form-control-md no-border"
                    addon-left-icon="now-ui-icons business_badge"
                    type="text"
                  ></fg-input>
                  <button
                    @click="checkNickname"
                    class="mt-2 btn btn-primary btn-round btn-md btn-block"
                  >중복 체크</button>
                </div>

                <div>
                  <fg-input
                    v-model="email"
                    id="Jemail"
                    placeholder="이메일을 입력해주세요"
                    class="form-control-md no-border m-0"
                    addon-left-icon="now-ui-icons ui-1_email-85"
                    type="text"
                  ></fg-input>
                  <div class="d-flex mt-2">
                    <button
                      @click="checkEmail"
                      class="btn btn-primary btn-round btn-md btn-block mr-1 mt-0"
                    >중복 체크</button>
                    <button
                      @click="sendEmail"
                      class="btn btn-primary btn-round btn-md btn-block ml-1 mt-0"
                    >인증 메일 전송</button>
                  </div>
                </div>
                <div class="input-wrap">
                  <fg-input
                    v-model="input_authnum"
                    id="input_authnum"
                    placeholder="인증번호를 입력해주세요"
                    class="form-control-md no-border"
                    addon-left-icon="now-ui-icons ui-1_send"
                    type="text"
                  ></fg-input>
                  <button
                    @click="authEmail"
                    class="mt-2 btn btn-primary btn-round btn-md btn-block"
                  >인증</button>
                </div>

                <div>
                  <fg-input
                    v-model="password"
                    id="password"
                    :type="passwordType"
                    placeholder="비밀번호를 입력해주세요"
                    class="form-control-md no-border"
                    addon-left-icon="now-ui-icons ui-1_lock-circle-open"
                  ></fg-input>
                </div>

                <div>
                  <fg-input
                    v-model="passwordConfirm"
                    id="password-confirm"
                    :type="passwordConfirmType"
                    placeholder="비밀번호를 한번 더 입력해주세요"
                    class="form-control-md no-border mt-2"
                    addon-left-icon="now-ui-icons objects_key-25"
                  ></fg-input>
                </div>
              </div>
              <button class="btn mb-5 btn-danger btn-round btn-md btn-block" @click="join">
                <span>작성완료</span>
              </button>
            </div>
          </card>
        </div>
      </div>
    </div>
    <main-footer></main-footer>
  </div>
</template>
<script>
import { Card, Button, FormGroupInput } from "@/components";
import MainFooter from "@/layout/MainFooter";
import axios from "axios";
export default {
  name: "join-page",
  bodyClass: "login-page",
  components: {
    Card,
    MainFooter,
    [Button.name]: Button,
    [FormGroupInput.name]: FormGroupInput,
  },
  methods: {
    checkNickname() {
      console.log(this.nickname);
      axios
        .get(`http://localhost:8080/account/nicknameChk/${this.nickname}`)
        .then((response) => {
          this.result = response.data;
          if (this.result.data == "fail" && this.result.object == "nickname") {
            alert("이미 가입된 닉네임입니다. 새로운 닉네임을 입력하세요.");
            document.getElementById("nickname").focus();
          } else {
            alert("사용 가능한 닉네임입니다.");
            this.nicknameChk = true;
            document.getElementById("nickname").setAttribute("readonly", true);
          }
        })
        .catch((err) => {
          console.log("Err!!! :", err.response);
        });
    },
    checkEmail() {
      if (!this.validEmail(this.email)) {
        alert("메일 형식을 확인하세요.");
        document.getElementById("Jemail").focus();
        return;
      }
      axios
        .get(`http://localhost:8080/account/emailChk/${this.email}`)
        .then((response) => {
          this.result = response.data;
          console.log(this.result);
          if (this.result.data == "fail" && this.result.object == "email") {
            document.getElementById("Jemail").focus();
            alert("이미 가입된 이메일입니다. 새로운 이메일을 입력하세요.");
          } else {
            this.emailChk = true;
            document.getElementById("Jemail").setAttribute("readonly", true);
            alert("사용 가능한 이메일입니다.");
          }
        })
        .catch((err) => {
          console.log("Err!!! :", err.response);
        });
    },
    join() {
      if (this.nickname == "") {
        alert("닉네임을 입력하세요.");
        document.getElementById("nickname").focus();
        return;
      } else if (!this.nicknameChk) {
        alert("닉네임 중복 체크하세요.");
        return;
      } else if (this.email == "") {
        alert("메일 주소를 입력하세요.");
        document.getElementById("Jemail").focus();
        return;
      } else if (!this.validEmail(this.email)) {
        alert("메일 형식을 확인하세요.");
        document.getElementById("Jemail").focus();
        return;
      } else if (!this.emailChk) {
        alert("이메일 중복 체크하세요.");
        return;
      } else if (this.authChk == false) {
        alert("이메일 인증을 진행해주세요.");
        document.getElementById("input_authnum").focus();
        return;
      } else if (this.password == "") {
        alert("비밀번호를 입력하세요.");
        document.getElementById("password").focus();
        return;
      } else if (this.passwordConfirm == "") {
        alert("비밀번호를 한번 더 입력하세요.");
        document.getElementById("password-confirm").focus();
        return;
      } else if (this.password != this.passwordConfirm) {
        alert("비밀번호가 일치하지 않습니다.");
        document.getElementById("password-confirm").focus();
        return;
      } else if (!this.validPW(this.password)) {
        alert("비밀번호는 영문과 숫자를 합쳐 8자 이상이어야 합니다.");
        document.getElementById("password").focus();
        return;
      } else {
        console.log(this.nickname, this.email, this.password);
        axios
          .post("http://localhost:8080/account/signup", {
            nickname: this.nickname,
            email: this.email,
            password: this.password,
          })
          .then((response) => {
            this.result = response.data;
            console.log(this.result);
            if (this.result.data != "fail") {
              alert("가입 성공! 회원가입을 축하합니다.");
              this.user = response.data.object;
              this.$session.set("user", this.user);
              this.$router.push("/");
              this.$cookie.set("auth-token", this.user.uid, 1);
            }
          })
          .catch((err) => {
            console.log("Err!!! :", err.response);
          });
      }
    },
    validPW: function (pw) {
      var re = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,}$/;
      return re.test(pw);
    },
    validEmail: function (email) {
      var re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
      return re.test(email);
    },
    sendEmail() {
      axios
        .get(`http://localhost:8080/email/send/${this.email}`)
        .then((response) => {
          alert("이메일이 발송되었습니다. 인증번호를 입력해주세요.");
          this.authnum = response.data.data;
        })
        .catch((err) => {
          console.log("Err!!! :", err.response);
        });
    },
    authEmail() {
      if (this.authnum === this.input_authnum) {
        alert("인증이 완료되었습니다.");
        document.getElementById("input_authnum").setAttribute("readonly", true);
        this.authChk = true;
      } else {
        alert("인증번호가 일치하지 않습니다. 다시 입력해주세요.");
      }
    },
  },
  watch: {},
  data: () => {
    return {
      email: "",
      nickname: "",
      password: "",
      passwordConfirm: "",
      passwordType: "password",
      passwordConfirmType: "password",
      emailChk: false,
      nicknameChk: false,
      authnum: "",
      input_authnum: "",
      authChk: false,
      modals: {
        classic: false,
      },
    };
  },
};
</script>
<style>
.input-group {
  margin-bottom: 0 !important;
}
</style>
