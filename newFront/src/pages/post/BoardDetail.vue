<template>
  <div class="container">
    <div class="mt-5"></div>
    <div class="card mt-5">
      <h1 class="card-header text-center mt-3">{{ board.title }}</h1>
      <div class="card-body">
        <blockquote class="blockquote mb-0 text-right">
          <!-- <p class="text-left">{{ board.content }}</p> -->
          <viewer height="500px" v-if="board.content != null" :initialValue="board.content" />
          <footer class="blockquote-footer">
            <router-link :to="`/profile/${this.board.uid}`">{{ board.nickname }}</router-link>
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
import Comment from "./Comment"
import { Button } from "@/components"
import "codemirror/lib/codemirror.css"
import "@toast-ui/editor/dist/toastui-editor.css"
import { Viewer } from "@toast-ui/vue-editor"

export default {
  components: {
    [Button.name]: Button,
    Comment,
    viewer: Viewer,
  },
  data: function() {
    return {
      board: Object,
      bid: "",
      isAuthorized: false,
      likechk: false,
    }
  },
  methods: {
    fetchBoard() {
      // console.log(this.$route.params.bid);
      this.$axios
        .get(`/board/${this.$route.params.bid}`)
        .then((res) => {
          this.board = res.data
          this.bid = this.board.bid
          this.likechking()
          if (this.board.uid == this.$cookie.get("auth-token")) {
            this.isAuthorized = true
          }
        })
        // .then((res) => console.log(res))
        .catch((err) => console.error(err))
    },
    updateBoard(item) {
      this.$router.push(`/boardmodify/${item}`)
    },
    deleteBoard() {
      this.$axios
        .delete(`/board/delete/${this.board.bid}`)
        .then((res) => {
          // console.log(res);
          this.$router.push(`/profile/${this.board.uid}`)
        })
        .catch((err) => console.error(err))
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
          Swal.fire("글이 삭제되었습니다.")
          this.deleteBoard()
        }
      })
    },
    likes() {
      this.$axios
        .post("/likes/checkLikes", null, {
          params: {
            bid: this.board.bid,
            uid: this.$cookie.get("auth-token"),
          },
        })
        .then((res) => {
          console.log(res.data)
          this.likechk = res.data
          this.fetchBoard()
        })
        // .then((res) => console.log(res))
        .catch((err) => console.error(err))
    },
    likechking() {
      this.$axios
        .post("/likes/likesCheck", null, {
          params: {
            bid: this.board.bid,
            uid: this.$cookie.get("auth-token"),
          },
        })
        .then((res) => {
          console.log(res.data)
          this.likechk = res.data
        })
        // .then((res) => console.log(res))
        .catch((err) => console.error(err))
    },
  },
  created() {
    this.fetchBoard()
  },
}
</script>

<style>
.tui-editor-contents {
  text-align: left;
}
</style>
