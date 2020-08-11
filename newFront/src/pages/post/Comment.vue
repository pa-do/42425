<template>
  <div>
    <div v-for="comment in comments" :key="`${comment.cid}`">
      <blockquote class="blockquote text-center">
        <p class="mb-0">{{ comment.content }}</p>
        <footer class="blockquote-footer">
          작성자
          <cite title="Source Title">{{comment.writeDate}}</cite>
        </footer>
      </blockquote>
    </div>
  </div>
</template>

<script>
import axios from "axios";
const BASE_URL = "http://localhost:8080";

export default {
  props: ["bid"],
  data: function () {
    return {
      comments: [],
      isAuthorized: false,
    };
  },
  methods: {
    fetchComment() {
      axios
        .get(BASE_URL + "/comment/board/" + this.bid)
        .then((res) => {
          this.comments = res.data;
        })
        // .then((res) => console.log(res))
        .catch((err) => console.error(err));
    },
  },
  created() {
    this.fetchComment();
  },
};
</script>

<style>
</style>