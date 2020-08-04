<template>
  <div class="post">
    <div class="wrapB">
      <h2>전체글</h2>
      <section class="post-list">
        <div v-for="board in boards" :key="`board_${board.bid}`">
          <div class="post-card" @click="goboard(`${board.bid}`)">
            <a>
              <div
                :style="{
                  backgroundImage:
                    'url(https://www.ipcc.ch/site/assets/uploads/sites/3/2019/10/img-placeholder.png)',
                }"
                class="post-img"
              />

              <div class="contents">
                <h3>{{ board.title }}</h3>
                <p class="content">{{ board.content }}</p>
                <span class="date"
                  >{{ board.writeDate.split("T").join(" ") }}ㆍ</span
                >
                <span class="comment">댓글 0개</span>
              </div>
            </a>

            <div class="writer-wrap">
              <a>{{ board.nickname }}</a>
              <span>♥ 2</span>
            </div>
          </div>
        </div>
      </section>
      <div class="tag-list-wrap">
        <h4>인기태그</h4>
        <ul class="tag-list">
          <li>#태그1 (8)</li>
          <li>#태그2 (5)</li>
          <li>#태그3 (2)</li>
        </ul>
      </div>
    </div>
    <!-- <infinite-loading @infinite="infiniteHandler"></infinite-loading> -->
  </div>
</template>

<script>
import "../../assets/css/post.scss";
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
  },
  created() {
    this.fetchBoards();
    // axios.get("http://localhost:8080/board" + this.limit).then((response) => {
    //   this.boards = response.data;
    // });
  },
};
</script>
