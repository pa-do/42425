<template>
  <div class="container">
    <!-- 내꺼인 경우 (수정 및 삭제 가능) -->
    <span v-if="mine">
      <div class="row">
        <span v-for="resume in resumes" :key="resume.rid">
          <div class="col-md-6 my-5">
            <span class="text-primary">{{resume.start_year}}-{{resume.end_year}}</span>
            <h2>{{resume.title}}</h2>
            <span>{{resume.place}}</span>
            <p class="mt-4">{{resume.content}}</p>
          </div>
        </span>
      </div>
    </span>
    <!-- 내꺼가 아닌 경우(조회만 가능) -->
    <span v-else>
      <div class="row">
        <span v-for="resume in resumes" :key="resume.rid">
          <div class="col-md-6 my-5">
            <span class="text-primary">{{resume.start_year}}-{{resume.end_year}}</span>
            <h2>{{resume.title}}</h2>
            <span>{{resume.place}}</span>
            <p class="mt-4">{{resume.content}}</p>
          </div>
        </span>
      </div>
    </span>
  </div>
</template>

<script>
export default {
  name: "resume",
  components: {},
  props: {
    uid: Number,
    mine: Boolean,
  },
  data: () => {
    return {
      resumes: [],
    };
  },
  methods: {},
};
</script>

<style>
</style>