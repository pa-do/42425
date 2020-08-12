<template>
  <div class="container">
    <div class="mt-5"></div>
    <div class="card mt-5">
      <h1 class="card-header text-center">{{ board.title }}</h1>
      <div class="card-body">
        <blockquote class="blockquote mb-0 text-right">
          <p class="text-left">{{ board.content }}</p>
          <footer class="blockquote-footer">
            {{ board.nickname }}
            <p>{{ board.writeDate.split("T").join(" ") }}</p>
            <h3 class="mb-0">
              <i v-if="!likechk" class="far fa-heart" @click="likes"></i>
              <i v-else class="far fa-heart text-danger" @click="likes"></i>
              {{ board.likes_count }}
            </h3>
          </footer>
        </blockquote>
      </div>
      <div v-if="isAuthorized" class="text-right">
        <n-button type="info" round @click="updateBoard(bid)">수정</n-button>
        <n-button type="danger" round @click="deleteAlert">삭제</n-button>
      </div>
    </div>
    <Comment :bid="this.bid" />
  </div>
</template>

<script>
import axios from "axios";
import Comment from "./Comment";
import { Button } from "@/components";

const BASE_URL = "http://localhost:8080";

export default {
  components: {
    [Button.name]: Button,
    Comment,
  },
  data: function() {
    return {
      board: Object,
      bid: "",
      isAuthorized: false,
      likechk: false,
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
          this.likechking();
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
    likes() {
      axios
        .post("http://localhost:8080/likes/checkLikes", null, {
          params: {
            bid: this.board.bid,
            uid: this.$cookie.get("auth-token"),
          },
        })
        .then((res) => {
          console.log(res.data);
          this.likechk = res.data;
          this.fetchBoard();
        })
        // .then((res) => console.log(res))
        .catch((err) => console.error(err));
    },
    likechking() {
      axios
        .post("http://localhost:8080/likes/likesCheck", null, {
          params: {
            bid: this.board.bid,
            uid: this.$cookie.get("auth-token"),
          },
        })
        .then((res) => {
          console.log(res.data);
          this.likechk = res.data;
        })
        // .then((res) => console.log(res))
        .catch((err) => console.error(err));
    },
  },
  created() {
    this.fetchBoard();
  },
};
</script>

<style></style>
