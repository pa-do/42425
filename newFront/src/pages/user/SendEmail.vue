<template>
  <div class="container row">
    <div class="d-block justify-content-center">
      <fg-input
        v-model="yourName"
        id="yourName"
        placeholder="your Name"
        type="text"
        class="no-border form-control-md my-3"
        autofocus
      ></fg-input>
      <fg-input
        v-model="yourEmail"
        id="yourEmail"
        placeholder="your Email"
        type="text"
        class="no-border form-control-md my-3"
      ></fg-input>
      <fg-input
        v-model="subject"
        id="subject"
        placeholder="subject"
        type="text"
        class="no-border form-control-md my-3"
      ></fg-input>
      <textarea v-model="message" id="message" placeholder="message" class="form-control"></textarea>

      <n-button @click="sendEmail" class="btn btn-primary btn-round">SEND</n-button>
    </div>
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
  methods: {
    sendEmail() {
      if (this.yourName == "") {
        Swal.fire({
          icon: "info",
          title: "당신의 이름을 입력하세요.",
        });
        return;
      }
      if (this.yourEmail == "") {
        Swal.fire({
          icon: "info",
          title: "당신의 메일 주소를 입력하세요.",
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
          console.log(res);
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