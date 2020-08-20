<template>
  <div>
    <div class="row">
      <div
        v-for="board in iboards"
        :key="`${board.bid}`"
        class="col-md-6 col-lg-4 my-3"
      >
        <div class="d-flex justify-content-center mt-4">
          <div @click="goboard(`${board.bid}`)" class="card">
            <img
              slot="image"
              class="card-img-top img-thumbnail"
              :src="
                `http://i3d205.p.ssafy.io:8080/img/boardImg/${board.boardImg}`
              "
              alt="Card image cap"
            />
            <div class="container">
              <h4 class="card-title">
                {{ board.title | truncate(20, "...") }}
              </h4>
              <p class="card-text">{{ board.content | truncate(20, "...") }}</p>
              <span class="date"
                >{{ board.writeDate.split("T").join(" ") }}ㆍ</span
              >
              <span class="text-danger">❤ {{ board.likes_count }}</span>
            </div>
          </div>
        </div>
      </div>
    </div>
    <infinite-loading @infinite="infiniteHandler"></infinite-loading>
  </div>
</template>

<script>
import { Button } from "@/components";
import InfiniteLoading from "vue-infinite-loading";
import "codemirror/lib/codemirror.css";
import "@toast-ui/editor/dist/toastui-editor.css";
import { Viewer } from "@toast-ui/vue-editor";

export default {
  name: "Post",
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
    // viewer: Viewer,
  },
  watch: {},
  methods: {
    fetchBoards() {
      this.$axios
        .get("/board/byUser/" + this.uid)
        .then((res) => {
          this.boards = res.data;
          this.getboard();
        })
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
      }, 1500);
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
<style scoped>
.card {
  cursor: pointer;
}
img {
  width: 100%;
  height: 15vw;
  object-fit: contain;
}
</style>
