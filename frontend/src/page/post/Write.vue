<template>
  <div class="container text-left">
    <h1 class="text-center">글 쓰기</h1>
    <div class="form-group">
      <label for="exampleFormControlInput1" class="mb-3">글 제목</label>
      <input type="text" class="form-control" v-model="writeData.title" id="exampleFormControlInput1" />
    </div>
    <div class="form-group">
      <label for="sample" class="mb-3">글 내용</label>
      <textarea class="form-control" id="sample" v-model="writeData.content"></textarea>
    </div>
    <br />
    <button @click="writeBoard" class="right btn btn-primary">확인</button>
  </div>
</template>

<script>
import axios from "axios";
import "suneditor/dist/css/suneditor.min.css";
import suneditor from "suneditor";
import plugins from "suneditor/src/plugins";

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
      console.log(this.writeBoard.content);
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
    },
  },
  mounted() {
    suneditor.create("sample", {
      width: "80%",
      height: "500px",
      plugins: plugins,
      buttonList: [
        ["undo", "redo"],
        ["font", "fontSize", "formatBlock"],
        ["paragraphStyle", "blockquote"],
        ["bold", "underline", "italic", "strike", "subscript", "superscript"],
        ["fontColor", "hiliteColor", "textStyle"],
        ["removeFormat"],
        "/", // Line break
        ["outdent", "indent"],
        ["align", "horizontalRule", "list", "lineHeight"],
        ["table", "link", "image", "video", "audio" /** ,'math' */], // You must add the 'katex' library at options to use the 'math' plugin. // You must add the "imageGalleryUrl".
        /** ['imageGallery'] */ ["fullScreen", "showBlocks", "codeView"],
        ["preview", "print"],
        ["save", "template"],
      ],
    });
  },
};
</script>

<style></style>
