<template>
  <div class="container text-left">
    <div class="form-group">
      <label for="exampleFormControlInput1" class="mb-3">글 제목</label>
      <fg-input type="text" v-model="writeData.title" id="exampleFormControlInput1"></fg-input>
    </div>
    <div class="form-group">
      <label for="exampleFormControlTextarea1" class="mb-3">글 내용</label>
      <Editor id="content" ref="content" mode="wysiwyg" height="500px" />
      <!-- <textarea class="form-control" id="exampleFormControlTextarea1" v-model="writeData.content" rows="3"></textarea> -->
    </div>
    <br />
    <n-button @click="writeBoard" class="right btn btn-primary">확인</n-button>
  </div>
</template>

<script>
import { Button, FormGroupInput as FgInput } from "@/components"
import "codemirror/lib/codemirror.css"
import "@toast-ui/editor/dist/toastui-editor.css"
import { Editor } from "@toast-ui/vue-editor"

export default {
  name: "Write",
  data() {
    return {
      writeData: {
        content: "",
        title: "",
        uid: null,
      },
    }
  },
  components: {
    [Button.name]: Button,
    FgInput,
    Editor,
  },
  methods: {
    writeBoard() {
      this.writeData.content = this.$refs.content.invoke("getHtml")
      this.writeData.uid = this.$cookie.get("auth-token")
      this.writeData.title = this.writeData.title.trim()
      console.log(this.writeData.content)
      if (!this.writeData.title || !this.writeData.content) {
        Swal.fire({
          icon: "warning",
          title: "제목 또는 내용이 입력되지<br>않았습니다.",
        })
      } else {
        this.$axios
          .post("/board/write", null, {
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
            })
            this.$emit("postWrite")
          })
          .catch((err) => {
            console.log("!!!!!!")
            console.log(err.response)
          })
      }
    },
  },
}
</script>

<style></style>
