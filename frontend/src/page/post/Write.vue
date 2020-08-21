<template>
  <div class="container text-left">
    <h1 class="text-center">글 쓰기</h1>
    <div class="form-group">
      <label for="exampleFormControlInput1" class="mb-3">글 제목</label>
      <input type="text" class="form-control" v-model="writeData.title" id="exampleFormControlInput1" />
    </div>
    <div class="form-group">
      <label for="content" class="mb-3">글 내용</label>
      <!-- <textarea class="form-control" id="content" v-model="writeData.content">입력테스트</textarea> -->
      <Editor id="content" ref="content" />
    </div>
    <br />
    <button @click="writeBoard" class="right btn btn-primary">확인</button>
  </div>
</template>

<script>
import axios from "axios";
import "codemirror/lib/codemirror.css";
import "@toast-ui/editor/dist/toastui-editor.css";
import { Editor } from "@toast-ui/vue-editor";

const BASE_URL = "http://localhost:8080";

export default {
  name: "Write",
  components: {
    Editor,
  },
  data() {
    return {
      writeData: {
        content: "",
        title: "",
        uid: this.$session.get("user").uid,
      },
    };
  },
  methods: {
    writeBoard() {
      this.writeData.content = this.$refs.content.invoke("getHtml");
      if (!this.writeData.title) {
        alert("제목이 입력되지 않았습니다!");
      } else if (!this.writeData.content) {
        alert("내용이 입력되지 않았습니다!");
      } else {
        // this.writeData.uid = this.$cookie.get("auth-token");
        // console.log(this.writeData.content);
        axios
          .post(BASE_URL + "/board/write", null, {
            params: {
              content: this.writeData.content,
              title: this.writeData.title,
              uid: this.writeData.uid,
            },
          })
          .then(() => {
            this.$router.push("/#/");
          })
          .catch((err) => {
            console.log(err.response);
          });
      }
    },
  },
  mounted() {
    if (!this.$session.get("user")) {
      alert("올바른 접근이 아닙니다.");
      this.$router.push("/");
    }
  },
};
</script>

<style></style>
