<template>
  <div class="shadow p-3 mb-5 bg-white rounded">
    <span v-if="!modifyResume">
      <span class="text-primary">{{resume.startYear}}-{{resume.endYear}}</span>
      <h3 class="mb-1">{{resume.title}}</h3>
      <span>{{resume.place}}</span>
      <p class="my-2">{{resume.content}}</p>
      <div v-if="mine" class="d-flex justify-content-end">
        <n-button @click="modifyResume_on" class="btn btn-primary btn-round mr-1" size="sm">수정</n-button>
        <n-button @click="deleteResume" class="btn btn-danger btn-round" size="sm">삭제</n-button>
      </div>
    </span>
    <span v-else>
      <div class="row d-flex justify-content-between">
        <div class="col-5 mr-1">
          <p class="mb-0">입사 년도</p>
          <fg-input
            v-model="newSYear"
            id="newSYear"
            placeholder="입사년도를 입력하세요."
            type="number"
            class="no-border form-control-md my-0"
          ></fg-input>
        </div>
        <div class="col-5">
          <p class="mb-0">퇴사 년도</p>
          <fg-input
            v-model="newEYear"
            id="newEYear"
            placeholder="퇴사년도를 입력하세요."
            type="number"
            class="no-border form-control-md my-0"
          ></fg-input>
        </div>
      </div>
      <div class="my-3">
        <p class="mb-0">기업/교육기관명</p>
        <fg-input
          v-model="newPlace"
          id="newPlace"
          placeholder="기업/교육기관명을 입력하세요."
          type="text"
          class="no-border form-control-md my-0"
        ></fg-input>
      </div>
      <div class="my-3">
        <p class="mb-0">직무</p>
        <fg-input
          v-model="newTitle"
          id="newTitle"
          placeholder="직무를 입력하세요."
          type="textr"
          class="no-border form-control-md my-0"
        ></fg-input>
      </div>
      <div class="my-3">
        <p class="mb-0">업무 및 성과</p>
        <fg-input
          v-model="newContent"
          id="newContent"
          placeholder="업무 및 성과를 입력하세요."
          type="textr"
          class="no-border form-control-md my-0"
        ></fg-input>
      </div>
      <div class="text-right">
        <n-button @click="modifyResume_" class="btn btn-primary" size="sm">수정</n-button>
        <n-button @click="modifyResume_off" class="btn btn-danger" size="sm">취소</n-button>
      </div>
    </span>
  </div>
</template>

<script>
import { Button, FormGroupInput as FgInput } from "@/components";

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
      this.$axios
        .get(`/portfolio/resume/detail/${this.rid}`)
        .then((res) => {
          this.resume = res.data.object;
        })
        .catch((err) => console.error(err));
    },
    modifyResume_on() {
      this.modifyResume = true;
      this.getdata();
      this.newSYear = this.resume.startYear;
      this.newEYear = this.resume.endYear;
      this.newTitle = this.resume.title;
      this.newPlace = this.resume.place;
      this.newContent = this.resume.content;
    },
    modifyResume_off() {
      this.modifyResume = false;
      this.getdata();
    },
    modifyResume_() {
      const nsy = this.newSYear * 1;
      if (nsy < 1900 || nsy > 3000) {
        Swal.fire({
          icon: "warning",
          title: "잘못된 입사년도입니다.",
          text: "입사년도를 네 자리의 숫자로 입력해주세요.",
        });
        return;
      }
      const ney = this.newEYear * 1;
      if (ney < 1900 || ney > 3000) {
        Swal.fire({
          icon: "warning",
          title: "잘못된 퇴사년도입니다.",
          text: "퇴사년도를 네 자리의 숫자로 입력해주세요.",
        });
        return;
      }
      if (this.newPlace.length > 45) {
        Swal.fire({
          icon: "warning",
          title: "기엽/교육기관 명이 너무 깁니다.",
          text: "기업/교육기관 명을 45자 미만으로 입력하세요.",
        });
        return;
      }
      if (this.newTitle.length > 45) {
        Swal.fire({
          icon: "warning",
          title: "직무가 너무 깁니다.",
          text: "직무를 45자 미만으로 입력하세요.",
        });
        return;
      }
      if (this.newContent.length > 100) {
        Swal.fire({
          icon: "warning",
          title: "업무 및 성과가 너무 깁니다.",
          text: "업무 및 성과를 100자 미만으로 입력하세요.",
        });
        return;
      }

      this.$axios
        .put(`/portfolio/resume/modify`, {
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
      Swal.fire({
        title: "정말 삭제하시겠어요?",
        text:
          "확인 버튼을 누르면 모든 데이터가 영구적으로 삭제되어 복구할 수 없게 됩니다.",
        icon: "warning",
        showCancelButton: true,
        confirmButtonColor: "#d33",
        cancelButtonColor: "#3085d6",
        confirmButtonText: "삭제할래요😥",
        cancelButtonText: "안할래요😊",
      }).then((result) => {
        if (result.value) {
          this.$axios
            .delete(
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
        }
      });
    },
  },
};
</script>

<style>
</style>