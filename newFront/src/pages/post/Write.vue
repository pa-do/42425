<template>
  <div class="container text-left">
    <h1 class="text-center">글 쓰기</h1>
    <div class="form-group">
      <label for="exampleFormControlInput1" class="mb-3">글 제목</label>
      <fg-input type="text" v-model="writeData.title" id="exampleFormControlInput1"></fg-input>
    </div>
    <div class="form-group">
      <label for="exampleFormControlTextarea1" class="mb-3">글 내용</label>
      <textarea
        class="form-control"
        id="exampleFormControlTextarea1"
        v-model="writeData.content"
        rows="3"
      ></textarea>
    </div>
    <br />
    <n-button @click="writeBoard" class="right btn btn-primary">확인</n-button>
  </div>
</template>

<script>
import { Button, FormGroupInput as FgInput } from "@/components";

export default {
  name: "Write",
  data() {
    return {
      writeData: {
        content: null,
        title: null,
        uid: null,
      },
    };
  },
  components: {
    [Button.name]: Button,
    FgInput,
  },
  methods: {
    writeBoard() {
      this.writeData.uid = this.$cookie.get("auth-token");
      this.$axios
        .post("/board/write", null, {
          params: {
            content: this.writeData.content,
            title: this.writeData.title,
            uid: this.writeData.uid,
          },
        })
        .then(() => {
          this.$router.push(`/profile/${this.writeData.uid}`);
        })
        .catch((err) => {
          console.log("!!!!!!");
          console.log(err.response);
        });
    },
  },
};
</script>

<style></style>
