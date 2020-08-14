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
                <fg-input
                  v-model="email"
                  id="Memail"
                  placeholder="이메일을 입력해주세요"
                  type="text"
                  class="no-border form-control-md"
                  addon-left-icon="now-ui-icons ui-1_email-85"
                ></fg-input>
                <div class="d-flex mt-2">
                  <n-button
                    @click="checkEmail"
                    class="m-0 btn btn-primary btn-round btn-md btn-block mr-1"
                  >이메일 체크</n-button>
                  <n-button
                    @click="sendEmail"
                    class="m-0 btn btn-primary btn-round btn-md btn-block ml-1"
                  >인증 메일 전송</n-button>
                </div>
              </div>
              <div class="input-wrap">
                <fg-input
                  v-model="input_authnum"
                  id="input_authnum"
                  placeholder="인증번호를 입력해주세요"
                  type="text"
                  class="no-border form-control-md mt-2"
                  addon-left-icon="now-ui-icons ui-1_send"
                ></fg-input>
                <n-button
                  @click="authEmail"
                  class="mt-2 btn btn-primary btn-round btn-md btn-block"
                >인증</n-button>
              </div>

              <div class="input-wrap password-wrap">
                <fg-input
                  v-model="password"
                  id="Mpassword"
                  type="password"
                  placeholder="비밀번호를 입력해주세요"
                  class="no-border form-control-md mt-2"
                  addon-left-icon="now-ui-icons ui-1_lock-circle-open"
                ></fg-input>
              </div>

              <div class="input-wrap password-wrap">
                <fg-input
                  v-model="passwordConfirm"
                  id="password-confirm"
                  type="password"
                  placeholder="비밀번호를 한번 더 입력해주세요"
                  class="no-border form-control-md mt-2"
                  addon-left-icon="now-ui-icons objects_key-25"
                ></fg-input>
              </div>
              <n-button class="btn mb-5 btn-danger btn-round btn-md btn-block" @click="modifypw">
                <span>수정 완료</span>
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
  name: "modifypw-page",
  bodyClass: "login-page",
  components: {
    Card,
    MainFooter,
    [Button.name]: Button,
    [FormGroupInput.name]: FormGroupInput,
  },
  methods: {
    checkEmail() {
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
          if (this.result.data == "fail" && this.result.object == "email") {
            document.getElementById("Memail").setAttribute("readonly", true);
            this.emailChk = true;
          } else {
            Swal.fire({
              icon: "success",
              title: "사용 가능한 이메일입니다.",
              text: "회원가입을 진행하세요..",
            });
          }
        })
        .catch((err) => {
          console.log("Err!!! :", err.response);
        });
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
            icon: "success",
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
        this.$axios
          .post(`/account/getuid`, null, {
            params: {
              email: this.email,
            },
          })
          .then((response) => {
            this.uid = response.data.data;
          })
          .catch((err) => {
            console.log(err);
          });
        document.getElementById("input_authnum").setAttribute("readonly", true);
        this.authChk = true;
      } else {
        Swal.fire({
          icon: "error",
          title: "인증번호가 일치하지 않습니다.",
          text: "인증번호를 확인해 주세요.",
        });
      }
    },
    modifypw() {
      if (this.emailChk === false) {
        Swal.fire({
          icon: "info",
          title: "이메일 주소를 확인해 주세요.",
        });
        return;
      } else if (this.authChk === false) {
        Swal.fire({
          icon: "warning",
          title: "이메일 인증을 진행해 주세요.",
        });
        return;
      } else if (this.password === "") {
        Swal.fire({
          icon: "info",
          title: "새로운 비밀번호를 확인해 주세요.",
        });
      } else if (this.passwordConfirm === "") {
        Swal.fire({
          icon: "info",
          title: "새로운 비밀번호를 입력해 주세요",
        });
      } else if (this.password !== this.passwordConfirm) {
        Swal.fire({
          icon: "warning",
          title: "비밀번호가 일치하지 않습니다.",
          text: "비밀번호를 확인해 주세요.",
        });
        return;
      } else if (this.password.length > 128) {
        Swal.fire({
          icon: "warning",
          title: "비밀번호가 너무 깁니다.",
          text: "비밀번호를 128자 미만으로 입력하세요.",
        });
        return;
      } else {
        this.$axios
          .put("/account/modify/password", {
            uid: this.uid,
            password: this.password,
          })
          .then((response) => {
            Swal.fire({
              icon: "success",
              title: "비밀번호가 변경되었습니다.",
            }).then(() => {
              this.$router.push("/login");
              this.$router.go();
            });
          })
          .catch((err) => {
            console.log("Err!!! :", err.response);
          });
      }
    },
  },
  data: () => {
    return {
      uid: "",
      email: "",
      emailChk: false,

      authnum: "",
      input_authnum: "",
      authChk: false,

      password: "",
      passwordConfirm: "",
    };
  },
};
</script>

<style></style>
