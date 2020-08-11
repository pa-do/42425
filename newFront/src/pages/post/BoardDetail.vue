<template>
  <div class="container">
    <div class="mt-5"></div>
    <div class="card mt-5">
      <div class="card-header">{{ board.title }}</div>
      <div class="card-body">
        <blockquote class="blockquote mb-0">
          <p>{{ board.content }}</p>
          <footer class="blockquote-footer">{{ board.nickname }}</footer>
          <p>{{ board.writeDate.split("T").join(" ") }}</p>
        </blockquote>
      </div>
      <div v-if="isAuthorized">
        <button class="btn btn-primary" @click="updateBoard(bid)">수정</button>
        <button class="btn btn-info" @click="deleteAlert">삭제</button>
      </div>
    </div>
    <Comment :bid="this.bid" />
  </div>
</template>

<script>
import axios from "axios";
import Comment from "./Comment";

const BASE_URL = "http://localhost:8080";

export default {
  components: {
    Comment,
  },
  data: function () {
    return {
      board: Object,
      bid: "",
      isAuthorized: false,
    };
  },
  methods: {
    fetchBoard() {
      // console.log(this.$route.params.bid);
      axios
        .get(BASE_URL + `/board/${this.$route.params.bid}`)
        .then((res) => {
          this.board = res.data;
          this.bid = this.board.bid;
          if (this.board.uid == this.$cookie.get("auth-token")) {
            this.isAuthorized = true;
          }
        })
        // .then((res) => console.log(res))
        .catch((err) => console.error(err));
    },
    updateBoard(item) {
      this.$router.push(`/boardmodify/${item}`);
    },
    deleteBoard() {
      axios
        .delete(`http://localhost:8080/board/delete/${this.board.bid}`)
        .then((res) => {
          // console.log(res);
          this.$router.push(`/profile/${this.board.uid}`);
        })
        .catch((err) => console.error(err));
    },
    deleteAlert() {
      Swal.fire({
        title: "정말 삭제하시겠어요?",
        text: "확인 버튼을 누르면 글이 삭제되어 복구할 수 없게 됩니다.",
        icon: "warning",
        showCancelButton: true,
        confirmButtonColor: "#d33",
        cancelButtonColor: "#3085d6",
        confirmButtonText: "삭제할래요😥",
        cancelButtonText: "안할래요😊",
      }).then((result) => {
        if (result.value) {
          Swal.fire("글이 삭제되었습니다.");
          this.deleteBoard();
        }
      });
    },
  },
  created() {
    this.fetchBoard();
  },
};
</script>

<style></style>
