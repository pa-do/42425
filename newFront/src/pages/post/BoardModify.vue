<template>
  <div class="container text-left">
    <h1 class="text-center">글 수정</h1>
    <!-- <div class="form-group"> -->
    <label for="exampleFormControlInput2" class="mb-3">글 제목</label>
    <input type="text" class="form-control mb-4" v-model="title" id="exampleFormControlInput2" />
    <h6 class="form-text text-muted mb-5">45글자 이상은 안됩니다.</h6>
    <label v-if="!isRemoveImg" for="mainImg" class="mb-3">메인 이미지 수정(목록 화면에 노출됩니다)</label><br />
    <input v-if="!isRemoveImg" type="file" ref="mainImg" id="mainImg" accept />
    <input hidden v-else type="file" ref="mainImg" id="mainImg" accept /><br />
    <n-checkbox v-model="isRemoveImg">메인 이미지 제거</n-checkbox>
    <!-- </div> -->
    <!-- <div class="form-group"> -->
    <label for="exampleFormControlTextarea2" class="mb-3">글 내용</label>
    <editor id="content" ref="content" mode="wysiwyg" height="500px" />
    <!-- </div> -->
    <br />
    <n-button @click="imgUpload" class="right btn btn-primary">본문 이미지 첨부</n-button>
    <n-button v-on:change="selectMainImg()" @click="writeBoard" class="right btn btn-primary">확인</n-button>
  </div>
</template>

<script>
import { Button } from "@/components"
import "codemirror/lib/codemirror.css"
import "@toast-ui/editor/dist/toastui-editor.css"
import { Editor } from "@toast-ui/vue-editor"
import { Checkbox } from "@/components"

export default {
  components: {
    [Checkbox.name]: Checkbox,
    [Button.name]: Button,
    editor: Editor,
  },
  data: function() {
    return {
      title: "",
      content: "",
      board: Object,
      isRemoveImg: false,
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
          this.$refs.content.invoke("setHtml", this.content)
        })

        .catch((err) => console.error(err))
    },
    selectMainImg() {
      this.mainImg = this.$refs.mainImg.files[0]
    },
    writeBoard() {
      this.title = this.title.trim()
      this.content = this.$refs.content.invoke("getHtml")
      if (!this.title || !this.content) {
        Swal.fire({
          icon: "warning",
          title: "제목 또는 내용이 입력되지<br>않았습니다.",
        })
      } else {
        const formData = new FormData()
        formData.append("file", this.$refs.mainImg.files[0])
        this.board.uid = this.$cookie.get("auth-token")
        this.$axios
          .put("/board/modify", formData, {
            headers: { "content-Type": "multipart/form-data" },
            params: {
              bid: this.board.bid,
              content: this.content,
              title: this.title,
              uid: this.board.uid,
              isRemoveImg: this.isRemoveImg,
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

    async imgUpload() {
      const { value: file } = await Swal.fire({
        title: "Select image",
        input: "file",
        inputAttributes: {
          accept: "image/*",
          "aria-label": "첨부할 이미지를 선택하세요.",
        },
      })
      if (file) {
        const reader = new FileReader()
        reader.onload = (e) => {
          Swal.fire({
            title: "이미지 첨부",
            imageUrl: e.target.result,
            imageAlt: "The uploaded picture",
          })
        }
        reader.readAsDataURL(file)

        const formData = new FormData()
        formData.append("boardImg", file)
        this.$axios
          .post(`/file/uploadBoardImg/`, formData, {
            headers: { "content-Type": "multipart/form-data" },
          })
          .then((response) => {
            this.$refs.content.invoke("setHtml", this.$refs.content.invoke("getHtml") + "<img src='" + response.data.object + "'/>")
          })
          .catch((err) => {
            console.err("Err!!! :", err.response)
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
