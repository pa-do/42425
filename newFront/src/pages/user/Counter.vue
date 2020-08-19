<template>
  <div class="content container">
    <div class="row">
      <n-button
        @click="scrollPost"
        class="btn-link social-description mx-0 my-0 col-4"
        type="neutral"
      >
        <h1>{{post}}</h1>
        <p>Post</p>
      </n-button>

      <n-button
        class="btn-link social-description mx-0 my-0 col-4"
        type="neutral"
        @click.native="modals.follower = true"
      >
        <h1>{{follower}}</h1>
        <p>Follower</p>
      </n-button>
      <!-- Modal -->
      <modal :show.sync="modals.follower" headerClasses="justify-content-center">
        <h4 slot="header" class="text-dark">{{nick}}님을 팔로우하는 사람들</h4>
        <div class="list-group" style="max-height: 40vh">
          <div v-for="user in followers" :key="user.fid">
            <div @click="goUser(user.follower.uid)" class="list-group-item list-group-item-action">
              <h5 class="my-0">{{user.follower.nickname}}</h5>
            </div>
          </div>
        </div>
        <template slot="footer">
          <n-button class="ml-auto" type="danger" @click.native="modals.follower = false">Close</n-button>
        </template>
      </modal>
      <!-- modal end -->

      <n-button
        class="btn-link social-description mx-0 my-0 col-4"
        type="neutral"
        @click.native="modals.followee = true"
      >
        <h1>{{followee}}</h1>
        <p>Following</p>
      </n-button>
      <modal :show.sync="modals.followee" headerClasses="justify-content-center">
        <h4 slot="header" class="text-dark">{{nick}}님이 팔로우하는 사람들</h4>
        <div class="list-group">
          <div v-for="user in followees" :key="user.fid">
            <div @click="goUser(user.followee.uid)" class="list-group-item list-group-item-action">
              <h5 class="my-0">{{user.followee.nickname}}</h5>
            </div>
          </div>
        </div>
        <template slot="footer">
          <n-button class="ml-auto" type="danger" @click.native="modals.followee = false">Close</n-button>
        </template>
      </modal>
    </div>
  </div>
</template>

<script>
import { Modal, Button } from "@/components";

export default {
  name: "counter",
  props: ["uid", "nick", "followChk"],
  components: {
    Modal,
    [Button.name]: Button,
  },
  watch: {
    followChk() {
      this.getFollower();
    },
  },
  mounted() {
    this.getPost();
    this.getFollower();
    this.getFollowee();
  },
  methods: {
    getPost() {
      this.$axios
        .get(`/board/byUser/${this.uid}`)
        .then((res) => {
          this.post = res.data.length;
        })
        .catch((err) => console.error(err));
    },
    getFollower() {
      this.$axios
        .get(`/follow/getFollowerList/${this.uid}`)
        .then((res) => {
          this.followers = res.data;
          this.follower = res.data.length;
        })
        .catch((err) => console.error(err));
    },
    getFollowee() {
      this.$axios
        .get(`/follow/getFolloweeList/${this.uid}`)
        .then((res) => {
          this.followees = res.data;
          this.followee = res.data.length;
        })
        .catch((err) => console.error(err));
    },
    goUser(item) {
      this.$router.push({
        path: `/profile/${item}`,
      });
      this.$router.go();
    },
    scrollPost() {
      this.$emit("update");
    },
  },
  data: () => {
    return {
      modals: {
        follower: false,
        followee: false,
      },
      post: 0,
      follower: 0,
      followee: 0,

      followers: [],
      followees: [],
    };
  },
};
</script>

<style>
</style>