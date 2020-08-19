<template>
  <transition name="modal" style="overflow-y: scroll;">
    <div class="modal-mask">
      <div class="modal-wrapper">
        <div class="modal-container text-dark">
          <div class="d-flex modal-header">
            <div class="align-self-center">
              <h3 class="mb-0">
                <i class="fas fa-quote-left mr-1"></i>
                {{ this.keyword }}
                <i class="fas fa-quote-right ml-1"></i>
                의 검색 결과
              </h3>
            </div>
            <n-button type="danger" round @click="$emit('close')"
              >닫기</n-button
            >
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
              <h4 slot="label" class="text-dark m-0">
                <i class="now-ui-icons design_bullet-list-67"></i>
                제목
              </h4>
              <div class="modal-body">
                <h4 class="text-dark my-3">
                  <i class="now-ui-icons design_bullet-list-67"></i>
                  제목
                  <badge class="align-middle" type="primary">{{
                    Tcount
                  }}</badge>
                </h4>
                <div v-for="board in boards" :key="`${board.bid}`">
                  <blockquote
                    class="blockquote text-center card"
                    @click="goboard(`${board.bid}`)"
                  >
                    <div class="row">
                      <p class="mb-0">
                        {{ board.title | truncate(20, "...") }}
                      </p>
                      <footer class="blockquote-footer">
                        {{ board.nickname | truncate(10, "...") }}
                      </footer>
                    </div>
                  </blockquote>
                </div>
              </div>
            </tab-pane>

            <tab-pane>
              <h4 slot="label" class="text-dark m-0">
                <i class="now-ui-icons files_single-copy-04"></i>내용
              </h4>
              <div class="modal-body">
                <h4 class="text-dark my-3">
                  <i class="now-ui-icons files_single-copy-04"></i>내용
                  <badge class="align-middle" type="primary">{{
                    Ccount
                  }}</badge>
                </h4>
                <div v-for="board in Cboards" :key="`${board.bid}`">
                  <blockquote
                    class="blockquote text-center card"
                    @click="goboard(`${board.bid}`)"
                  >
                    <div class="row">
                      <p class="mb-0">
                        {{ board.content | truncate(20, "...") }}
                      </p>
                      <footer class="blockquote-footer">
                        {{ board.nickname | truncate(10, "...") }}
                      </footer>
                    </div>
                  </blockquote>
                </div>
              </div>
            </tab-pane>

            <tab-pane>
              <h4 slot="label" class="text-dark m-0">
                <i class="now-ui-icons users_single-02"></i>닉네임
              </h4>
              <div class="modal-body">
                <h4 class="text-dark my-3">
                  <i class="now-ui-icons users_single-02"></i>닉네임
                  <badge class="align-middle" type="primary">{{
                    Ncount
                  }}</badge>
                </h4>
                <div v-for="board in Nboards" :key="`${board.uid}`">
                  <blockquote
                    class="blockquote text-center card"
                    @click="goNick(`${board.uid}`)"
                  >
                    <div class="row">
                      <p class="mb-0">
                        {{ board.nickname | truncate(20, "...") }}
                      </p>
                    </div>
                  </blockquote>
                </div>
              </div>
            </tab-pane>

            <tab-pane>
              <h4 slot="label" class="text-dark m-0">
                <i class="now-ui-icons ui-2_settings-90"></i>기술
              </h4>
              <div class="modal-body">
                <h4 class="text-dark my-3">
                  <i class="now-ui-icons ui-2_settings-90"></i>기술
                  <badge class="align-middle" type="primary">{{
                    Scount
                  }}</badge>
                </h4>
                <div v-for="board in Sboards" :key="`${board.sid}`">
                  <blockquote
                    class="blockquote text-center card"
                    @click="goNick(`${board.user.uid}`)"
                  >
                    <div class="row ml-3">
                      <p class="mb-0 font-weight-bold">
                        {{ board.user.nickname | truncate(20, "...") }}
                      </p>
                    </div>
                    <div class="row ml-3">
                      <p class="mb-0">{{ board.skill }}</p>
                      <p class="mb-0">({{ board.value }})</p>
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
import { Button, Tabs, TabPane, Badge } from "@/components";
export default {
  props: ["keyword"],
  components: {
    [Button.name]: Button,
    Tabs,
    TabPane,
    [Badge.name]: Badge,
  },
  data: function() {
    return {
      boards: [],
      Cboards: [],
      Nboards: [],
      Sboards: [],

      Tcount: 0,
      Ccount: 0,
      Ncount: 0,
      Scount: 0,
    };
  },
  methods: {
    searchTitle() {
      this.$axios
        .get("/board/searchTitle/" + this.keyword)
        .then((res) => {
          this.boards = res.data;
          this.Tcount = this.boards.length;
          console.log(this.Tcount);
        })
        .catch((err) => console.error(err));
    },
    searchContent() {
      this.$axios
        .get("/board/searchContent/" + this.keyword)
        .then((res) => {
          this.Cboards = res.data;
          this.Ccount = this.Cboards.length;
          console.log(this.Ccount);
        })
        .catch((err) => console.error(err));
    },
    searchNickname() {
      this.$axios
        .get("/account/searchNickname/" + this.keyword)
        .then((res) => {
          this.Nboards = res.data;
          this.Ncount = this.Nboards.length;
          console.log(this.Ncount);
        })
        .catch((err) => console.error(err));
    },
    searchSkill() {
      this.$axios
        .get(`/portfolio/skill/search/${this.keyword}`)
        .then((res) => {
          this.Sboards = res.data.object;
          this.Scount = this.Sboards.length;
          console.log(this.Scount);
        })
        .catch((err) => console.error(err));
    },
    goboard(item) {
      this.$emit("close");
      this.$router.push({
        path: `/board/${item}`,
      });
      this.$router.go();
    },
    goNick(item) {
      this.$emit("close");
      this.$router.push({
        path: `/profile/${item}`,
      });
      this.$router.go();
    },
  },
  created() {
    this.searchTitle();
    this.searchContent();
    this.searchNickname();
    this.searchSkill();
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
