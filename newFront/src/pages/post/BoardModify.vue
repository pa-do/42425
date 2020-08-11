<template>
  <div class="container text-left">
    <h1 class="text-center">글 수정</h1>
    <div class="form-group">
      <label for="exampleFormControlInput2" class="mb-3">글 제목</label>
      <input type="text" class="form-control" v-model="title" id="exampleFormControlInput2" />
    </div>
    <div class="form-group">
      <label for="exampleFormControlTextarea2" class="mb-3">글 내용</label>
      <textarea class="form-control" id="exampleFormControlTextarea2" v-model="content" rows="3"></textarea>
    </div>
    <br />
    <button @click="writeBoard" class="right btn btn-primary">확인</button>
  </div>
</template>

<script>
import axios from "axios";

const BASE_URL = "http://localhost:8080";

export default {
  data: function () {
    return {
      title: "",
      content: "",
      board: Object,
    };
  },
  methods: {
    fetchBoard() {
      axios
        .get(BASE_URL + `/board/${this.$route.params.mid}`)
        .then((res) => {
          this.board = res.data;
          this.title = this.board.title;
          this.content = this.board.content;
          console.log(this.board);
        })

        .catch((err) => console.error(err));
    },
    writeBoard() {
      this.board.uid = this.$cookie.get("auth-token");
      axios
        .put(BASE_URL + "/board/modify", null, {
          params: {
            bid: this.board.bid,
            content: this.content,
            title: this.title,
            uid: this.board.uid,
          },
        })
        .then(() => {
          this.$router.push(`/profile/${this.board.uid}`);
        })
        .catch((err) => {
          console.log("!!!!!!");
          console.log(err.response);
        });
    },
  },
  created() {
    this.fetchBoard();
  },
};
</script>

<style></style>
