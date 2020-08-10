<template>
  <div>
    <h2>전체글</h2>
    <button class="btn btn-danger" @click="gowrite">글쓰기</button>
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
            <div>
              <h4 class="card-title">{{ board.title }}</h4>
              <p class="card-text">{{ board.content }}</p>
              <span class="date">{{ board.writeDate.split("T").join(" ") }}ㆍ</span>
            </div>
          </div>
        </div>
      </div>
    </div>
    <!-- <infinite-loading @infinite="infiniteHandler"></infinite-loading> -->
  </div>
</template>

<script>
import axios from "axios";
// import InfiniteLoading from "vue-infinite-loading";

export default {
  name: "Post",
  data: () => {
    return {
      boards: [],
      limit: 0,
    };
  },
  components: {
    // InfiniteLoading,
  },
  watch: {},
  methods: {
    fetchBoards() {
      axios
        .get("http://localhost:8080/board")
        .then((res) => (this.boards = res.data))
        .catch((err) => console.error(err));
    },
    goboard(item) {
      this.$router.push({
        path: `/board/${item}`,
      });
    },
    infiniteHandler($state) {
      axios
        .get("http://localhost:8080/board" + (this.limit + 10))
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
  created() {
    this.fetchBoards();
    // axios.get("http://localhost:8080/board" + this.limit).then((response) => {
    //   this.boards = response.data;
    // });
  },
};
</script>