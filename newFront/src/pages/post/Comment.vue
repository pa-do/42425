<template>
  <div>
    <div class="row mb-5">
      <fg-input
        v-model="newComment"
        placeholder="댓글을 입력하세요"
        type="text"
        class="col-9 col-md-10 col-lg-11 my-2"
        addon-left-icon="now-ui-icons text_caps-small"
        @keyup.enter="writeComment()"
      ></fg-input>
      <n-button type="primary" round @click="writeComment()">확인</n-button>
    </div>
    <CommentDetail v-for="comment in comments" :key="comment.cid" :comment="comment" @update="fetchComment" />
  </div>
</template>

<script>
import { FormGroupInput, Button } from "@/components"
import CommentDetail from "./CommentDetail"

export default {
  props: ["bid"],
  components: {
    [FormGroupInput.name]: FormGroupInput,
    [Button.name]: Button,
    CommentDetail,
  },
  data: function() {
    return {
      comments: [],
      isAuthorized: false,
      newComment: "",
    }
  },
  methods: {
    fetchComment() {
      this.$axios
        .get("/comment/board/" + this.bid)
        .then((res) => {
          this.comments = res.data
        })
        // .then((res) => console.log(res))
        .catch((err) => console.error(err))
    },
    writeComment() {
      this.newComment = this.newComment.trim()
      if (!this.newComment) {
        Swal.fire({
          icon: "warning",
          title: "댓글 내용이 입력되지 않았습니다.",
        })
      } else {
        this.cuid = this.$cookie.get("auth-token")
        this.$axios
          .post("/comment/write", null, {
            params: {
              bid: this.bid,
              content: this.newComment,
              uid: this.$cookie.get("auth-token"),
            },
          })
          .then(() => {
            Swal.fire({
              icon: "success",
              title: "댓글이 달렸습니다",
            })
            this.newComment = ""
            this.fetchComment()
          })
          .catch((err) => {
            console.log("!!!!!!")
            console.log(err.response)
          })
      }
    },
  },
  created() {
    this.fetchComment()
  },
}
</script>

<style></style>
