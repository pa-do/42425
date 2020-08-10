<template>
  <div class="content my-3 mx-5">
    <card style="width: 20rem;">
      <h4 class="my-0 text-primary">{{period}}</h4>
      <h4 class="card-title my-2 text-dark">{{done}}</h4>
      <h6 class="card-subtitle my-2 text-muted">{{at}}</h6>
      <p class="card-text text-dark my-1">{{detail}}</p>
    </card>
  </div>
</template>

<script>
import { Card } from "@/components";
export default {
  name: "resume",
  bodyClass: "profile-page",
  components: {
    Card,
  },
  data: () => {
    return {
      period: "2011.03. ~ 2017.02.",
      done: "컴퓨터공학과 학사",
      at: "금오공과대학교",
      detail: "어쨌거나 졸업은 했습니다만,,,,",
    };
  },
};
</script>

<style>
</style>