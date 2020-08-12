<template>
  <div class="container">
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
  </div>
</template>

<script>
export default {
  name: "resume",
  components: {},
  props: ["uid"],
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