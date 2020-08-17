<template>
  <div class="content">
    <div class="social-description">
      <h2>{{post}}</h2>
      <p>Post</p>
    </div>
    <div class="social-description">
      <h2>{{follower}}</h2>
      <p>Follower</p>
    </div>
    <div class="social-description">
      <h2>{{followee}}</h2>
      <p>Following</p>
    </div>
  </div>
</template>

<script>
export default {
  name: "counter",
  props: ["uid"],
  created() {
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
          this.follower = res.data.length;
        })
        .catch((err) => console.error(err));
    },
    getFollowee() {
      this.$axios
        .get(`/follow/getFolloweeList/${this.uid}`)
        .then((res) => {
          this.followee = res.data.length;
        })
        .catch((err) => console.error(err));
    },
  },
  data: () => {
    return {
      post: 0,
      follower: 0,
      followee: 0,
    };
  },
};
</script>

<style>
</style>