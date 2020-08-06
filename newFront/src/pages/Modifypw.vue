<template>
  <div class="page-header clear-filter" filter-color="orange">
    <div
      class="page-header-image"
      style="background-image: url('img/login.jpg')"
    ></div>
    <div class="mb-5">
      <div class="container">
        <div class="col-md-5 ml-auto mr-auto">
          <card type="login" plain class="mt-5">
            <div slot="header" class="logo-container mt-5">
              <img v-lazy="'img/now-logo.png'" alt />
            </div>

            <div>
              <div>
                <input
                  v-model="email"
                  id="Memail"
                  placeholder="이메일을 입력해주세요"
                  class="form-control no-border input-l py-3 my-3"
                  type="text"
                />
                <div class="d-flex">
                  <button
                    @click="checkEmail"
                    class="m-0 btn btn-primary btn-round btn-md btn-block mr-1"
                  >
                    이메일 체크
                  </button>
                  <button
                    @click="sendEmail"
                    class="m-0 btn btn-primary btn-round btn-md btn-block ml-1"
                  >
                    인증 메일 전송
                  </button>
                </div>
              </div>
              <div class="input-wrap">
                <input
                  v-model="input_authnum"
                  id="input_authnum"
                  placeholder="인증번호를 입력해주세요"
                  class="form-control no-border input-l py-3 my-3"
                  type="text"
                />
                <button
                  @click="authEmail"
                  class="mt-2 btn btn-primary btn-round btn-md btn-block"
                >
                  인증
                </button>
              </div>

              <div class="input-wrap password-wrap">
                <input
                  v-model="password"
                  id="Mpassword"
                  type="password"
                  placeholder="비밀번호를 입력해주세요"
                  class="form-control no-border input-l py-3 my-3"
                />
              </div>

              <div class="input-wrap password-wrap">
                <input
                  v-model="passwordConfirm"
                  id="password-confirm"
                  type="password"
                  placeholder="비밀번호를 한번 더 입력해주세요"
                  class="form-control no-border input-l py-3 my-3"
                />
              </div>
              <button
                class="btn mb-5 btn-danger btn-round btn-md btn-block"
                @click="modifypw"
              >
                <span>수정 완료</span>
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
        alert("메일 형식을 확인하세요.");
        return;
      }
      axios
        .get(`http://localhost:8080/account/emailChk/${this.email}`)
        .then((response) => {
          this.result = response.data;
          if (this.result.data == "fail" && this.result.object == "email") {
            document.getElementById("Memail").setAttribute("readonly", true);
            this.emailChk = true;
          } else {
            document.getElementById("Memail").focus();
            alert("가입되지 않은 이메일입니다. \n회원가입을 이용하세요.");
          }
        })
        .catch((err) => {
          console.log("Err!!! :", err.response);
        });
    },
    validEmail: function(email) {
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
        console.log(this.email);
        // axios
        //   .post(`http://localhost:8080/account/getuid/`, {
        //     email: this.email,
        //   })
        //   .then((response) => {
        //     alert(response);
        //   })
        //   .catch((err) => {
        //     alert(err);
        //   });
        axios({
          method: "POST",
          url: `http://localhost:8080/account/getuid`,
          params: {
            email: this.email,
          },
        })
          .then((response) => {
            this.uid = response.data.data;
          })
          .catch((err) => {
            alert(err);
          });
        document.getElementById("input_authnum").setAttribute("readonly", true);
        this.authChk = true;
      } else {
        alert("인증번호가 일치하지 않습니다. 다시 입력해주세요.");
      }
    },
    modifypw() {
      if (this.emailChk === false) {
        alert("이메일 주소를 확인해 주세요.");
        return;
      } else if (this.authChk === false) {
        alert("이메일 인증을 진행해 주세요.");
        return;
      } else if (this.password !== this.passwordConfirm) {
        alert("비밀번호가 일치하지 않습니다. \n비밀번호를 다시 입력하세요.");
        document.getElementById("passwordConfirm").focus();
        return;
      } else {
        axios
          .put("http://localhost:8080/account/modify/password", {
            uid: this.uid,
            password: this.password,
          })
          .then((response) => {
            this.$router.go();

            alert("비밀번호가 변경되었습니다.");
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
