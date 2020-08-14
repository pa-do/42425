<template>
  <div>
    <!-- <h2>전체글</h2> -->
    <div class="text-right">
      <n-button v-if="mine" class="btn btn-primary" @click="gowrite">글쓰기</n-button>
    </div>
    <div class="row">
      <div v-for="board in boards" :key="`${board.bid}`" class="col-md-6 col-lg-4 my-3">
        <div class="d-flex justify-content-center mt-4">
          <div @click="goboard(`${board.bid}`)" class="card">
            <img
              slot="image"
              class="card-img-top"
              src="https://www.ipcc.ch/site/assets/uploads/sites/3/2019/10/img-placeholder.png"
              alt="Card image cap"
            />
            <!-- <img
              slot="image"
              class="card-img-top"
              src="https://picsum.photos/200/300"
              alt="Card image cap"
            />-->
            <div class="container">
              <h4 class="card-title">{{ board.title }}</h4>
              <p class="card-text">{{ board.content | truncate(20, "...") }}</p>
              <span class="date">{{ board.writeDate.split("T").join(" ") }}ㆍ</span>
              <span class="text-danger">❤ {{ board.likes_count }}</span>
            </div>
          </div>
        </div>
      </div>
    </div>
    <!-- <infinite-loading @infinite="infiniteHandler"></infinite-loading> -->
  </div>
</template>

<script>
import { Button } from "@/components";
// import InfiniteLoading from "vue-infinite-loading";

export default {
  name: "Post",
  data: () => {
    return {
      boards: [],
      limit: 0,
    };
  },
  props: ["uid", "mine"],
  components: {
    [Button.name]: Button,
    // InfiniteLoading,
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
      this.$axios
        .get("/board" + (this.limit + 10))
        .then((response) => {
          setTimeout(() => {
            if (response.data.length) {
              this.boards = concat(response.data);
              $state.loaded();
              this.limit += 10;
              if (this.boards.length / 10 == 0) {
                $state.complete();
              }
            } else {
              $state.complete();
            }
          }, 1000);
        })
        .catch((err) => console.error(err));
    },
    gowrite() {
      this.$router.push("/board/write");
    },
  },
  filters: {
    truncate: function (text, length, suffix) {
      if (text.length > length) {
        return text.substring(0, length) + suffix;
      } else {
        return text;
      }
    },
  },
  created() {
    this.fetchBoards();
    // this.$axios.get("/board" + this.limit).then((response) => {
    //   this.boards = response.data;
    // });
  },
};
</script>
