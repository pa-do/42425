<template>
  <div></div>
</template>

<script>
export default {
  methods: {
    logout() {
      Swal.fire({
        icon: "success",
        title: "BYE " + this.$session.get("user").nickname,
      }).then(() => {
        this.$session.destroy();
        this.$router.push("/");
        this.$cookie.delete("auth-token");
        this.$router.go();
      });
      // this.$session.destroy();
      // this.$router.push("/");
      // this.$cookie.delete("auth-token");
      // this.$router.go();
    },
  },
  created() {
    this.logout();
  },
};
</script>

<style></style>
