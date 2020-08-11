<template>
  <div class="container text-left">
    <h1 class="text-center">글 쓰기</h1>
    <div class="form-group">
      <label for="exampleFormControlInput1" class="mb-3">글 제목</label>
      <input
        type="text"
        class="form-control"
        v-model="writeData.title"
        id="exampleFormControlInput1"
      />
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
    <button @click="writeBoard" class="right btn btn-primary">확인</button>
  </div>
</template>

<script>
import axios from "axios";

const BASE_URL = "http://127.0.0.1:8080";

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
  methods: {
    writeBoard() {
      this.writeData.uid = this.$cookie.get("auth-token");
      axios
        .post(BASE_URL + "/board/write", null, {
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
