<template>
  <div>
    <div class="row mb-5">
      <fg-input
        v-model="newComment"
        placeholder="댓글을 입력하세요"
        type="text"
        class="col-9 col-md-10 col-lg-11 my-2"
        addon-left-icon="now-ui-icons text_caps-small"
      ></fg-input>
      <n-button type="primary" round @click="writeAlert()">확인</n-button>
    </div>
    <CommentDetail
      v-for="comment in comments"
      :key="comment.cid"
      :comment="comment"
      @update="fetchComment"
    />
  </div>
</template>

<script>
import { FormGroupInput, Button } from "@/components";
import CommentDetail from "./CommentDetail";
import axios from "axios";
const BASE_URL = "http://localhost:8080";

export default {
  props: ["bid"],
  components: {
    [FormGroupInput.name]: FormGroupInput,
    [Button.name]: Button,
    CommentDetail,
  },
  data: function () {
    return {
      comments: [],
      isAuthorized: false,
      newComment: "",
    };
  },
  methods: {
    fetchComment() {
      axios
        .get(BASE_URL + "/comment/board/" + this.bid)
        .then((res) => {
          this.comments = res.data;
        })
        // .then((res) => console.log(res))
        .catch((err) => console.error(err));
    },
    writeAlert() {
      Swal.fire({
        icon: "success",
        title: "댓글이 달렸습니다",
      }).then(this.writeComment());
    },
    writeComment() {
      this.cuid = this.$cookie.get("auth-token");
      axios
        .post(BASE_URL + "/comment/write", null, {
          params: {
            bid: this.bid,
            content: this.newComment,
            uid: this.$cookie.get("auth-token"),
          },
        })
        .then(() => {
          this.newComment = "";
          this.fetchComment();
        })
        .catch((err) => {
          console.log("!!!!!!");
          console.log(err.response);
        });
    },
  },
  created() {
    this.fetchComment();
  },
};
</script>

<style>
</style>