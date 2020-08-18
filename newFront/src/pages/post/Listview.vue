<template>
  <div class="mt-5">
    <table class="table text-center">
      <thead class="thead-dark">
        <tr>
          <th scope="col">#</th>
          <th scope="col">제목</th>
          <th scope="col" class="d-none d-md-block">작성시간</th>
          <th scope="col"><i class="far fa-heart"></i></th>
        </tr>
      </thead>
      <tbody>
        <tr
          v-for="board in boards"
          :key="`${board.bid}`"
          @click="goboard(`${board.bid}`)"
        >
          <th scope="row">{{ board.bid }}</th>
          <td>{{ board.title | truncate(20, "...") }}</td>
          <td class="d-none d-md-block">
            {{ board.writeDate.split("T").join(" ") }}
          </td>
          <td>{{ board.likes_count }}</td>
        </tr>
      </tbody>
    </table>
    <infinite-loading @infinite="infiniteHandler"></infinite-loading>
  </div>
</template>

<script>
import { Button } from "@/components";
import InfiniteLoading from "vue-infinite-loading";

export default {
  data: () => {
    return {
      boards: [],
      iboards: [],
      limit: -6,
    };
  },
  props: ["uid"],
  components: {
    [Button.name]: Button,
    InfiniteLoading,
  },
  watch: {},
  methods: {
    fetchBoards() {
      this.$axios
        .get("/board/byUser/" + this.uid)
        .then((res) => (this.boards = res.data))
        .catch((err) => console.error(err));
    },
    goboard(item) {
      this.$router.push({
        path: `/board/${item}`,
      });
    },
    infiniteHandler($state) {
      setTimeout(() => {
        this.getboard();
        if (this.boards.length > this.limit) {
          $state.loaded();
        } else {
          $state.complete();
        }
      }, 2000);
    },
    getboard() {
      this.iboards = this.iboards.concat(
        this.boards.slice(this.limit, this.limit + 6)
      );
      this.limit = this.limit + 6;
    },
  },
  filters: {
    truncate: function(text, length, suffix) {
      if (text.length > length) {
        return text.substring(0, length) + suffix;
      } else {
        return text;
      }
    },
  },
  created() {
    this.fetchBoards();
    this.getboard();
  },
};
</script>

<style>
tr {
  cursor: pointer;
}
</style>
