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
    <n-button @click="writeBoard" class="right btn btn-primary">확인</n-button>
  </div>
</template>

<script>
import { Button } from "@/components"

export default {
  components: {
    [Button.name]: Button,
  },
  data: function() {
    return {
      title: "",
      content: "",
      board: Object,
    }
  },
  methods: {
    fetchBoard() {
      this.$axios
        .get(`/board/${this.$route.params.mid}`)
        .then((res) => {
          this.board = res.data
          this.title = this.board.title
          this.content = this.board.content
          console.log(this.board)
        })

        .catch((err) => console.error(err))
    },
    writeBoard() {
      this.title = this.title.trim()
      this.content = this.content.trim()
      if (!this.title || !this.content) {
        Swal.fire({
          icon: "warning",
          title: "제목 또는 내용이 입력되지<br>않았습니다.",
        })
      } else {
        this.board.uid = this.$cookie.get("auth-token")
        this.$axios
          .put("/board/modify", null, {
            params: {
              bid: this.board.bid,
              content: this.content,
              title: this.title,
              uid: this.board.uid,
            },
          })
          .then(() => {
            Swal.fire({
              icon: "success",
              title: "글이 수정되었습니다.",
            })
            this.$router.push(`/profile/${this.board.uid}`)
          })
          .catch((err) => {
            console.log("!!!!!!")
            console.log(err.response)
          })
      }
    },
  },
  created() {
    this.fetchBoard()
  },
}
</script>

<style></style>
