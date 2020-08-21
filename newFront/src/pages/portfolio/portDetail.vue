<template>
  <div class="container">
    <div class="mt-5"></div>
    <div class="card mt-5">
      <h1 class="card-header text-center mt-3">{{ project.title }}</h1>
      <h4
        class="text-center"
      >개발기간 : {{project.startdate}}~{{project.enddate}} (약 {{project.devdate}}주)</h4>
      <div class="card-body">
        <blockquote class="blockquote mb-0 text-right">
          <p class="text-left">요약 : {{ project.summary }}</p>
          <viewer height="500px" v-if="project.content != null" :initialValue="project.content" />
          <footer class="blockquote-footer">
            <router-link :to="`/profile/${this.project.uid}`">{{ project.nickname }}</router-link>
          </footer>
        </blockquote>
      </div>
      <div v-if="isAuthorized" class="text-right">
        <n-button type="info" round>수정</n-button>
        <n-button type="danger" round @click="deleteAlert">삭제</n-button>
      </div>
    </div>
  </div>
</template>

<script>
import { Button } from "@/components";
import "codemirror/lib/codemirror.css";
import "@toast-ui/editor/dist/toastui-editor.css";
import { Viewer } from "@toast-ui/vue-editor";

export default {
  components: {
    [Button.name]: Button,
    viewer: Viewer,
  },
  data: function () {
    return {
      project: Object,
      pid: "",
      isAuthorized: false,
    };
  },
  methods: {
    fetchProject() {
      // console.log(this.$route.params.bid);
      this.$axios
        .get(`/project/${this.$route.params.pid}`)
        .then((res) => {
          this.project = res.data;
          this.pid = this.project.pid;
          if (this.project.uid == this.$cookie.get("auth-token")) {
            this.isAuthorized = true;
          }
        })
        // .then((res) => console.log(res))
        .catch((err) => console.error(err));
    },
    deleteProject() {
      this.$axios
        .delete(`/project/delete/${this.project.pid}`)
        .then((res) => {
          // console.log(res);
          this.$router.push(`/profile/${this.project.uid}`);
        })
        .catch((err) => console.error(err));
    },
    deleteAlert() {
      Swal.fire({
        title: "정말 삭제하시겠어요?",
        text: "확인 버튼을 누르면 글이 삭제되어 복구할 수 없게 됩니다.",
        icon: "warning",
        showCancelButton: true,
        confirmButtonColor: "#d33",
        cancelButtonColor: "#3085d6",
        confirmButtonText: "삭제할래요😥",
        cancelButtonText: "안할래요😊",
      }).then((result) => {
        if (result.value) {
          Swal.fire("글이 삭제되었습니다.");
          this.deleteProject();
        }
      });
    },
  },
  created() {
    this.fetchProject();
  },
};
</script>

<style>
.tui-editor-contents {
  text-align: left;
}
</style>
