<template>
  <div class="container text-left mt-5">
    <!-- <div class="form-group"> -->
    <label for="exampleFormControlInput2" class="mb-3 mt-5"
      >프로젝트 제목</label
    >
    <fg-input
      type="text"
      v-model="writeData.title"
      id="exampleFormControlInput2"
    ></fg-input>
    <h6 class="form-text text-muted mb-5">45글자 이상은 안됩니다.</h6>
    <div class="row d-flex justify-content-between">
      <div class="col-5 mr-1">
        <p class="mb-0">프로젝트 시작날짜</p>
        <input type="date" v-model="writeData.startdate" />
      </div>
      <div class="col-5">
        <p class="mb-0">끝낸날짜</p>
        <input type="date" v-model="writeData.enddate" />
      </div>
    </div>
    <label for="mainImg" class="mb-5"
      >메인 이미지 업로드(목록 화면에 노출됩니다)</label
    ><br />
    <input type="file" ref="mainImg" id="mainImg" accept /><br />
    <label for="exampleFormControlTextarea1" class="mb-3"
      >프로젝트 요약(목록 화면에 노출됩니다)</label
    >
    <textarea
      class="form-control"
      id="exampleFormControlTextarea1"
      v-model="writeData.content"
      rows="3"
    ></textarea>
    <label for="exampleFormControlTextarea1" class="mb-3">프로젝트 내용</label>
    <Editor id="content" ref="content" mode="wysiwyg" height="500px" />
    <!-- <textarea class="form-control" id="exampleFormControlTextarea1" v-model="writeData.content" rows="3"></textarea> -->
    <!-- </div> -->
    <br />
    <n-button @click="imgUpload" class="right btn btn-primary"
      >본문 이미지 첨부</n-button
    >
    <n-button
      v-on:change="selectMainImg()"
      @click="writeProject"
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
        startdate: null,
        enddate: null,
        summary: "",
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
    writeProject() {
      this.writeData.content = this.$refs.content.invoke("getHtml");
      this.writeData.uid = this.$cookie.get("auth-token");
      this.writeData.title = this.writeData.title.trim();
      this.writeData.summary = this.writeData.title.trim();
      if (
        !this.writeData.title ||
        !this.writeData.content ||
        !this.writeData.summary
      ) {
        Swal.fire({
          icon: "warning",
          title: "제목, 요약 또는 내용이 입력되지<br>않았습니다.",
        });
      } else if (this.writeData.title.length > 45) {
        Swal.fire({
          icon: "warning",
          title: "제목이 너무 깁니다.",
          text: "제목을 45자 미만으로 입력하세요.",
        });
        return;
      } else if (this.writeData.summary.length > 50) {
        Swal.fire({
          icon: "warning",
          title: "요약이 너무 깁니다.",
          text: "요약을 50자 미만으로 입력하세요.",
        });
        return;
      } else {
        const formData = new FormData();
        formData.append("file", this.$refs.mainImg.files[0]);

        this.$axios
          .post("/project/write", formData, {
            headers: { "content-Type": "multipart/form-data" },
            params: {
              content: this.writeData.content,
              title: this.writeData.title,
              summary: this.writeData.summary,
              startdate: this.writeData.startdate,
              enddate: this.writeData.enddate,
              uid: this.writeData.uid,
            },
          })
          .then(() => {
            Swal.fire({
              icon: "success",
              title: "프로젝트가 등록되었습니다.",
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
        formData.append("projectImg", file);
        this.$axios
          .post(`/file/uploadProjectImg/`, formData, {
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
