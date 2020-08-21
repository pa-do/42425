<template>
  <div class="container row">
    <div class="col-1"></div>
    <div class="col-10">
      <!-- 일단 숨겨봄 -->

      <fg-input
        v-model="yourName"
        id="yourName"
        placeholder="메일 보내기 기능은 회원만 사용가능합니다."
        type="text"
        class="no-border form-control-md my-3"
        readonly
      ></fg-input>
      <fg-input
        v-model="yourEmail"
        id="yourEmail"
        placeholder="메일 보내기 기능은 회원만 사용가능합니다."
        type="text"
        class="no-border form-control-md my-3"
        readonly
      ></fg-input>

      <fg-input
        v-model="subject"
        id="subject"
        placeholder="제목"
        type="text"
        class="no-border form-control-md my-3"
      ></fg-input>
      <textarea v-model="message" id="message" placeholder="내용" class="form-control" rows="3"></textarea>
      <div class="row">
        <div class="col-4" style="margin: 0 auto;"></div>
        <div class="col-4 text-center">
          <div
            class="g-recaptchak"
            data-theme="light"
            :data-sitekey="sitekey"
            style="transform:scale(0.77);-webkit-transform:scale(0.77);transform-origin:0 0;-webkit-transform-origin:0 0;"
          >
            <vue-recaptcha
              ref="recaptcha"
              @verify="onVerify"
              @expired="onExpired"
              :sitekey="sitekey"
            ></vue-recaptcha>
          </div>
          <n-button @click="sendEmail" class="btn btn-primary btn-round text-center">SEND</n-button>
        </div>
        <div class="col-4" style="margin: 0 auto;"></div>
      </div>
    </div>
    <div class="col-1"></div>
  </div>
</template>

<script>
import { Button, FormGroupInput } from "@/components";
import VueRecaptcha from "vue-recaptcha";
export default {
  name: "sendemail",
  components: {
    [Button.name]: Button,
    [FormGroupInput.name]: FormGroupInput,

    "vue-recaptcha": VueRecaptcha,
  },
  props: ["email"],
  created() {
    this.getdata();
  },
  methods: {
    onSubmit: function () {
      this.$refs.invisibleRecaptcha.execute();
    },
    onVerify: function (response) {
      this.reCAPTCHA = true;
    },
    onExpired: function () {
      this.reCAPTCHA = false;
    },
    resetRecaptcha() {
      this.$refs.recaptcha.reset(); // Direct call reset method
      this.reCAPTCHA = false;
    },
    getdata() {
      if (this.$session.get("user")) {
        let myAccount = this.$session.get("user");
        this.yourName = myAccount.nickname;
        this.yourEmail = myAccount.email;
      }
    },
    sendEmail() {
      if (this.reCAPTCHA === false) {
        Swal.fire({
          icon: "warning",
          title: "로봇이 아님을 증명하세요.",
        });
        return;
      }
      if (this.yourName == "") {
        Swal.fire({
          icon: "info",
          title: "메일 보내기 기능은 회원만 사용가능합니다.",
        });
        return;
      }
      if (this.yourEmail == "") {
        Swal.fire({
          icon: "info",
          title: "메일 보내기 기능은 회원만 사용가능합니다.",
        });
        return;
      }
      if (this.subject == "") {
        Swal.fire({
          icon: "info",
          title: "메일 제목을 입력하세요.",
        });
        return;
      }
      if (this.message == "") {
        Swal.fire({
          icon: "info",
          title: "메시지를 입력하세요.",
        });
        return;
      }

      this.$axios
        .post(`/email/sendme`, null, {
          params: {
            email: this.yourEmail,
            message: this.message,
            myEmail: this.email,
            name: this.yourName,
            subject: this.subject,
          },
        })
        .then((res) => {
          this.subject = "";
          this.message = "";
          Swal.fire({
            icon: "success",
            title: "메일이 전송되었습니다.",
          }).then(() => {
            this.resetRecaptcha();
          });
        })
        .catch((err) => {
          console.log("Err!!! :", err.response);
        });
    },
  },
  data: () => {
    return {
      sitekey: "6LcFoL4ZAAAAAEeAoTIIdnsejJRIRP4ecyWbxt5e",

      yourName: "",
      yourEmail: "",
      subject: "",
      message: "",

      reCAPTCHA: false,
    };
  },
};
</script>

<style>
@media screen and (max-height: 575px) {
  #rc-imageselect,
  .g-recaptcha {
    transform: scale(0.77);
    -webkit-transform: scale(0.77);
    transform-origin: 0 0;
    -webkit-transform-origin: 0 0;
  }
}
</style>