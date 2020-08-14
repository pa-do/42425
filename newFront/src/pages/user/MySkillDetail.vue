<template>
  <div>
    <span v-if="!modifySkill">
      <h2 class="my-0">{{ skill.skill }}</h2>
      <div class="progress mt-0 mb-3">
        <div
          class="progress-bar btn-primary"
          role="progressbar"
          :aria-valuenow="skill.value"
          aria-valuemin="0"
          aria-valuemax="100"
          :style="'width:' + skill.value + '%'"
        >
          <span>{{ skill.value }}%</span>
        </div>
      </div>

      <div v-if="mine" class="d-flex justify-content-end">
        <n-button @click="modifySkill_on" class="btn btn-primary btn-round mr-1">수정</n-button>
        <n-button @click="deleteSkill" class="btn btn-danger btn-round">삭제</n-button>
      </div>
    </span>
    <span v-else>
      <div class="row">
        <div>
          <p class="mb-0">스킬 이름</p>
          <fg-input :placeholder="newSkill" v-model="newSkill"></fg-input>
        </div>
        <div>
          <p class="mb-0">능력치</p>
          <fg-input :placeholder="newValue" v-model="newValue"></fg-input>
        </div>
        <div class="d-flex justify-content-end">
          <n-button @click="modifySkill_" class="btn btn-primary btn-round mr-1">수정</n-button>
          <n-button @click="modifySkill_off" class="btn btn-danger btn-round">취소</n-button>
        </div>
      </div>
    </span>
  </div>
</template>

<script>
import { Button, FormGroupInput as FgInput } from "@/components";

export default {
  name: "myskilldetail",
  components: {
    [Button.name]: Button,
    FgInput,
  },
  props: ["sid", "mine"],
  data: () => {
    return {
      modifySkill: false,

      skill: "",

      newSkill: "",
      newValue: "",
    };
  },
  created() {
    this.getdata();
  },
  methods: {
    getdata() {
      this.$axios
        .get(`/portfolio/skill/detail/${this.sid}`)
        .then((res) => {
          this.skill = res.data.object;
        })
        .catch((err) => console.error(err));
    },
    modifySkill_on() {
      this.modifySkill = true;
      this.getdata();
      this.newSkill = this.skill.skill;
      this.newValue = this.skill.value;
    },
    modifySkill_off() {
      this.modifySkill = false;
      this.getdata();
    },
    modifySkill_() {
      if (this.newSkill.length > 15) {
        Swal.fire({
          icon: "warning",
          title: "스킬 이름이 너무 깁니다.",
          text: "스킬 이름을 15자 이하로 입력해주세요.",
        });
        return;
      }
      const val = this.newValue * 1;
      if (val < 0 || val > 100) {
        Swal.fire({
          icon: "warning",
          title: "잘못된 능력치입니다.",
          text: "능력치를 0 이상 100 이하의 숫자로 입력해주세요.",
        });
        return;
      }

      this.$axios
        .put(`/portfolio/skill/modify`, {
          sid: this.skill.sid,
          skill: this.newSkill,
          value: this.newValue,
          user: this.$session.get("user"),
        })
        .then((res) => {
          console.log(res);
          Swal.fire({
            icon: "success",
            title: "스킬 수정 성공",
            text: "스킬을 성공적으로 수정하였습니다.",
          });
          this.modifySkill_off();
          this.$emit("update");
        })
        .catch((err) => {
          console.log("Err!!! :", err.response);
        });
    },
    deleteSkill() {
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
            .delete(`/portfolio/skill/delete/${this.skill.sid}`)
            .then((res) => {
              console.log(res);
              Swal.fire({
                title: "삭제 완료!",
                text: "스킬이 영구적으로 삭제되었습니다.",
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