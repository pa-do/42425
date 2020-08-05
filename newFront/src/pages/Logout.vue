<template>
  <div>
    <button @click="logout">로그아웃</button>
    로그아웃일까?
  </div>
</template>

<script>
export default {
  methods: {
    logout() {
      alert("BYE111 " + this.$session.get("user").nickname);
      this.$session.destroy();
      this.$router.push("/");
      this.$cookie.delete("auth-token");
      this.$router.go();
    },
  },
  created() {
    this.logout();
  },
};
</script>

<style></style>
