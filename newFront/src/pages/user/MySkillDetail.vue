<template>
  <div class="my-4">
    <span v-if="!modifySkill">
      <h3 class="my-0">{{ skill.skill }}</h3>
      <div class="progress my-3">
        <div
          class="progress-bar progress-bar-striped progress-bar-animated bg-primary"
          :style="'width:' + skill.value + '%' "
          role="progressbar"
          :aria-valuenow="skill.value"
          aria-valuemin="0"
          aria-valuemax="100"
        >{{skill.value}} %</div>
      </div>

      <div v-if="mine" class="d-flex justify-content-end">
        <n-button @click="modifySkill_on" class="btn btn-primary btn-round mr-1 mt-0" size="sm">수정</n-button>
        <n-button @click="deleteSkill" class="btn btn-danger btn-round mt-0" size="sm">삭제</n-button>
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

<style lang="scss" scoped>
.semi-donut {
  --percentage: 0;
  --fill: #ff0;
  width: 300px;
  height: 150px;
  position: relative;
  color: #fff;
  font-size: 22px;
  font-weight: 600;
  overflow: hidden;
  color: var(--fill);
  display: flex;
  align-items: flex-end;
  justify-content: center;
  box-sizing: border-box;
  &:after {
    content: "";
    width: 300px;
    height: 300px;
    border: 50px solid;
    border-color: rgba(0, 0, 0, 0.15) rgba(0, 0, 0, 0.15) var(--fill)
      var(--fill);
    position: absolute;
    border-radius: 50%;
    left: 0;
    top: 0;
    box-sizing: border-box;
    transform: rotate(calc(1deg * (-45 + var(--percentage) * 1.8)));
    animation: fillAnimation 1s ease-in;
  }
}

// ===================Semi Donut Chart model-2========================

.semi-donut-model-2 {
  width: 300px;
  height: 150px;
  position: relative;
  text-align: center;
  color: #fff;
  font-size: 22px;
  font-weight: 600;
  border-radius: 150px 150px 0 0;
  overflow: hidden;
  color: var(--fill);
  display: flex;
  align-items: flex-end;
  justify-content: center;
  box-sizing: border-box;
  &:before,
  &:after {
    content: "";
    width: 300px;
    height: 150px;
    border: 50px solid var(--fill);
    border-top: none;
    position: absolute;
    transform-origin: 50% 0% 0;
    border-radius: 0 0 300px 300px;
    box-sizing: border-box;
    left: 0;
    top: 100%;
  }
  &:before {
    border-color: rgba(0, 0, 0, 0.15);
    transform: rotate(180deg);
  }
  &:after {
    z-index: 3;
    animation: 1s fillGraphAnimation ease-in;
    transform: rotate(calc(1deg * (var(--percentage) * 1.8)));
  }
  &:hover {
    &:after {
      opacity: 0.8;
      cursor: pointer;
    }
  }
}

// ===================Multi Semi Donut Chart ========================

.multi-graph {
  width: 300px;
  height: 150px;
  position: relative;
  color: #fff;
  font-size: 22px;
  font-weight: 600;
  display: flex;
  align-items: flex-end;
  justify-content: center;
  overflow: hidden;
  box-sizing: border-box;
  &:before {
    content: "";
    width: 300px;
    height: 150px;
    border: 50px solid rgba(0, 0, 0, 0.15);
    border-bottom: none;
    position: absolute;
    box-sizing: border-box;
    transform-origin: 50% 0%;
    border-radius: 300px 300px 0 0;
    left: 0;
    top: 0;
  }
  .graph {
    width: 300px;
    height: 150px;
    border: 50px solid var(--fill);
    border-top: none;
    position: absolute;
    transform-origin: 50% 0% 0;
    border-radius: 0 0 300px 300px;
    left: 0;
    top: 100%;
    z-index: 5;
    animation: 1s fillGraphAnimation ease-in;
    transform: rotate(calc(1deg * (var(--percentage) * 1.8)));
    box-sizing: border-box;
    cursor: pointer;
    &:after {
      // content: attr(data-name) ;
      content: attr(data-name) " " counter(varible) "%";
      counter-reset: varible var(--percentage);
      background: var(--fill);
      box-sizing: border-box;
      border-radius: 2px;
      color: #fff;
      font-weight: 200;
      font-size: 12px;
      height: 20px;
      padding: 3px 5px;
      top: 0px;
      position: absolute;
      left: 0;
      transform: rotate(calc(-1deg * var(--percentage) * 1.8))
        translate(-30px, 0px);
      transition: 0.2s ease-in;
      transform-origin: 0 50% 0;
      opacity: 0;
    }
    &:hover {
      opacity: 0.8;
      &:after {
        opacity: 1;
        left: 30px;
      }
    }
  }
}

@keyframes fillAnimation {
  0% {
    transform: rotate(-45deg);
  }
  50% {
    transform: rotate(135deg);
  }
}

@keyframes fillGraphAnimation {
  0% {
    transform: rotate(0deg);
  }
  50% {
    transform: rotate(180deg);
  }
}
</style>
