<template>
  <div>
    <span v-if="!modifyResume">
      <span class="text-primary">{{resume.startYear}}-{{resume.endYear}}</span>
      <h2>{{resume.title}}</h2>
      <span>{{resume.place}}</span>
      <p class="mt-4">{{resume.content}}</p>
      <div v-if="mine">
        <n-button @click="modifyResume_on" class="btn btn-primary">수정</n-button>
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
  props: ["uid", "mine", "resume"],
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
        .get(BASE_URL + `/portfolio/resume/${this.rid}`)
        .then((res) => {
          console.log("here~!");
          this.resumes = res.data.object;
        })
        .catch((err) => console.error(err));
    },
    modifyResume_on() {
      this.modifyResume = true;
      console.log(this.modifyResume);
      this.getData();
    },
    modifyResume_off() {
      this.modifyResume = false;
      console.log(this.modifyResume);
      this.getData();
    },
    modifyResume_() {
      console.log(typeof resume);
    },
  },
};
</script>

<style>
</style>