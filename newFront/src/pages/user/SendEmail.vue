<template>
  <div class="container row">
    <div class="col-1"></div>
    <div class="col-10">
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
      <div class="text-right">
        <n-button @click="sendEmail" class="btn btn-primary btn-round">SEND</n-button>
      </div>
    </div>
    <div class="col-1"></div>
  </div>
</template>

<script>
import { Button, FormGroupInput } from "@/components";

export default {
  name: "sendemail",
  components: {
    [Button.name]: Button,
    [FormGroupInput.name]: FormGroupInput,
  },
  props: ["email"],
  created() {
    this.getdata();
  },
  methods: {
    getdata() {
      if (this.$session.get("user")) {
        let myAccount = this.$session.get("user");
        this.yourName = myAccount.nickname;
        this.yourEmail = myAccount.email;
      }
    },
    sendEmail() {
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
        })
        .catch((err) => {
          console.log("Err!!! :", err.response);
        });
    },
  },
  data: () => {
    return {
      yourName: "",
      yourEmail: "",
      subject: "",
      message: "",
    };
  },
};
</script>

<style>
</style>