<template>
  <div class="container text-left">
    <h1 class="text-center">글 쓰기</h1>
    <div class="form-group">
      <label for="nickname" class="mb-3">작성자</label>
      <input class="form-control" v-model="writeData.nickname" readonly id="nickname" />
    </div>
    <div class="form-group">
      <label for="title" class="mb-3">글 제목</label>
      <input type="text" class="form-control" v-model="writeData.title" id="title" />
    </div>
    <div class="form-group">
      <label for="content" class="mb-3">글 내용</label>
      <!-- <textarea class="form-control" v-model="writeData.content" id="content"></textarea> -->
      <div style="padding:2px">
        <button type="button" class="right btn btn-primary" onclick="xFn(this)">backColor</button><input class="form-control" type="text" size="8" />
        <button type="button" class="right btn btn-primary" onclick="xFn(this)">bold</button>
        <button type="button" class="right btn btn-primary" onclick="xFn(this)">copy</button>
        <button type="button" class="right btn btn-primary" onclick="xFn(this)">createLink</button><input class="form-control" type="text" size="16" />
        <button type="button" class="right btn btn-primary" onclick="xFn(this)">cut</button>
        <button type="button" class="right btn btn-primary" onclick="xFn(this)">decreaseFontSize</button>
        <button type="button" class="right btn btn-primary" onclick="xFn(this)">delete</button>
        <button type="button" class="right btn btn-primary" onclick="xFn(this)">enableInlineTableEditing</button>
        <button type="button" class="right btn btn-primary" onclick="xFn(this)">enableObjectResizing</button>
        <button type="button" class="right btn btn-primary" onclick="xFn(this)">fontName</button><input class="form-control" type="text" size="8" />
        <button type="button" class="right btn btn-primary" onclick="xFn(this)">fontSize</button><input class="form-control" type="text" size="2" />
        <button type="button" class="right btn btn-primary" onclick="xFn(this)">foreColor</button><input class="form-control" type="text" size="8" />
        <button type="button" class="right btn btn-primary" onclick="xFn(this)">formatBlock</button><input class="form-control" type="text" size="8" />
        <button type="button" class="right btn btn-primary" onclick="xFn(this)">forwardDelete</button>
        <button type="button" class="right btn btn-primary" onclick="xFn(this)">heading</button><input class="form-control" type="text" size="3" />
        <button type="button" class="right btn btn-primary" onclick="xFn(this)">hiliteColor</button><input class="form-control" type="text" size="8" />
        <button type="button" class="right btn btn-primary" onclick="xFn(this)">increaseFontSize</button>
        <button type="button" class="right btn btn-primary" onclick="xFn(this)">indent</button>
        <button type="button" class="right btn btn-primary" onclick="xFn(this)">insertBrOnReturn</button>
        <button type="button" class="right btn btn-primary" onclick="xFn(this)">insertHorizontalRule</button>
        <button type="button" class="right btn btn-primary" onclick="xFn(this)">insertHTML</button><input class="form-control" type="text" size="16" />
        <button type="button" class="right btn btn-primary" onclick="xFn(this)">insertImage</button><input class="form-control" type="text" size="16" />
        <button type="button" class="right btn btn-primary" onclick="xFn(this)">insertOrderedList</button>
        <button type="button" class="right btn btn-primary" onclick="xFn(this)">insertUnorderedList</button>
        <button type="button" class="right btn btn-primary" onclick="xFn(this)">insertParagraph</button>
        <button type="button" class="right btn btn-primary" onclick="xFn(this)">insertText</button><input class="form-control" type="text" size="16" />
        <button type="button" class="right btn btn-primary" onclick="xFn(this)">italic</button>
        <button type="button" class="right btn btn-primary" onclick="xFn(this)">justifyCenter</button>
        <button type="button" class="right btn btn-primary" onclick="xFn(this)">justifyFull</button>
        <button type="button" class="right btn btn-primary" onclick="xFn(this)">justifyLeft</button>
        <button type="button" class="right btn btn-primary" onclick="xFn(this)">justifyRight</button>
        <button type="button" class="right btn btn-primary" onclick="xFn(this)">outdent</button>
        <button type="button" class="right btn btn-primary" onclick="xFn(this)">paste</button>
        <button type="button" class="right btn btn-primary" onclick="xFn(this)">redo</button>
        <button type="button" class="right btn btn-primary" onclick="xFn(this)">removeFormat</button>
        <button type="button" class="right btn btn-primary" onclick="xFn(this)">selectAll</button>
        <button type="button" class="right btn btn-primary" onclick="xFn(this)">strikeThrough</button>
        <button type="button" class="right btn btn-primary" onclick="xFn(this)">subscript</button>
        <button type="button" class="right btn btn-primary" onclick="xFn(this)">superscript</button>
        <button type="button" class="right btn btn-primary" onclick="xFn(this)">underline</button>
        <button type="button" class="right btn btn-primary" onclick="xFn(this)">undo</button>
        <button type="button" class="right btn btn-primary" onclick="xFn(this)">unlink</button>
        <button type="button" class="right btn btn-primary" onclick="xHv()">HTML</button>
      </div>
      <iframe id="yF" src="about:blank" style="box-sizing:border-box;border:1px solid #999;padding:2px;width:100%;height:150px;"></iframe>
      <div id="yM" style="padding:2px"></div>
    </div>
    <br />
    <button @click="writeBoard" class="right btn btn-primary">확인</button>
  </div>
