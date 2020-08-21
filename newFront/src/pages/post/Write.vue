<template>
  <div class="container text-left">
    <!-- <div class="form-group"> -->
    <label for="exampleFormControlInput1" class="mb-3">글 제목</label>
    <fg-input
      type="text"
      v-model="writeData.title"
      id="exampleFormControlInput1"
    ></fg-input>
    <h6 class="form-text text-muted mb-5">45글자 이상은 안됩니다.</h6>
    <label for="mainImg" class="mb-3"
      >메인 이미지 업로드(목록 화면에 노출됩니다)</label
    ><br />
    <input type="file" ref="mainImg" id="mainImg" accept /><br />
    <!-- </div> -->
    <!-- <div class="form-group"> -->
    <label for="exampleFormControlTextarea1" class="mt-5 mb-3">글 내용</label>
    <Editor id="content" ref="content" mode="wysiwyg" height="500px" />
    <!-- <textarea class="form-control" id="exampleFormControlTextarea1" v-model="writeData.content" rows="3"></textarea> -->
    <!-- </div> -->
    <br />
    <n-button @click="imgUpload" class="right btn btn-primary"
      >본문 이미지 첨부</n-button
    >
    <n-button
      v-on:change="selectMainImg()"
      @click="writeBoard"
      class="right btn btn-primary"
      >확인</n-button
    >
  </div>
</template>

<script>
import { Button, FormGroupInput as FgInput } from "@/components";
import "codemirror/lib/codemirror.css";
import "@toast-ui/editor/dist/toastui-editor.css";
import { Editor } from "@toast-ui/vue-editor";

export default {
  name: "Write",
  data() {
    return {
      writeData: {
        content: "",
        title: "",
        uid: null,
        mainImg: null,
      },
    };
  },
  components: {
    [Button.name]: Button,
    FgInput,
    Editor,
  },
  methods: {
    selectMainImg() {
      this.mainImg = this.$refs.mainImg.files[0];
    },
    writeBoard() {
      this.writeData.content = this.$refs.content.invoke("getHtml");
      this.writeData.uid = this.$cookie.get("auth-token");
      this.writeData.title = this.writeData.title.trim();
      if (!this.writeData.title || !this.writeData.content) {
        Swal.fire({
          icon: "warning",
          title: "제목 또는 내용이 입력되지<br>않았습니다.",
        });
      } else if (this.writeData.title.length > 45) {
        Swal.fire({
          icon: "warning",
          title: "글 제목이 너무 깁니다.",
          text: "제목을 45자 미만으로 입력하세요.",
        });
        return;
      } else {
        const formData = new FormData();
        formData.append("file", this.$refs.mainImg.files[0]);

        this.$axios
          .post("/board/write", formData, {
            headers: { "content-Type": "multipart/form-data" },
            params: {
              content: this.writeData.content,
              title: this.writeData.title,
              uid: this.writeData.uid,
            },
          })
          .then(() => {
            Swal.fire({
              icon: "success",
              title: "글이 작성되었습니다.",
            });
            this.$emit("postWrite");
          })
          .catch((err) => {
            console.log("!!!!!!");
            console.log(err.response);
          });
      }
    },

    async imgUpload() {
      const { value: file } = await Swal.fire({
        title: "Select image",
        input: "file",
        inputAttributes: {
          accept: "image/*",
          "aria-label": "첨부할 이미지를 선택하세요.",
        },
      });
      if (file) {
        const reader = new FileReader();
        reader.onload = (e) => {
          Swal.fire({
            title: "이미지 첨부",
            imageUrl: e.target.result,
            imageAlt: "The uploaded picture",
          });
        };
        reader.readAsDataURL(file);

        const formData = new FormData();
        formData.append("boardImg", file);
        this.$axios
          .post(`/file/uploadBoardImg/`, formData, {
            headers: { "content-Type": "multipart/form-data" },
          })
          .then((response) => {
            this.$refs.content.invoke(
              "setHtml",
              this.$refs.content.invoke("getHtml") +
                "<img src='" +
                response.data.object +
                "'/>"
            );
          })
          .catch((err) => {
            console.err("Err!!! :", err.response);
          });
      }
    },
  },
};
</script>

<style></style>
