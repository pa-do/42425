<template>
  <transition name="modal" style="overflow-y: scroll;">
    <div class="modal-mask">
      <div class="modal-wrapper">
        <div class="modal-container text-dark">
          <div class="d-flex modal-header">
            <div class="align-self-center">" {{ this.keyword }} " 의 검색 결과</div>
            <n-button type="danger" round @click="$emit('close')">닫기</n-button>
          </div>

          <tabs
            type="success"
            tabNavWrapperClasses="col-md-4"
            tabContentClasses="col-md-8"
            vertical
            square
            class="row"
          >
            <tab-pane>
              <span slot="label" class="text-dark">
                <i class="now-ui-icons design_bullet-list-67"></i>제목
              </span>
              <div class="modal-body">
                <div v-for="board in boards" :key="`${board.bid}`">
                  <blockquote class="blockquote text-center card" @click="goboard(`${board.bid}`)">
                    <div class="row">
                      <p class="mb-0">{{ board.title | truncate(20, "...") }}</p>
                      <footer class="blockquote-footer">{{ board.nickname | truncate(10, "...") }}</footer>
                    </div>
                  </blockquote>
                </div>
              </div>
            </tab-pane>

            <tab-pane>
              <span slot="label" class="text-dark">
                <i class="now-ui-icons files_single-copy-04"></i>내용
              </span>
              <div class="modal-body">
                <div v-for="board in Cboards" :key="`${board.bid}`">
                  <blockquote class="blockquote text-center card" @click="goboard(`${board.bid}`)">
                    <div class="row">
                      <p class="mb-0">{{ board.content | truncate(20, "...") }}</p>
                      <footer class="blockquote-footer">{{ board.nickname | truncate(10, "...") }}</footer>
                    </div>
                  </blockquote>
                </div>
              </div>
            </tab-pane>

            <tab-pane>
              <span slot="label" class="text-dark">
                <i class="now-ui-icons users_single-02"></i>닉네임
              </span>
              <div class="modal-body">
                <div v-for="board in Nboards" :key="`${board.bid}`">
                  <blockquote class="blockquote text-center card" @click="goboard(`${board.bid}`)">
                    <div class="row">
                      <p class="mb-0">{{ board.nickname | truncate(20, "...") }}</p>
                    </div>
                  </blockquote>
                </div>
              </div>
            </tab-pane>
          </tabs>

          <!-- <div class="modal-body">
            <div v-for="board in boards" :key="`${board.bid}`">
              <blockquote class="blockquote text-center card" @click="goboard(`${board.bid}`)">
                <div class="row">
                  <p class="mb-0">{{ board.title | truncate(20, "...") }}</p>
                  <footer class="blockquote-footer">{{ board.nickname | truncate(10, "...") }}</footer>
                </div>
              </blockquote>
            </div>
          </div>-->
        </div>
      </div>
    </div>
  </transition>
</template>

<script>
import { Button, Tabs, TabPane } from "@/components";
export default {
  props: ["keyword"],
  components: {
    [Button.name]: Button,
    Tabs,
    TabPane,
  },
  data: function () {
    return {
      boards: [],
      Cboards: [],
      Nboards: [],
    };
  },
  methods: {
    searchTitle() {
      this.$axios
        .get("/board/searchTitle/" + this.keyword)
        .then((res) => (this.boards = res.data))
        .catch((err) => console.error(err));
    },
    searchContent() {
      this.$axios
        .get("/board/searchContent/" + this.keyword)
        .then((res) => (this.Cboards = res.data))
        .catch((err) => console.error(err));
    },
    searchNickname() {
      this.$axios
        .get("/board/searchNickname/" + this.keyword)
        .then((res) => (this.Nboards = res.data))
        .catch((err) => console.error(err));
    },
    goboard(item) {
      this.$emit("close");
      this.$router.push({
        path: `/board/${item}`,
      });
      this.$router.go();
    },
  },
  created() {
    this.searchTitle();
    this.searchContent();
    this.searchNickname();
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
};
</script>

<style>
.modal-mask {
  position: fixed;
  z-index: 9998;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  display: table;
  transition: opacity 0.3s ease;
}

.modal-wrapper {
  display: table-cell;
  vertical-align: top;
}

.modal-container {
  width: 700px;
  margin: 0px auto;
  padding: 20px 30px;
  background-color: #fff;
  border-radius: 2px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.33);
  transition: all 0.3s ease;
  font-family: Helvetica, Arial, sans-serif;
}

.modal-header h3 {
  margin-top: 0;
  color: #42b983;
}

.modal-body {
  margin: 20px 0;
  max-height: calc(100vh - 200px);
  overflow-y: auto;
}

.modal-default-button {
  float: right;
}

.modal-enter {
  opacity: 0;
}

.modal-leave-active {
  opacity: 0;
}

.modal-enter .modal-container,
.modal-leave-active .modal-container {
  -webkit-transform: scale(1.1);
  transform: scale(1.1);
}
</style>