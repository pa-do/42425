<template>
  <div class="container">
    <div v-if="mine" class="text-right">
      <n-button @click="createResume" round class="btn btn-primary">
        <i class="fas fa-plus fa-2x"></i>
      </n-button>
    </div>
    <div class="row justify-content-between">
      <div class="d-block col-md-6 px-5 py-1"></div>
      <div class="d-block col-md-6 px-5 py-1"></div>
      <span v-for="resume in resumes" :key="resume.rid" class="col-md-6">
        <ResumeDetail :rid="resume.rid" :mine="mine" @update="getdata" />
      </span>
    </div>
  </div>
</template>

<script>
import { Button } from "@/components";
import ResumeDetail from "../user/ResumeDetail";

export default {
  name: "resume",
  components: {
    [Button.name]: Button,
    ResumeDetail,
  },
  props: ["uid", "mine"],
  data: () => {
    return {
      resumes: [],
      modifyResume: false,
    };
  },
  created() {
    this.getdata();
  },
  methods: {
    getdata() {
      this.$axios
        .get(`/portfolio/resume/${this.uid}`)
        .then((res) => {
          this.resumes = res.data.object;
        })
        .catch((err) => console.error(err));
    },
    createResume() {
      Swal.mixin({
        input: "text",
        confirmButtonText: "Next &rarr;",
        showCancelButton: true,
        progressSteps: ["1", "2", "3", "4", "5"],
      })
        .queue([
          {
            title: "입사년도",
            text: "네 자리의 숫자를 입력하세요. (2020)",
            inputValidator: (value) => {
              return new Promise((resolve) => {
                if ((1900 <= value) & (value <= 2020)) {
                  resolve();
                } else {
                  resolve("입사년도를 네 자리의 숫자로 입력해주세요.");
                }
              });
            },
          },
          {
            title: "퇴사년도",
            text: "네 자리의 숫자를 입력하세요. (2020)",
            inputValidator: (value) => {
              return new Promise((resolve) => {
                if ((1900 <= value) & (value <= 2020)) {
                  resolve();
                } else {
                  resolve("퇴사년도를 네 자리의 숫자로 입력해주세요.");
                }
              });
            },
          },
          {
            title: "기업 또는 교육기관명",
            text: "기업 또는 교육기관의 이름을 입력하세요.",
            inputValidator: (value) => {
              return new Promise((resolve) => {
                if (value.length <= 45) {
                  resolve();
                } else {
                  resolve("기업 또는 교육기관명을 45자 이하로 입력하세요.");
                }
              });
            },
          },
          {
            title: "직무",
            text: "직무를 입력하세요.",
            inputValidator: (value) => {
              return new Promise((resolve) => {
                if (value.length <= 45) {
                  resolve();
                } else {
                  resolve("직무를 45자 이하로 입력하세요.");
                }
              });
            },
          },
          {
            title: "업무 및 성과",
            text: "업무 및 성과를 입력하세요.",
            inputValidator: (value) => {
              return new Promise((resolve) => {
                if (value.length <= 100) {
                  resolve();
                } else {
                  resolve("성과를 100자 이하로 입력하세요.");
                }
              });
            },
          },
        ])
        .then((result) => {
          if (result.value) {
            this.$axios
              .post(`/portfolio/resume/create`, {
                uid: this.uid,
                startYear: result.value[0],
                endYear: result.value[1],
                place: result.value[2],
                title: result.value[3],
                content: result.value[4],
              })
              .then((response) => {
                this.result = response.data;
                if (this.result.data != "fail") {
                  Swal.fire({
                    icon: "success",
                    title: "이력 등록 완료",
                    text: "새로운 이력을 등록하였습니다..",
                  });
                  this.getdata();
                }
              })
              .catch((err) => {
                console.log("Err!!! :", err.response);
              });
          }
        });
    },
  },
};
</script>

<style>
</style>