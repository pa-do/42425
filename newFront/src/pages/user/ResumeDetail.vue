<template>
  <div>
    <span v-if="!modifyResume">
      <span class="text-primary">{{resume.startYear}}-{{resume.endYear}}</span>
      <h2>{{resume.title}}</h2>
      <span>{{resume.place}}</span>
      <p class="mt-4">{{resume.content}}</p>
      <div v-if="mine">
        <n-button @click="modifyResume_on" class="btn btn-primary">수정</n-button>
        <n-button @click="deleteResume" class="btn btn-danger">삭제</n-button>
      </div>
    </span>
    <span v-else>
      <div class="row">
        <fg-input
          v-model="newSYear"
          id="newSYear"
          placeholder="입사년도를 입력하세요."
          type="number"
          class="no-border form-control-md my-3"
        ></fg-input>
        <fg-input
          v-model="newEYear"
          id="newEYear"
          placeholder="퇴사년도를 입력하세요."
          type="number"
          class="no-border form-control-md my-3"
        ></fg-input>
      </div>
      <fg-input
        v-model="newPlace"
        id="newPlace"
        placeholder="기업/교육기관명을 입력하세요."
        type="text"
        class="no-border form-control-md my-3"
      ></fg-input>
      <fg-input
        v-model="newTitle"
        id="newTitle"
        placeholder="직무를 입력하세요."
        type="textr"
        class="no-border form-control-md my-3"
      ></fg-input>
      <fg-input
        v-model="newContent"
        id="newContent"
        placeholder="업무 및 성과를 입력하세요."
        type="textr"
        class="no-border form-control-md my-3"
      ></fg-input>
      <div class="text-right">
        <n-button @click="modifyResume_" class="btn btn-primary">수정</n-button>
        <n-button @click="modifyResume_off" class="btn btn-danger">취소</n-button>
      </div>
    </span>
  </div>
</template>

<script>
import axios from "axios";
import { Button, FormGroupInput as FgInput } from "@/components";

const BASE_URL = "http://localhost:8080";

export default {
  name: "resume",
  components: {
    [Button.name]: Button,
    FgInput,
  },
  props: ["rid", "mine"],
  data: () => {
    return {
      modifyResume: false,

      resume: "",

      newSYear: "",
      newEYear: "",
      newTitle: "",
      newPlace: "",
      newContent: "",
    };
  },
  created() {
    this.getdata();
  },
  methods: {
    getdata() {
      axios
        .get(BASE_URL + `/portfolio/resume/detail/${this.rid}`)
        .then((res) => {
          this.resume = res.data.object;
        })
        .catch((err) => console.error(err));
    },
    modifyResume_on() {
      this.modifyResume = true;
      console.log(this.modifyResume);
      this.getdata();
      this.newSYear = this.resume.startYear;
      this.newEYear = this.resume.endYear;
      this.newTitle = this.resume.title;
      this.newPlace = this.resume.place;
      this.newContent = this.resume.content;
    },
    modifyResume_off() {
      this.modifyResume = false;
      console.log(this.modifyResume);
      this.getdata();
    },
    modifyResume_() {
      axios
        .put(BASE_URL + `/portfolio/resume/modify`, {
          uid: this.resume.uid,
          rid: this.resume.rid,
          startYear: this.newSYear,
          endYear: this.newEYear,
          title: this.newTitle,
          place: this.newPlace,
          content: this.newContent,
        })
        .then((res) => {
          console.log(res);
          Swal.fire({
            icon: "success",
            title: "이력 수정 성공",
            text: "이력을 성공적으로 수정하였습니다.",
          });
          this.modifyResume_off();
          this.$emit("update");
        })
        .catch((err) => {
          console.log("Err!!! :", err.response);
        });
    },
    deleteResume() {
      axios
        .delete(
          BASE_URL +
            `/portfolio/resume/delete/${this.resume.uid}/${this.resume.rid}`
        )
        .then((res) => {
          console.log(res);
          Swal.fire({
            title: "삭제 완료!",
            text: "이력이 영구적으로 삭제되었습니다.",
            icon: "success",
            showConfirmButton: true,
            confirmButtonText: "확인",
          }).then(() => {
            this.$emit("update");
          });
        })
        .catch((err) => {
          console.log("Err!!!: ", err);
        });
    },
  },
};
</script>

<style>
</style>