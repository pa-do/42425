<template>
  <div class="page-header clear-filter" filter-color="orange-">
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
                  <n-button
                    @click="checkNickname"
                    class="mt-2 btn btn-primary btn-round btn-md btn-block"
                  >중복 체크</n-button>
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
                    <n-button
                      @click="checkEmail"
                      class="btn btn-primary btn-round btn-md btn-block mr-1 mt-0"
                    >중복 체크</n-button>
                    <n-button
                      @click="sendEmail"
                      class="btn btn-primary btn-round btn-md btn-block ml-1 mt-0"
                    >인증 메일 전송</n-button>
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
                  <n-button
                    @click="authEmail"
                    class="mt-2 btn btn-primary btn-round btn-md btn-block"
                  >인증</n-button>
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
              <n-button class="btn mb-5 btn-danger btn-round btn-md btn-block" @click="join">
                <span>작성완료</span>
              </n-button>
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
      if (this.nickname.length > 128) {
        Swal.fire({
          icon: "warning",
          title: "닉네임이 너무 깁니다.",
          text: "닉네임을 128자 미만으로 입력하세요.",
        });
        return;
      }
      this.$axios
        .get(`/account/nicknameChk/${this.nickname}`)
        .then((response) => {
          this.result = response.data;
          if (this.result.data == "fail" && this.result.object == "nickname") {
            Swal.fire({
              icon: "warning",
              title: "이미 가입된 닉네임입니다.",
              text: "새로운 닉네임을 입력하세요.",
            });
          } else {
            Swal.fire({
              icon: "success",
              title: "사용 가능한 닉네임입니다.",
            });
            this.nicknameChk = true;
            document.getElementById("nickname").setAttribute("readonly", true);
          }
        })
        .catch((err) => {
          console.log("Err!!! :", err.response);
        });
    },
    checkEmail() {
      if (this.email.length > 128) {
        Swal.fire({
          icon: "warning",
          title: "이메일 주소가 너무 깁니다.",
          text: "이메일 주소를 128자 미만으로 입력하세요.",
        });
        return;
      }
      if (!this.validEmail(this.email)) {
        Swal.fire({
          icon: "warning",
          title: "메일 형식을 확인하세요.",
        });
        return;
      }
      this.$axios
        .get(`/account/emailChk/${this.email}`)
        .then((response) => {
          this.result = response.data;
          console.log(this.result);
          if (this.result.data == "fail" && this.result.object == "email") {
            Swal.fire({
              icon: "warning",
              title: "이미 가입된 이메일입니다.",
              text: "새로운 이메일을 입력하세요.",
            });
          } else {
            this.emailChk = true;
            document.getElementById("Jemail").setAttribute("readonly", true);
            Swal.fire({
              icon: "success",
              title: "사용 가능한 이메일입니다.",
            });
          }
        })
        .catch((err) => {
          console.log("Err!!! :", err.response);
        });
    },
    join() {
      if (this.nickname == "") {
        Swal.fire({
          icon: "info",
          title: "닉네임을 입력하세요.",
        });
        return;
      } else if (!this.nicknameChk) {
        Swal.fire({
          icon: "warning",
          title: "닉네임 중복 체크를 진행하세요,",
        });
        return;
      } else if (this.email == "") {
        Swal.fire({
          icon: "info",
          title: "메일 주소를 입력하세요.",
        });
        return;
      } else if (!this.validEmail(this.email)) {
        Swal.fire({
          icon: "error",
          title: "메일 형식을 확인하세요.",
        });
        return;
      } else if (!this.emailChk) {
        Swal.fire({
          icon: "warning",
          title: "이메일 중복 체크를 진행하세요.",
        });
        return;
      } else if (this.authChk == false) {
        Swal.fire({
          icon: "warning",
          title: "이메일 인증을 진행하세요.",
        });
        return;
      } else if (this.password == "") {
        Swal.fire({
          icon: "info",
          title: "비밀번호를 입력하세요.",
        });
        return;
      } else if (this.passwordConfirm == "") {
        Swal.fire({
          icon: "info",
          title: "비밀번호를 한번 더 입력하세요.",
        });
        return;
      } else if (this.password != this.passwordConfirm) {
        Swal.fire({
          icon: "warning",
          title: "비밀번호가 일치하지 않습니다.",
        });
        return;
      } else if (!this.validPW(this.password)) {
        Swal.fire({
          icon: "warning",
          title: "비밀번호는 영문과 숫자를 합쳐 8자 이상이어야 합니다.",
        });
        return;
      } else if (this.password.length > 128) {
        Swal.fire({
          icon: "warning",
          title: "비밀번호가 너무 깁니다.",
          text: "비밀번호를 128자 미만으로 입력하세요.",
        });
      } else {
        this.$axios
          .post("/account/signup", {
            nickname: this.nickname,
            email: this.email,
            password: this.password,
          })
          .then((response) => {
            this.result = response.data;
            if (this.result.data != "fail") {
              this.user = response.data.object;
              this.$session.set("user", this.user);
              Swal.fire({
                icon: "success",
                title: "가입 완료",
                text: "가입을 축하드립니다.",
              });
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
      this.$axios
        .get(`/email/send/${this.email}`)
        .then((response) => {
          Swal.fire({
            icon: "info",
            title: "이메일이 발송되었습니다.",
            text: "인증번호를 입력해주세요.",
          });
          this.authnum = response.data.data;
        })
        .catch((err) => {
          console.log("Err!!! :", err.response);
        });
    },
    authEmail() {
      if (this.authnum === this.input_authnum) {
        Swal.fire({
          icon: "success",
          title: "인증이 완료되었습니다.",
        });
        document.getElementById("input_authnum").setAttribute("readonly", true);
        this.authChk = true;
      } else {
        Swal.fire({
          icon: "warning",
          title: "인증번호가 일치하지 않습니다. 다시 입력해주세요.",
        });
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
