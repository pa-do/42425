<template>
  <blockquote class="blockquote text-center card">
    <div v-if="isMode">
      <p v-if="isShow && filtering" @click="showup" class="mb-0">
        {{ comment.content }}
      </p>
      <p v-else class="mb-0" @click="showup">
        {{ comment.content | truncate(50, "...") }}
        <i v-if="filtering" class="now-ui-icons gestures_tap-01"></i>
      </p>
      <router-link :to="`/profile/${this.comment.uid}`">
        <footer class="blockquote-footer">
          {{ comment.nickname | truncate(20, "...") }}
          <cite title="프로필 보기">[{{ comment.writeDate }}]</cite>
        </footer>
      </router-link>
      <div v-if="check" class="text-right">
        <n-button type="info" round @click="updateMode">수정</n-button>
        <n-button type="danger" round @click="deleteAlert">삭제</n-button>
      </div>
    </div>
    <div v-else>
      <fg-input
        v-model="newComment"
        placeholder="댓글을 입력하세요"
        type="text"
        class
        addon-left-icon="now-ui-icons text_caps-small"
        autofocus
      ></fg-input>
      <div class="text-right">
        <n-button type="info" round @click="updateAlert">확인</n-button>
        <n-button type="danger" round @click="updateMode">취소</n-button>
      </div>
    </div>
  </blockquote>
</template>

<script>
import { FormGroupInput, Button } from "@/components";

export default {
  components: {
    [FormGroupInput.name]: FormGroupInput,
    [Button.name]: Button,
  },
  props: ["comment"],
  data: function() {
    return {
      isShow: false,
      isMode: true,
      newComment: this.comment.content,
    };
  },
  methods: {
    updateMode() {
      this.isMode = !this.isMode;
    },
    showup() {
      this.isShow = !this.isShow;
    },
    updateComment(item) {
      this.$axios
        .put("/comment/modify", null, {
          params: {
            cid: this.comment.cid,
            content: this.newComment,
          },
        })
        .then(() => {
          this.$emit("update");
        })
        .catch((err) => {
          console.log("!!!!!!");
          console.log(err.response);
        });
    },
    updateAlert() {
      Swal.fire({
        icon: "success",
        title: "댓글이 수정되었습니다",
      }).then((this.isMode = true), this.updateComment());
    },
    deleteComment() {
      this.$axios
        .delete(`/comment/delete/${this.comment.cid}`)
        .then((res) => {
          // console.log(res);
          this.$emit("update");
        })
        .catch((err) => console.error(err));
    },
    deleteAlert() {
      Swal.fire({
        title: "정말 삭제하시겠어요?",
        text: "삭제 버튼을 누르면 댓글이 삭제되어 복구할 수 없게 됩니다.",
        icon: "warning",
        showCancelButton: true,
        confirmButtonColor: "#d33",
        cancelButtonColor: "#3085d6",
        confirmButtonText: "삭제할래요😥",
        cancelButtonText: "안할래요😊",
      }).then((result) => {
        if (result.value) {
          Swal.fire("댓글이 삭제되었습니다.");
          this.deleteComment();
        }
      });
    },
  },
  computed: {
    check() {
      if (this.comment.uid == this.$cookie.get("auth-token")) {
        return true;
      } else return false;
    },
    filtering() {
      if (this.comment.content.length > 50) {
        return true;
      } else return false;
    },
  },
  filters: {
    truncate: function(text, length, suffix) {
      if (text.length > length) {
        return text.substring(0, length) + suffix;
      } else {
        return text;
      }
    },
  },
};
</script>

<style></style>
