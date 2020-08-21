<template>
  <div class="container">
    <div v-if="mine" class="text-right">
      <n-button @click="createSkill" round class="btn btn-primary">
        <i class="fas fa-plus fa-2x"></i>
      </n-button>
    </div>
    <div class="row justify-content-between">
      <div class="d-block col-md-6 px-5 py-1"></div>
      <div class="d-block col-md-6 px-5 py-1"></div>
      <span v-for="skill in myskills" :key="skill.sid" class="col-md-6">
        <MySkillDetail :sid="skill.sid" :mine="mine" @update="getdata" />
      </span>
    </div>
  </div>
</template>

<script>
import { Button } from "@/components";
import MySkillDetail from "../user/MySkillDetail";

export default {
  name: "myskill",
  components: {
    [Button.name]: Button,
    MySkillDetail,
  },
  props: ["uid", "mine"],
  data: () => {
    return {
      myskills: [],
      modifySkills: false,
    };
  },
  created() {
    this.getdata();
  },
  methods: {
    getdata() {
      this.$axios
        .get(`/portfolio/skill/${this.uid}`)
        .then((res) => {
          this.myskills = res.data.object;
        })
        .catch((err) => console.error(err));
    },
    createSkill() {
      Swal.mixin({
        input: "text",
        confirmButtonText: "Next &rarr;",
        showCancelButton: true,
        progressSteps: ["1", "2"],
      })
        .queue([
          {
            title: "기술명",
            text: "기술 이름을 입력하세요.",
            inputValidator: (value) => {
              return new Promise((resolve) => {
                if (value.length <= 15) {
                  resolve();
                } else {
                  resolve("기술 이름을 15자 이하로 입력하세요.");
                }
              });
            },
          },
          {
            title: "숙련도",
            text: "숙련도를 0 이상 100 이하의 숫자로 입력하세요.",
            inputValidator: (value) => {
              return new Promise((resolve) => {
                if ((0 <= value) & (value <= 100)) {
                  resolve();
                } else {
                  resolve("숙련도를 0 이상 100 이하의 숫자로 입력하세요.");
                }
              });
            },
          },
        ])
        .then((result) => {
          if (result.value) {
            const answers = JSON.stringify(result.value);
            this.$axios
              .post(`/portfolio/skill/create`, {
                user: this.$session.get("user"),
                skill: result.value[0],
                value: result.value[1],
              })
              .then((response) => {
                this.result = response.data;
                if (this.result.data != "fail") {
                  Swal.fire({
                    icon: "success",
                    title: "기술 등록 완료",
                    text: "새로운 기술을 등록하였습니다..",
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
