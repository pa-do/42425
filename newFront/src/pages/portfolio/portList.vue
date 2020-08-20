<template>
  <div class="mt-5">
    <b-carousel
      id="carousel-1"
      v-model="slide"
      :interval="4000"
      controls
      indicators
      background="#ababab"
      img-width="500"
      img-height="300"
      style="text-shadow: 1px 1px 2px #333;"
      @sliding-start="onSlideStart"
      @sliding-end="onSlideEnd"
    >
      <b-carousel-slide
        v-for="board in boards"
        :key="`${board.pid}`"
        :caption="`${board.title}`"
        :text="`${board.summary}`"
      >
        <template v-slot:img>
          <img
            class="d-block mx-auto class-name"
            width="1024"
            :src="
              `http://i3d205.p.ssafy.io:8080/img/projectImg/${board.projectImg}`
            "
            alt="image slot"
          />
        </template>
      </b-carousel-slide>
    </b-carousel>
  </div>
</template>

<script>
import { BCarousel, BCarouselSlide } from "bootstrap-vue";
export default {
  data: () => {
    return {
      boards: [],
      slide: 0,
      sliding: null,
    };
  },
  components: {
    BCarousel,
    BCarouselSlide,
  },
  // props: ["uid"],

  watch: {},
  methods: {
    fetchBoards() {
      this.$axios
        .get("/project/byuser/" + 4)
        .then((res) => (this.boards = res.data))
        .catch((err) => console.error(err));
    },
    onSlideStart(slide) {
      this.sliding = true;
    },
    onSlideEnd(slide) {
      this.sliding = false;
    },
    // goboard(item) {
    //   this.$router.push({
    //     path: `/board/${item}`,
    //   });
    // },
    // getboard() {
    //   this.iboards = this.iboards.concat(
    //     this.boards.slice(this.limit, this.limit + 6)
    //   );
    //   this.limit = this.limit + 6;
    // },
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
  },
};
</script>

<style scoped>
.class-name {
  height: 70vh;
  object-fit: contain;
}
</style>