</template>

<script>
import axios from "axios";
import router from "../../router";

const BASE_URL = "http://localhost:8080";

export default {
  name: "Write",
  data() {
    return {
      writeData: {
        content: "",
        title: "",
        uid: this.$session.get("user").uid,
        nickname: this.$session.get("user").nickname,
        xE: {},
      },
    };
  },
  methods: {
    writeBoard() {
      // this.writeData.uid = this.$cookie.get("auth-token");
      if (!this.writeData.title) {
        alert("제목이 입력되지 않았습니다!");
      } else if (!this.writeData.content) {
        alert("내용이 입력되지 않았습니다!");
      } else {
        axios
          .post(BASE_URL + "/board/write", null, {
            params: {
              content: this.writeData.content,
              title: this.writeData.title,
              uid: this.writeData.uid,
            },
          })
          .then(() => {
            this.$router.push("/");
          })
          .catch((err) => {
            console.log(err.response);
          });
      }
    },
    xFn(e) {
      xE.w.focus();

      var c = e.innerHTML,
        a = c + "<br>queryCommandSupported : " + xE.d.queryCommandSupported(c);

      e = e.nextSibling;
      e = e.nodeName.toLowerCase() == "input" ? e.value : null;

      a += "<br>queryCommandEnabled : ";
      try {
        a += xE.d.queryCommandEnabled(c);
      } catch (x) {
        a += "Error : " + x.message;
      }
      a += "<br>queryCommandState : ";
      try {
        a += xE.d.queryCommandState(c);
      } catch (x) {
        a += "Error : " + x.message;
      }
      a += "<br>queryCommandValue : ";
      try {
        a += xE.d.queryCommandValue(c);
      } catch (x) {
        a += "Error : " + x.message;
      }
      a += "<br>execCommand : ";
      try {
        a += xE.d.execCommand(c, false, e);
      } catch (x) {
        a += "Error : " + x.message;
      }

      xE.m.innerHTML = a;
    },
    xHv() {
      xE.m.innerHTML = xE.d.body.innerHTML
        .replace(/&/g, "&amp;")
        .replace(/</g, "&lt;")
        .replace(/>/g, "&gt;");
    },
  },
  mounted() {
    if (!this.$session.get("user")) {
      alert("올바른 접근이 아닙니다.");
      this.$router.push("/");
    } else {
      xE.w = document.getElementById("yF").contentWindow;
      xE.d = xE.w.document;

      xE.d.write("<!DOCTYPE html><html><head></head><body></body></html>");
      xE.d.designMode = "on";

      xE.m = document.getElementById("yM");

      xE.w.focus();
    }
  },
};
</script>

<style></style>
