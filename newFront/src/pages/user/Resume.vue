<template>
  <div class="container">
    <div v-if="mine" class="text-right">
      <n-button @click="createResume" class="btn btn-primary">이력 추가</n-button>
    </div>
    <div class="row justify-content-between">
      <span v-for="resume in resumes" :key="resume.rid">
        <div class="d-block col-md-6 my-5">
          <ResumeDetail :rid="resume.rid" :mine="mine" @update="getdata" />
        </div>
      </span>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import { Button } from "@/components";
import ResumeDetail from "../user/ResumeDetail";

const BASE_URL = "http://localhost:8080";

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
      axios
        .get(BASE_URL + `/portfolio/resume/${this.uid}`)
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
          },
          {
            title: "퇴사년도",
            text: "네 자리의 숫자를 입력하세요. (2020)",
          },
          {
            title: "기업 또는 교육기관명",
            text: "기업 또는 교육기관의 이름을 입력하세요.",
          },
          {
            title: "직무",
            text: "직무를 입력하세요.",
          },
          {
            title: "업무 및 성과",
            text: "업무 및 성과를 입력하세요.",
          },
        ])
        .then((result) => {
          if (result.value) {
            const answers = JSON.stringify(result.value);
            Swal.fire({
              title: "All done!",
              html: `Your answers: <pre><code>${answers}</code></pre>`,
              confirmButtonText: "이력 등록",
            }).then(() => {
              axios
                .post(BASE_URL + `/portfolio/resume/create`, {
                  uid: this.uid,
                  startYear: result.value[0],
                  endYear: result.value[1],
                  title: result.value[2],
                  place: result.value[3],
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
                    this.$emit("update");
                  }
                })
                .catch((err) => {
                  console.log("Err!!! :", err.response);
                });
            });
          }
        });
    },
  },
};
</script>

<style>
</style>