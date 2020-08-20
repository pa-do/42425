<template>
  <div class="mt-5">
    <h3 class="title">
      PortFolio
      <i v-if="mine" class="far fa-edit" @click="projectWrite"></i>
    </h3>
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
  props: ["uid", "mine"],

  watch: {},
  methods: {
    fetchBoards() {
      this.$axios
        .get("/project/byuser/" + this.uid)
        .then((res) => (this.boards = res.data))
        .catch((err) => console.error(err));
    },
    onSlideStart(slide) {
      this.sliding = true;
    },
    onSlideEnd(slide) {
      this.sliding = false;
    },
    projectWrite() {
      this.$router.push({
        path: `/portWrite`,
      });
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
  },
};
</script>

<style scoped>
.class-name {
  height: 70vh;
  object-fit: contain;
}
i {
  cursor: pointer;
}
</style>
