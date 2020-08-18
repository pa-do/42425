<template>
  <div class="page-header clear-filter" filter-color="orange-">
    <div class="page-header-image" style="background-image: url('img/login.jpg')"></div>
    <div class="content">
      <div class="container">
        <div class="d-block d-sm-none py-5"></div>
        <div class="col-md-5 ml-auto mr-auto">
          <card type="login" plain>
            <div slot="header" class="logo-container">
              <img v-lazy="'img/now-logo.png'" alt />
            </div>

            <fg-input
              id="email"
              v-model="email"
              class="no-border form-control-md"
              placeholder="이메일을 입력해주세요"
              addon-left-icon="now-ui-icons users_circle-08"
              type="text"
              @keyup.enter="login"
            ></fg-input>

            <fg-input
              id="password"
              v-model="password"
              class="no-border form-control-md mt-2"
              placeholder="영문, 숫자 혼용 8자 이상"
              addon-left-icon="now-ui-icons ui-1_lock-circle-open"
              type="password"
              @keyup.enter="login"
            ></fg-input>

            <template slot="raw-content">
              <div class="card-footer text-center">
                <div class="btn btn-primary btn-round btn-md btn-block" @click="login">Login</div>
              </div>
              <div class="pull-left">
                <h6>
                  <!-- <a href="#join" class="link footer-link">Create Account</a> -->
                  <router-link to="join" class="link footer-link">Join us</router-link>
                </h6>
              </div>
              <div class="pull-right">
                <h6>
                  <!-- <a href="#pablo" class="link footer-link">Need Help?</a> -->
                  <router-link to="modifypw" class="link footer-link">Find password</router-link>
                </h6>
              </div>
            </template>
          </card>
        </div>
      </div>
    </div>
    <div class="d-none d-sm-block">
      <main-footer></main-footer>
    </div>
  </div>
</template>
<script>
import { Card, Button, FormGroupInput } from "@/components";
import MainFooter from "@/layout/MainFooter";

export default {
  name: "login-page",
  bodyClass: "login-page",
  components: {
    Card,
    MainFooter,
    [Button.name]: Button,
    [FormGroupInput.name]: FormGroupInput,
  },
  methods: {
    login() {
      if (this.email == "") {
        Swal.fire({
          icon: "info",
          title: "메일 주소를 입력하세요.",
        });
        document.getElementById("email").focus();
        return;
      } else if (!this.validEmail(this.email)) {
        Swal.fire({
          icon: "warning",
          title: "메일 형식을 확인하세요.",
        });
        document.getElementById("email").focus();
        return;
      } else if (this.password == "") {
        Swal.fire({
          icon: "info",
          title: "비밀번호를 입력하세요.",
        });
        document.getElementById("password").focus();
        return;
      }

      this.$axios
      .post("/account/login", null, {
        params: {
          email: this.email,
          password: this.password,
        },
      })
        .then((response) => {
          this.result = response.data;
          this.user = response.data.object;

          this.$session.set("user", this.user);
          // this.$router.push("/");
          // this.$router.go();
          // this.$cookie.set("auth-token", this.user.uid, 1);
          Swal.fire({
            icon: "success",
            title: this.user.nickname + "님 환영합니다!",
          }).then(() => {
            this.$router.push("/");
            this.$router.go();
            this.$cookie.set("auth-token", this.user.uid, 1);
          });
        })
        .catch((err) => {
          console.log("ERROR :", err);
          Swal.fire({
            icon: "error",
            title: "이메일 또는 비밀번호를 확인하세요.",
            text: "비밀번호는 영문과 숫자를 포함해 8자 이상이어야 합니다.",
          });
        });
    },
    validEmail: function (email) {
      var re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
      return re.test(email);
    },
  },
  data: () => {
    return {
      email: "",
      password: "",
    };
  },
};
</script>
<style></style>
